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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import java.util.List;
import org.kmworks.liferay.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public final class DLFolderLocalServiceUtil {
  
  private DLFolderLocalServiceUtil() {}

  public static DLFolder addDLFolder(DLFolder dlFolder)
          throws SystemException {
    return (DLFolder)RPCTunneling.invoke(TGT, "addDLFolder", 
            new Class[] { DLFolder.class }, 
            dlFolder);
  }
  
  public static DLFolder addFolder(
          long userId, 
          long groupId, 
          long repositoryId, 
          boolean mountPoint, 
          long parentFolderId, 
          String name, 
          String description, 
          boolean hidden, 
          ServiceContext serviceContext) 
          throws SystemException {
    return (DLFolder)RPCTunneling.invoke(TGT, "addFolder", 
            new Class[] { 
              long.class, 
              long.class, 
              long.class,
              boolean.class,
              long.class,
              String.class,
              String.class,
              boolean.class,
              ServiceContext.class
            }, 
          userId,
          groupId,
          repositoryId,
          mountPoint,
          parentFolderId,
          name,
          description,
          hidden,
          serviceContext);
  }
  
  public static DLFolder deleteDLFolder(long folderId) 
          throws SystemException {
    return (DLFolder)RPCTunneling.invoke(TGT, "deleteDLFolder", 
            new Class[] { long.class }, 
            folderId);
  }

  public static DLFolder fetchDLFolder(long folderId) 
          throws SystemException {
    return (DLFolder)RPCTunneling.invoke(TGT, "fetchDLFolder", 
            new Class[] { long.class }, 
            folderId);
  }

  public static DLFolder getFolder(long folderId) 
          throws SystemException {
    return (DLFolder)RPCTunneling.invoke(TGT, "getFolder", 
            new Class[] { long.class }, 
            folderId);
  }

  public static List<DLFolder> getFolders(long groupId, long parentId) 
          throws SystemException {
    return (List<DLFolder>)RPCTunneling.invoke(TGT, "getFolders", 
            new Class[] { long.class, long.class }, 
            groupId, parentId);
  }

  public static DLFolder updateDLFolder(DLFolder dlFolder)
          throws SystemException {
    return (DLFolder)RPCTunneling.invoke(TGT, "updateDLFolder", 
            new Class[] { DLFolder.class }, 
            dlFolder);
  }
  
  private static final Class<?> TGT = DLFolderLocalServiceUtil.class;
}
