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
import com.liferay.portal.model.User;
import java.util.List;
import java.util.Locale;
import org.kmworks.liferay.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public class UserLocalServiceUtil {

  public static User addUser(User user)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "addUser",
            new Class[]{User.class},
            user);
  }

  public static User addUser(
          long creatorUserId,
          long companyId,
          boolean autoPassword,
          String password1,
          String password2,
          boolean autoScreenName,
          String screenName,
          String emailAddress,
          long facebookId,
          String openId,
          Locale locale,
          String firstName,
          String middleName,
          String lastName,
          int prefixId,
          int suffixId,
          boolean male,
          int birthdayMonth,
          int birthdayDay,
          int birthdayYear,
          String jobTitle,
          long[] groupIds,
          long[] organizationIds,
          long[] roleIds,
          long[] userGroupIds,
          boolean sendEmail,
          ServiceContext context)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "addUser",
            new Class[]{
              long.class,
              long.class,
              boolean.class,
              String.class,
              String.class,
              boolean.class,
              String.class,
              String.class,
              long.class,
              String.class,
              Locale.class,
              String.class,
              String.class,
              String.class,
              int.class,
              int.class,
              boolean.class,
              int.class,
              int.class,
              int.class,
              String.class,
              long[].class,
              long[].class,
              long[].class,
              long[].class,
              boolean.class,
              ServiceContext.class
            },
            creatorUserId,
            companyId,
            autoPassword,
            password1,
            password2,
            autoScreenName,
            screenName,
            emailAddress,
            facebookId,
            openId,
            locale,
            firstName,
            middleName,
            lastName,
            prefixId,
            suffixId,
            male,
            birthdayMonth,
            birthdayDay,
            birthdayYear,
            jobTitle,
            groupIds,
            organizationIds,
            roleIds,
            userGroupIds,
            sendEmail,
            context);
  }

  public static void addUserGroupUser(long userGroupId, long userId) 
          throws SystemException {
    RPCTunneling.invoke_void(SELF, "addUserGroupUser",
            new Class[]{ long.class, long.class },
            userGroupId, userId);
  }
  
  public static User deleteUser(User user)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "deleteUser",
            new Class[]{User.class},
            user);
  }

  public static User deleteUserByScreenName(long companyId, String screenName)
          throws SystemException {
    final User user = getUserByScreenName(companyId, screenName);
    return deleteUser(user);
  }
  
  public static int getCompanyUsersCount(long companyId)
          throws SystemException {
    return (Integer) RPCTunneling.invoke(SELF, "getCompanyUsersCount",
            new Class[]{long.class},
            companyId);
  }

  public static List<User> getCompanyUsers(long companyId, int start, int end)
          throws SystemException {
    return (List<User>) RPCTunneling.invoke(SELF, "getCompanyUsers",
            new Class[]{long.class, int.class, int.class},
            companyId, start, end);
  }

  public static User getDefaultUser(long companyId)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "getDefaultUser",
            new Class[]{long.class},
            companyId);
  }

  public static User getUser(long userId)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "getUser",
            new Class[]{long.class},
            userId);
  }

  public static User getUserByEmailAddress(long companyId, String emailAddress)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "getUserByEmailAddress",
            new Class[]{long.class, String.class},
            companyId, emailAddress);
  }

  public static User getUserById(long userId)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "getUserById",
            new Class[]{long.class},
            userId);
  }

  public static User getUserByScreenName(long companyId, String screenName)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "getUserByScreenName",
            new Class[]{long.class, String.class},
            companyId, screenName);
  }
  
  public static void unsetUserGroupUsers(long userGroupId, long[] userIds)
          throws SystemException {
    RPCTunneling.invoke_void(SELF, "unsetUserGroupUsers",
            new Class[]{ long.class, long[].class },
            userGroupId, userIds);
  }

  static User updateIncompleteUser(
          long creatorUserId,
          long companyId,
          boolean autoPassword,
          String password1,
          String password2,
          boolean autoScreenName,
          String screenName,
          String emailAddress,
          long facebookId,
          String openId,
          Locale locale,
          String firstName,
          String middleName,
          String lastName,
          int prefixId,
          int suffixId,
          boolean male,
          int birthdayMonth,
          int birthdayDay,
          int birthdayYear,
          String jobTitle,
          boolean updateUserInformation,
          boolean sendEmail,
          ServiceContext serviceContext)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "updateIncompleteUser",
            new Class[]{
              long.class,
              long.class,
              boolean.class,
              String.class,
              String.class,
              boolean.class,
              String.class,
              String.class,
              long.class,
              String.class,
              Locale.class,
              String.class,
              String.class,
              String.class,
              int.class,
              int.class,
              boolean.class,
              int.class,
              int.class,
              int.class,
              String.class,
              boolean.class,
              boolean.class,
              ServiceContext.class
            },
            creatorUserId,
            companyId,
            autoPassword,
            password1,
            password2,
            autoScreenName,
            screenName,
            emailAddress,
            facebookId,
            openId,
            locale,
            firstName,
            middleName,
            lastName,
            prefixId,
            suffixId,
            male,
            birthdayMonth,
            birthdayDay,
            birthdayYear,
            jobTitle,
            updateUserInformation,
            sendEmail,
            serviceContext);
  }

  public static User updateUser(User user)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "updateUser",
            new Class[]{User.class},
            user);
  }

  public static User updatePassword(long userId, String password1, String password2, boolean passwordReset)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "updatePassword",
            new Class[]{long.class, String.class, String.class, String.class},
            userId, password1, password2, passwordReset);
  }

  public static User updateStatus(long userId, int status, ServiceContext serviceContext)
          throws SystemException {
    return (User) RPCTunneling.invoke(SELF, "updatePassword",
            new Class[]{long.class, int.class, ServiceContext.class},
            userId, status, serviceContext);
  }

  private static final Class<?> SELF = UserLocalServiceUtil.class;
}
