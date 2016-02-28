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

import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.service.http.HttpTestUtil;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.kmworks.liferay.rpc.Configuration;

/**
 *
 * @author cpl
 */
public class UserLocalServiceUtilTest {

  public UserLocalServiceUtilTest() {
  }
  
  @BeforeClass
  public static void initialize() {
        Configuration.initialize(
            HttpTestUtil.PORTAL_URI, 
            HttpTestUtil.USER_NAME, 
            HttpTestUtil.USER_PWD, 
            HttpTestUtil.SHARED_SECRET);
  }
  
  @Test
  public void testUpdateUser() throws Exception {
    final long userId = 25964L;
    final User lpUser = UserLocalServiceUtil.getUser(userId);
    assertEquals(userId, lpUser.getUserId());
    lpUser.setLastName("Zwei");
    pln(lpUser.getScreenName());
    pln(UserImpl.class.cast(lpUser).getOriginalEmailAddress());
    assertTrue(lpUser instanceof UserImpl);
    final UserImpl userImpl = (UserImpl)lpUser;
    assertEquals(lpUser.getEmailAddress(), userImpl.getOriginalEmailAddress());
    final User lpUser2 = UserLocalServiceUtil.updateUser(lpUser);
    assertEquals(lpUser, lpUser2);
  }
  
  private void pln(String s) {
    System.out.println(s);
  }

}
