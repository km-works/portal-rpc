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
import com.liferay.portal.model.Group;
import java.util.List;
import java.util.Locale;
import org.kmworks.portal.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public class GroupLocalServiceUtil {
  
	public static Group addGroup(Group group)
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "addGroup", 
            new Class[] { Group.class }, 
            group);
	}

  public static Group deleteGroup(long groupId) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "deleteGroup", 
            new Class[] { long.class }, 
            groupId);
  }

  public static Group deleteGroup(Group group) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "deleteGroup", 
            new Class[] { Group.class }, 
            group);
  }

  public static Group fetchGroup(long groupId)
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "fetchGroup", 
            new Class[] { long.class }, 
            groupId);
  }
  
  public static Group fetchGroup(long companyId, String name) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "fetchGroup", 
            new Class[] { long.class, String.class }, 
            companyId, name);
  }

  public static Group getCompanyGroup(long companyId)
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getCompanyGroup", 
            new Class[] { long.class }, 
            companyId);
  }
  
  public static Group getGroup(long groupId)
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getGroup", 
            new Class[] { long.class }, 
            groupId);
  }
  
  public static Group getGroup(long companyId, String name) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getGroup", 
            new Class[] { long.class, String.class }, 
            companyId, name);
  }

  public static String getGroupDescriptiveName(Group group, Locale locale) 
          throws SystemException {
    return (String)RPCTunneling.invoke(SELF, "getGroupDescriptiveName", 
            new Class[] { Group.class, Locale.class }, 
            group, locale);
  }

  public static List<Group> getGroups(long companyId, long parentGroupId, boolean site) 
          throws SystemException {
    return (List<Group>)RPCTunneling.invoke(SELF, "getGroups", 
            new Class[] { long.class, long.class, boolean.class }, 
            companyId, parentGroupId, site);
  }

  public static int getGroupsCount() 
          throws SystemException {
    return (Integer)RPCTunneling.invoke(SELF, "getGroupsCount", 
            new Class[] { } 
            );
  }

  public static Group getLayoutGroup(long companyId, long plid) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getLayoutGroup", 
            new Class[] { long.class, long.class }, 
            companyId, plid);
  }

  public static Group getLayoutPrototypeGroup(long companyId, long layoutPrototypeId) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getLayoutPrototypeGroup", 
            new Class[] { long.class, long.class }, 
            companyId, layoutPrototypeId);
  }

  public static Group getLayoutSetPrototypeGroup(long companyId, long layoutSetPrototypeId) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getLayoutSetPrototypeGroup", 
            new Class[] { long.class, long.class }, 
            companyId, layoutSetPrototypeId);
  }

  public static List<Group> getLayoutsGroups(long companyId, long parentGroupId, boolean site, int start, int end) 
          throws SystemException {
    return (List<Group>)RPCTunneling.invoke(SELF, "getLayoutsGroups", 
            new Class[] { long.class, long.class, boolean.class, int.class, int.class }, 
            companyId, parentGroupId, site, start, end);
  }

  public static int getLayoutsGroupsCount(long companyId, long parentGroupId, boolean site) 
          throws SystemException {
    return (Integer)RPCTunneling.invoke(SELF, "getLayoutsGroupsCount", 
            new Class[] { long.class, long.class, boolean.class }, 
            companyId, parentGroupId, site);
  }

  public static Group getOrganizationGroup(long companyId, long organizationId) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getOrganizationGroup", 
            new Class[] { long.class, long.class }, 
            companyId, organizationId);
  }

  public static Group getStagingGroup(long liveGroupId) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getStagingGroup", 
            new Class[] { long.class }, 
            liveGroupId);
  }

  public static Group getUserGroup(long companyId, long userId) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getUserGroup", 
            new Class[] { long.class, long.class }, 
            companyId, userId);
  }

  public static Group getUserGroupGroup(long companyId, long userGroupId) 
          throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "getUserGroupGroup", 
            new Class[] { long.class, long.class }, 
            companyId, userGroupId);
  }

  public static List<Group> getUserGroups(long userId) 
          throws SystemException {
    return (List<Group>)RPCTunneling.invoke(SELF, "getUserGroups", 
            new Class[] { long.class }, 
            userId);
  }

  public static List<Group> getUserSitesGroups(long userId) 
          throws SystemException {
    return (List<Group>)RPCTunneling.invoke(SELF, "getUserSitesGroups", 
            new Class[] { long.class }, 
            userId);
  }

  public static List<Group> getUserSitesGroups(long userId, boolean includeAdministrative) 
          throws SystemException {
    return (List<Group>)RPCTunneling.invoke(SELF, "getUserSitesGroups", 
            new Class[] { long.class, boolean.class }, 
            userId, includeAdministrative);
  }

  public static boolean hasStagingGroup(long liveGroupId) 
          throws SystemException {
    return (Boolean)RPCTunneling.invoke(SELF, "hasStagingGroup", 
            new Class[] { long.class }, 
            liveGroupId);
  }

	public static Group updateGroup(Group group)
		throws SystemException {
    return (Group)RPCTunneling.invoke(SELF, "updateGroup", 
            new Class[] { Group.class }, 
            group);
	}

  private static final Class<?> SELF = GroupLocalServiceUtil.class;
}
