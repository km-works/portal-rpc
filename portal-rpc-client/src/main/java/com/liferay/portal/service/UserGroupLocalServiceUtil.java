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
import com.liferay.portal.model.UserGroup;
import java.util.List;
import org.kmworks.portal.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public class UserGroupLocalServiceUtil {
  
  public static UserGroup addUserGroup(long userId, long companyId, String name, String description, 
          ServiceContext serviceContext) throws SystemException {
    return (UserGroup)RPCTunneling.invoke(SELF, "addUserGroup", 
            new Class[] { long.class, long.class, String.class, String.class, ServiceContext.class }, 
            userId, companyId, name, description, serviceContext);
  }
  
  public static UserGroup addUserGroup(UserGroup userGroup) 
          throws SystemException {
    return (UserGroup)RPCTunneling.invoke(SELF, "addUserGroup", 
            new Class[] { UserGroup.class }, 
            userGroup);    
  }
  
  public static void addUserUserGroups(long userId, List<UserGroup> userGroups) 
          throws SystemException {
    RPCTunneling.invoke_void(SELF, "addUserUserGroups", 
            new Class[] { long.class, List.class }, 
            userId, userGroups);
  }
  
  public static UserGroup getUserGroup(long userGroupId) 
          throws SystemException {
    return (UserGroup)RPCTunneling.invoke(SELF, "getUserGroup", 
            new Class[] { long.class }, 
            userGroupId);
  }

  public static UserGroup getUserGroup(long companyId, String name) 
          throws SystemException {
    return (UserGroup)RPCTunneling.invoke(SELF, "getUserGroup", 
            new Class[] { long.class, String.class }, 
            companyId, name);
  }

  public static List<UserGroup> getUserGroups(long companyId) 
          throws SystemException {
    return (List<UserGroup>)RPCTunneling.invoke(SELF, "getUserGroups", 
            new Class[] { long.class }, 
            companyId);
  }

  public static List<UserGroup> getUserUserGroups(long userId) 
          throws SystemException {
    return (List<UserGroup>)RPCTunneling.invoke(SELF, "getUserUserGroups", 
            new Class[] { long.class }, 
            userId);
  }

  public static UserGroup updateUserGroup(UserGroup userGroup) 
          throws SystemException {
    return (UserGroup)RPCTunneling.invoke(SELF, "updateUserGroup", 
            new Class[] { UserGroup.class }, 
            userGroup);    
  }
  
  public static void setUserUserGroups(long userId, long[] userGroupIds) 
          throws SystemException {
    RPCTunneling.invoke_void(SELF, "setUserUserGroups", 
            new Class[] { long.class, long[].class }, 
            userId, userGroupIds);    
  }
  
  public static void clearUserUserGroups(long userId) 
          throws SystemException {
    RPCTunneling.invoke_void(SELF, "clearUserUserGroups", 
            new Class[] { long.class }, 
            userId);    
  }
  
  private static final Class<?> SELF = UserGroupLocalServiceUtil.class;
}
