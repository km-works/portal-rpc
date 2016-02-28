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
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import org.kmworks.liferay.rpc.Configuration;
import org.kmworks.liferay.rpc.client.RPCExtensionsHttp;
import org.kmworks.liferay.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public final class DLFileEntryLocalServiceUtil {
   
  private DLFileEntryLocalServiceUtil() {}

  public static DLFileEntry addDLFileEntry(DLFileEntry dlFileEntry) 
          throws SystemException {
    return (DLFileEntry)RPCTunneling.invoke(SELF, "addDLFileEntry", 
            new Class[] { DLFileEntry.class }, 
            dlFileEntry);
  }

  public static DLFileEntry deleteDLFileEntry(long fileEntryId) 
          throws SystemException {
    return (DLFileEntry)RPCTunneling.invoke(SELF, "deleteDLFileEntry", 
            new Class[] { long.class }, 
            fileEntryId);
  }

  public static DLFileEntry fetchFileEntryByAnyImageId(long imageId) 
          throws SystemException {
    return (DLFileEntry)RPCTunneling.invoke(SELF, "fetchFileEntryByAnyImageId", 
            new Class[] { long.class }, 
            imageId);
  }

  public static DLFileEntry getDLFileEntry(long fileEntryId) 
          throws SystemException {
    return (DLFileEntry)RPCTunneling.invoke(SELF, "getDLFileEntry", 
            new Class[] { long.class }, 
            fileEntryId);
  }

  public static DLFileEntry getFileEntry(long fileEntryId) 
          throws SystemException {
    return getDLFileEntry(fileEntryId);
  }
  
  public static File getFile(long userId, long fileEntryId, String version, boolean incrementCounter) 
          throws SystemException {
    return (File)RPCTunneling.invoke(SELF, "getFile", 
            new Class[] { long.class, long.class, String.class, boolean.class }, 
            userId, fileEntryId, version, incrementCounter);
  }

  public static DLFileEntry updateDLFileEntry(DLFileEntry dlFileEntry) 
          throws SystemException {
    return (DLFileEntry)RPCTunneling.invoke(SELF, "updateDLFileEntry", 
            new Class[] { DLFileEntry.class }, 
            dlFileEntry);
  }

  public  static List<DLFileEntry> 	getFileEntries(long groupId, long folderId) 
          throws SystemException {
    return (List<DLFileEntry>)RPCTunneling.invoke(SELF, "getFileEntries", 
            new Class[] { long.class, long.class }, 
            groupId, folderId);
  }
  
  public static InputStream getFileAsStream(long userId, long fileEntryId, String version, boolean incrementCounter) 
          throws PortalException, SystemException {
    return RPCExtensionsHttp.getFileAsStream(Configuration.getToken(), fileEntryId, version);
  }

  private static final Class<?> SELF = DLFileEntryLocalServiceUtil.class;
}
