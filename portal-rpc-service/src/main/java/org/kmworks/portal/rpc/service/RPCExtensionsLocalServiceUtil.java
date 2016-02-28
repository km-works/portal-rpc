/*
 * Copyright (C) 2005-2014 Christian P. Lerch <christian.p.lerch [at] gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kmworks.portal.rpc.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RPCExtensionsLocalServiceUtil {
  
  /** Autheniticate a user's credentials against the Liferay user definitions.
   * @param companyId The id of the portal instance containing the user
   * @param authType the basic authentication method configured in the target portal: "emailAddress" | "screenName" | "userId"
   * @param login The login string according to authType
   * @param password The plain-text (unencrypted, undigested) user password
   * @return The userId on successful authentication, Authenticator.FAILURE otherwise
   * @throws PortalException
   * @throws SystemException 
   */
  public static Long authenticateUser(
          long companyId,
          String authType,
          String login,
          String password)
      throws PortalException, SystemException {
    return com.liferay.portal.service.UserLocalServiceUtil.authenticateForBasic(companyId, authType, login, password);
  }
  
  /** Download a FileEntry's file content to the filesystem of the server
   * 
   * @param userId
   * @param fileEntryId
   * @param version
   * @param dstFile
   * @return number of bytes downloaded
   * @throws PortalException
   * @throws SystemException 
   */
  public static Long downloadFileToServer(
          long userId, 
          long fileEntryId, 
          String version, 
          File dstFile)
			throws PortalException, SystemException {
    
    long byteCount;
    try (InputStream in = DLFileEntryLocalServiceUtil.getFileAsStream(userId, fileEntryId, version); 
      OutputStream out = new FileOutputStream(dstFile)) {
      byteCount = copy(in, out);
    } catch (IOException e) {
      throw new SystemException(e);
    }
    return byteCount;
  }

	/** Return a FileEntry's file content as a (serializable) byte array
	 * 
   * @param userId
   * @param fileEntryId
   * @param version
   * @return byte array filled with file content
   * @throws com.liferay.portal.kernel.exception.PortalException
   * @throws com.liferay.portal.kernel.exception.SystemException
	 */
  public static byte[] getFileAsBytes(
          long userId, 
          long fileEntryId, 
          String version)
			throws PortalException, SystemException {
	  
    byte[] result;
    try (InputStream in = DLFileEntryLocalServiceUtil.getFileAsStream(userId, fileEntryId, version)) {
      result = toByteArray(in);
    } catch (IOException e) {
      throw new SystemException(e);
    }
	  return result;
	}
  
  /*
      Private Helper Methods
  */
  private static byte[] toByteArray(InputStream in) throws IOException {
	  
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    copy(in, out);
      out.flush();
      out.close();
	    return out.toByteArray();
	  }
	  
  private static final int BUF_SIZE = 0x1000; // 4K
	  
  /**
   * Copies all bytes from the input stream to the output stream.
   * Does not close or flush either stream.
   *
   * @param from the input stream to read from
   * @param to the output stream to write to
   * @return the number of bytes copied
   * @throws IOException if an I/O error occurs
   */
  private static long copy(InputStream from, OutputStream to)
      throws IOException {
    byte[] buf = new byte[BUF_SIZE];
    long total = 0;
    while (true) {
      int r = from.read(buf);
      if (r == -1) {
        break;
      }
      to.write(buf, 0, r);
      total += r;
    }
    return total;
  }
}
