/*
 * Copyright (C) 2005-2014 Christian P. Lerch <christian.p.lerch [at] gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.liferay.portal.service.http;

import org.kmworks.liferay.rpc.Configuration;
import org.kmworks.liferay.rpc.utils.AuthToken;

/*
 * Set Maven option -DfailIfNoTests=false to ignore test results for building from sources
 * 
 * @author Christian P. Lerch
 */
public class HttpTestUtil {
  
  public static final String SHARED_SECRET = "A8B7C6D5E4F3G2H1";   // change this to match the config of your portal server
  
  public static final String PORTAL_URI = "http://dev.portal.amorc.de:8080"; // portal instance base URI:
                                                                    // you must provide the actual virtual hostname here
  public static final String USER_NAME = "admin";                  // test user login name; could also be e-mail, or user id
                                                                    // as configured in your basic authentication settings
  public static final String USER_PWD = "manager";                 // test user plain-text password

  protected void configure() {
    Configuration.initialize(PORTAL_URI, USER_NAME, USER_PWD, SHARED_SECRET);
  }
  
  protected AuthToken getToken() {
    return Configuration.getToken();
  }
  
  protected void pln(String s) {
    System.out.println(s);
  }
  
}
