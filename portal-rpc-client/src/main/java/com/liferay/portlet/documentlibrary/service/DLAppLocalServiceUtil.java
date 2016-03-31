/*
 * Copyright (C) 2005-2016 Christian P. Lerch <christian.p.lerch [at] gmail [dot] com>
 * 
 *  This library is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU Lesser General Public License as published by the Free
 *  Software Foundation; either version 3 of the License, or (at your option)
 *  any later version.
 * 
 *  This library is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 *  details.
 * 
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 */
package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.service.ServiceContext;
import org.kmworks.portal.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public final class DLAppLocalServiceUtil {
  
  private DLAppLocalServiceUtil() {}
  
  public static FileEntry addFileEntry(
          long userId,
          long repositoryId,
          long folderId,
          String sourceFileName,
          String mimeType,
          String title,
          String description,
          String changeLog,
          byte[] bytes,
          ServiceContext ctx) throws PortalException, SystemException {
      return (FileEntry)RPCTunneling.invoke(TGT, "addFileEntry", 
              new Class[] { 
                long.class, long.class, long.class, 
                String.class, String.class, String.class, String.class, String.class,
                byte[].class,
                ServiceContext.class
              }, 
              userId, repositoryId, folderId, 
              sourceFileName, mimeType, title, description, changeLog, 
              bytes, 
              ctx);  
  }

  public static Folder getFolder(long folderId) throws SystemException {
    return (Folder)RPCTunneling.invoke(TGT, "getFolder", 
            new Class[] { long.class }, 
            folderId);
  }
  
  public static FileEntry getFileEntry(long fileEntryId) throws PortalException, SystemException {
    return (FileEntry)RPCTunneling.invoke(TGT, "getFileEntry", 
            new Class[] { long.class }, 
            fileEntryId);
  }

  private static final Class<?> TGT = DLAppLocalServiceUtil.class;
}
