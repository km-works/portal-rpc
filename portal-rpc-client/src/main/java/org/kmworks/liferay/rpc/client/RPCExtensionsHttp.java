/*
 * Copyright (C) 2005-2014 Christian P. Lerch <christian.p.lerch [at] gmail [dot] com>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 */
package org.kmworks.liferay.rpc.client;

import com.google.common.io.ByteStreams;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.ServiceContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import org.kmworks.portal.rpc.service.RPCExtensionsLocalServiceUtil;
import org.kmworks.liferay.rpc.utils.AuthToken;
import org.kmworks.liferay.rpc.utils.RPCTunneling;
import org.kmworks.util.misc.MimeType;

/**
 *
 * @author Christian P. Lerch
 */
public class RPCExtensionsHttp {
  
  /** Return the user's repositoryId (sometimes also called groupId).
   * 
   * @param token The authenticated user token
   * @return The user's repository id
   */
  public static long getRepositoryId(AuthToken token) {
    
    final long userId = token.getUserId();
    final HttpPrincipal principal = token.getPrincipal();

    try {
			MethodKey methodKey = new MethodKey(com.liferay.portal.service.GroupLocalServiceUtil.class,
					"getGroup", TYPE_LONG_STRING);
			MethodHandler methodHandler = new MethodHandler(methodKey,
					token.getCompanyId(), String.valueOf(userId));
			Group group = (Group)RPCTunneling.invoke(principal, methodHandler);
      if (group == null) throw new SystemException("GroupLocalServiceUtil.getGroup returned null for userId: " + userId);
      return group.getGroupId();
		}
		catch (PortalException | SystemException se) {
			LOG.error(se, se);
			throw new RuntimeException(se);
    }
  }
  
  /** Download a FileEntry's file content to a file on the local file system.
   * 
   * @param token The authenticated user token
   * @param fileEntryId
   * @param version
   * @param localFile
   * @return 
   */
  public static long downloadFile(AuthToken token,
          long fileEntryId,
          String version,
          File localFile) {
    
    long byteCount;
    try {
      try (InputStream from = new ByteArrayInputStream(getFileAsBytes(token, fileEntryId, version)); 
           OutputStream to = new FileOutputStream(localFile)) {
        byteCount = ByteStreams.copy(from, to);
      }
    } catch (IOException ioe) {
      LOG.error(ioe, ioe);
      throw new RuntimeException(ioe);
    }
    return byteCount;
  }
  
  /** Download a FileEntry's file content to a file on the server file system.
   * Be careful with relative file specs: they are resolved relative to the application server home directory.
   * This is the only file retrieval method, that doesn't serialize the file content through the
   * underlying HTTP stream.
   * 
   * @param token The authenticated user token
   * @param fileEntryId
   * @param version
   * @param serverFile
   * @return 
   */
	public static long downloadFileToServer(AuthToken token, 
          long fileEntryId, 
          String version,
          File serverFile) {
    
		try {
			MethodKey methodKey = new MethodKey(RPCExtensionsLocalServiceUtil.class,
					"downloadFileToServer", TYPE_LONG_LONG_STRING_FILE);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					token.getUserId(), fileEntryId, version, serverFile);

			return (long)RPCTunneling.invoke(token.getPrincipal(), methodHandler);
		}
		catch (PortalException | SystemException se) {
			LOG.error(se, se);
			throw new RuntimeException(se);
		}
	}

  /** Return a FileEntry's file content as a stream.
   * This is just a convenience wrapper for getFileAsBytes, which is doing the job.
   * @param token The authenticated user token
   * @param fileEntryId
   * @param version
   * @return
   * @throws PortalException
   * @throws SystemException 
   */
	public static InputStream getFileAsStream(AuthToken token, 
          long fileEntryId, 
          String version)
      throws PortalException, SystemException {
    
    return new ByteArrayInputStream(getFileAsBytes(token, fileEntryId, version));
	}
  
  /** Return a FileEntry's file content as a (serializable) byte array.
   * 
   * All other methods for retrieving a FileEntry's file content are based on this one,
   * because byte arrays are the only containers that can be serialized.
   * Note however, that for very lerge files calling this mehtod may not be reasonable
   * due to excessive memory consumption. In such a case downloading the file to the 
   * server's filesystem with method downloadFileToServer() may be a viable solution.
   * 
   * @param token The authenticated user token
   * @param fileEntryId
   * @param version
   * @return
   */
  public static byte[] getFileAsBytes(AuthToken token, 
          long fileEntryId, 
          String version) {
    
		try {
			MethodKey methodKey = new MethodKey(RPCExtensionsLocalServiceUtil.class,
					"getFileAsBytes", TYPE_LONG_LONG_STRING);

			MethodHandler methodHandler = new MethodHandler(methodKey,
					token.getUserId(), fileEntryId, version);

			return (byte[])RPCTunneling.invoke(token.getPrincipal(), methodHandler);
		}
		catch (PortalException | SystemException se) {
			LOG.error(se, se);
			throw new RuntimeException(se);
		}
	}
  
  public static FileEntry uploadFile(
          AuthToken token, 
          long folderId,
          long repositoryId,
          String title, 
          String sourceFileName,
          InputStream in) throws RuntimeException
  {
    FileEntry fileEntry = null;
    try {
      fileEntry = uploadFile(token, folderId, repositoryId, title, sourceFileName, ByteStreams.toByteArray(in));
    } catch(IOException ex) {
      throw new RuntimeException(ex);
    }
    return fileEntry;
  }
  
  /** Upload a local file to the portal, thereby creating a new FileEntry.
   * This is just a convenience wrapper around DLAppServiceHttp.addFileEntry(...) which is doing all the work.
   * @param token  The authenticated user token holding the user that will be the owner of the new FileEntry
   * @param folderId  The id of target folder; must be in repository of the user defined in AccessToken
   * @param repositoryId
   * @param title The title of new FileEntry (i.e. document title)
   * @param sourceFileName The upload file's name
   * @param content
   * @return Newly created FileEntry
   */
  public static FileEntry uploadFile(
          AuthToken token, 
          long folderId,
          long repositoryId,
          String title, 
          String sourceFileName,
          byte[] content)  throws RuntimeException
  {  
    final long userId = token.getUserId();
    final String mimeType = MimeType.fromFileName(sourceFileName);
    
    final ServiceContext svcCtx = new ServiceContext();
    svcCtx.setCompanyId(token.getPrincipal().getCompanyId());
    svcCtx.setScopeGroupId(repositoryId);
    svcCtx.setUserId(userId);
    svcCtx.setAddGroupPermissions(true);
    svcCtx.setIndexingEnabled(true);
    
    FileEntry fileEntry = null;
    try {
      fileEntry = com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil.addFileEntry(
              token.getUserId(),                // owner
              repositoryId,                     // = groupId
              folderId,                         // parent folder
              sourceFileName,                   // 
              mimeType,                         // 
              title,                            // document title
              "",                               // description
              "",                               // changeLog entry
              content,      // file's byte content
              svcCtx);
    } catch (PortalException | SystemException ex) {
      throw new RuntimeException(String.format("Cannot upload file '%s' to folder id=%d", title, folderId), ex);
    }
    return fileEntry; 
  }
  
  /**
   * 
   * @param token The authenticated user token
   * @return
   */
  public static int getOrganizationsCount(AuthToken token) {
    
		try {
			MethodKey methodKey = new MethodKey(com.liferay.portal.service.OrganizationLocalServiceUtil.class,
					"getOrganizationsCount", TYPE_VOID);
			MethodHandler methodHandler = new MethodHandler(methodKey);
			return (int)RPCTunneling.invoke(token.getPrincipal(), methodHandler);
		}
		catch (PortalException | SystemException ex) {
      LOG.error(ex, ex);
      throw new RuntimeException(ex);
    }
  }
  
  public static long getCompanyId(final AuthToken token) {
    
    long companyId = 0L;
		try {
			MethodKey methodKey = new MethodKey(com.liferay.portal.service.CompanyLocalServiceUtil.class,
					"getCompanyByVirtualHost", TYPE_STRING);
			MethodHandler methodHandler = new MethodHandler(methodKey, token.getVirtualHost());
			final Company company = (Company)RPCTunneling.invoke(token.getPrincipal(), methodHandler);
      companyId = company.getCompanyId();
		}
		catch (PortalException | SystemException | URISyntaxException e) {
			LOG.error(e, e);
			throw new RuntimeException(e);
		}
    return companyId;
  }
  
  public static User getUserfromToken(final AuthToken token) {
    
    final String login = token.getPrincipal().getLogin();
    final long companyId = token.getCompanyId();
    final String authType = determineAuthType(login);
    
		User user = null;
    switch (authType) {
      case CompanyConstants.AUTH_TYPE_EA:
        user = fetchUserByEmailAddress(token, companyId, login);
        break;
      case CompanyConstants.AUTH_TYPE_SN:
        user = fetchUserByScreenName(token, companyId, login);
        break;
      case CompanyConstants.AUTH_TYPE_ID:
        user = fetchUserById(token, Long.parseLong(login));
        break;
    }
    return user;
  }

  private static User fetchUserByScreenName(AuthToken token, long companyId, String screenName) {
		try {
			MethodKey methodKey = new MethodKey(com.liferay.portal.service.UserLocalServiceUtil.class,
					"fetchUserByScreenName", TYPE_LONG_STRING);
			MethodHandler methodHandler = new MethodHandler(methodKey,
          companyId, screenName);
			return (User)RPCTunneling.invoke(token.getPrincipal(), methodHandler);
		}
		catch (PortalException | SystemException se) {
			LOG.error(se, se);
			throw new RuntimeException(se);
		}
  }
  
  private static User fetchUserByEmailAddress(AuthToken token, long companyId, String emailAddress) {
		try {
			MethodKey methodKey = new MethodKey(com.liferay.portal.service.UserLocalServiceUtil.class,
					"fetchUserByEmailAddress", TYPE_LONG_STRING);
			MethodHandler methodHandler = new MethodHandler(methodKey,
          companyId, emailAddress);
			return (User)RPCTunneling.invoke(token.getPrincipal(), methodHandler);
		}
		catch (PortalException | SystemException se) {
			LOG.error(se, se);
			throw new RuntimeException(se);
		}
  }
  
  private static User fetchUserById(AuthToken token, long userId) {
		try {
			MethodKey methodKey = new MethodKey(com.liferay.portal.service.UserLocalServiceUtil.class,
					"fetchUserById", TYPE_LONG);
			MethodHandler methodHandler = new MethodHandler(methodKey,
          userId);
			return (User)RPCTunneling.invoke(token.getPrincipal(), methodHandler);
		}
		catch (PortalException | SystemException se) {
			LOG.error(se, se);
			throw new RuntimeException(se);
		}
  }

  /**
   * Return total number of users stored in the Liferay database.
   * There is no filter by companyId, hence the user count across all portal instances is returned.
   * @param token The authenticated user token
   * @return
   */
  public static int getUsersCount(AuthToken token) {
		try {
			MethodKey methodKey = new MethodKey(com.liferay.portal.service.UserLocalServiceUtil.class,
					"getUsersCount", TYPE_VOID);
			MethodHandler methodHandler = new MethodHandler(methodKey);
			return (int)RPCTunneling.invoke(token.getPrincipal(), methodHandler);
		}
		catch (PortalException | SystemException ex) {
      LOG.error(ex, ex);
      throw new RuntimeException(ex);
    }
  }
  
  private static String determineAuthType(String login) {
    return login.contains("@") 
              ? CompanyConstants.AUTH_TYPE_EA 
              : isNumber(login) 
                  ? CompanyConstants.AUTH_TYPE_ID
                  : CompanyConstants.AUTH_TYPE_SN;
  }
  
  private static boolean isNumber(String s) {
    Long number = null;
    try {
      number = Long.parseLong(s);
    } catch (NumberFormatException e) {}
    return number != null;
  }

	private static final Class<?>[] TYPE_VOID = new Class[] {
	}; 
	private static final Class<?>[] TYPE_LONG_LONG_STRING = new Class[] {
			long.class, long.class, String.class
	}; 
	private static final Class<?>[] TYPE_LONG_LONG_STRING_FILE = new Class[] {
			long.class, long.class, String.class, File.class
	};
  private static final Class<?>[] TYPE_LONG_STRING = new Class[] {
    long.class, String.class
  };
  private static final Class<?>[] TYPE_STRING = new Class[] {
    String.class
  };
  private static final Class<?>[] TYPE_LONG = new Class[] {
    long.class
  };
  
	private static final Log LOG = LogFactoryUtil.getLog(RPCExtensionsHttp.class);
  
}
