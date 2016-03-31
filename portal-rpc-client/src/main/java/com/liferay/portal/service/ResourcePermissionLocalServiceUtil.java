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
package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ResourcePermission;
import java.util.Collection;
import java.util.List;
import org.kmworks.portal.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public class ResourcePermissionLocalServiceUtil {
  
  private ResourcePermissionLocalServiceUtil() {}
  
  
  public static ResourcePermission addResourcePermission(ResourcePermission resourcePermission)
		throws SystemException {
    return (ResourcePermission)RPCTunneling.invoke(SELF, "addResourcePermission", 
            new Class[] { ResourcePermission.class }, 
            resourcePermission);
	}
  
  public static List<String> getAvailableResourcePermissionActionIds(
           long companyId, 
           String name, 
           int scope, 
           String primKey, 
           long roleId, 
           Collection<String> actionIds) 
          throws SystemException {
    return (List<String>)RPCTunneling.invoke(SELF, "getAvailableResourcePermissionActionIds", 
            new Class[] { long.class, String.class, int.class, String.class, long.class, Collection.class }, 
            companyId, name, scope, primKey, roleId, actionIds);
  }

  public static void setResourcePermissions(
          long companyId, 
          String name, 
          int scope, 
          String primKey, 
          long roleId, 
          String[] actionIds) 
          throws SystemException {
    RPCTunneling.invoke_void(SELF, "setResourcePermissions", 
            new Class[] { long.class, String.class, int.class, String.class, long.class, String[].class }, 
            companyId, name, scope, primKey, roleId, actionIds);
  }

  
  public static ResourcePermission updateResourcePermission(ResourcePermission resourcePermission)
		throws SystemException {
    return (ResourcePermission)RPCTunneling.invoke(SELF, "updateResourcePermission", 
            new Class[] { ResourcePermission.class }, 
            resourcePermission);
	}
  
  private static final Class<?> SELF = ResourcePermissionLocalServiceUtil.class;
}
