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
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import java.util.List;
import org.kmworks.liferay.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public final class DLFileVersionLocalServiceUtil {
  
  private DLFileVersionLocalServiceUtil() {}
    
  public static DLFileVersion addDLFileVersion(DLFileVersion dlFileVersion) 
          throws SystemException {
    return (DLFileVersion)RPCTunneling.invoke(TGT, "addDLFileVersion", 
            new Class[] { DLFileVersion.class }, 
            dlFileVersion);    
  }
          
  public static DLFileVersion getFileVersion(long fileVersionId) 
          throws SystemException {
    return (DLFileVersion)RPCTunneling.invoke(TGT, "getFileVersion", 
            new Class[] { long.class }, 
            fileVersionId);    
  }
  
  public static DLFileVersion getFileVersion(long fileEntryId, String version) 
          throws SystemException {
    return (DLFileVersion)RPCTunneling.invoke(TGT, "getFileVersion", 
            new Class[] { long.class, String.class }, 
            fileEntryId, version);    
  }
  
  public static List<DLFileVersion> getFileVersions(long fileEntryId, int status)
          throws SystemException {
    return (List<DLFileVersion>)RPCTunneling.invoke(TGT, "getFileVersions", 
            new Class[] { long.class, int.class }, 
            fileEntryId, status);    
  }
  
  public static int getFileVersionsCount(long fileEntryId, int status)
          throws SystemException {
    return (Integer)RPCTunneling.invoke(TGT, "getFileVersionsCount", 
            new Class[] { long.class, int.class }, 
            fileEntryId, status);    
  }
  
  public static DLFileVersion getLatestFileVersion(long fileEntryId, boolean excludeWorkingCopy)
          throws SystemException {
    return (DLFileVersion)RPCTunneling.invoke(TGT, "getLatestFileVersion", 
            new Class[] { long.class, boolean.class }, 
            fileEntryId, excludeWorkingCopy);    
  }
  
  public static DLFileVersion updateDLFileVersion(DLFileVersion dlFileVersion) 
          throws SystemException {
    return (DLFileVersion)RPCTunneling.invoke(TGT, "updateDLFileVersion", 
            new Class[] { DLFileVersion.class }, 
            dlFileVersion);    
  }

  private static final Class<?> TGT = DLFileVersionLocalServiceUtil.class;
}
