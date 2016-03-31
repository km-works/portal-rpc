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
import com.liferay.portal.model.Role;
import java.util.List;
import org.kmworks.portal.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public class RoleLocalServiceUtil {
  

  public static Role addRole(Role role)  
          throws SystemException {
    return (Role) RPCTunneling.invoke(SELF, "addRole",
            new Class[]{Role.class},
            role);
  }
  
  public static Role getRole(long roleId)  
          throws SystemException {
    return (Role) RPCTunneling.invoke(SELF, "getRole",
            new Class[]{long.class},
            roleId);
  }
  
  public static Role getRole(long companyId, String name)  
          throws SystemException {
    return (Role) RPCTunneling.invoke(SELF, "getRole",
            new Class[]{long.class, String.class},
            companyId, name);
  }
  
  public static Role getTeamRole(long companyId, long teamId)  
          throws SystemException {
    return (Role) RPCTunneling.invoke(SELF, "getTeamRole",
            new Class[]{long.class, long.class},
            companyId, teamId);
  }
  
  public static List<Role> getUserRoles(long userId)  
          throws SystemException {
    return (List<Role>) RPCTunneling.invoke(SELF, "getUserRoles",
            new Class[]{long.class},
            userId);
  }
  
  public static boolean hasUserRole(long userId, long companyId, String name, boolean inherited)  
          throws SystemException {
    return (boolean) RPCTunneling.invoke(SELF, "hasUserRole",
            new Class[]{long.class, long.class, String.class, boolean.class},
            userId, companyId, name, inherited);
  }
  
  public static boolean hasUserRoles(long userId, long companyId, String[] names, boolean inherited)  
          throws SystemException {
    return (boolean) RPCTunneling.invoke(SELF, "hasUserRoles",
            new Class[]{long.class, long.class, String[].class, boolean.class},
            userId, companyId, names, inherited);
  }
  
  public static Role updateRole(Role role)  
          throws SystemException {
    return (Role) RPCTunneling.invoke(SELF, "updateRole",
            new Class[]{Role.class},
            role);
  }
  
  private static final Class<?> SELF = RoleLocalServiceUtil.class;
}
