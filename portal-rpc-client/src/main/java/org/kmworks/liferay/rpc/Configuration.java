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
package org.kmworks.liferay.rpc;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.util.PropsUtil;
import org.kmworks.liferay.rpc.utils.AuthToken;

/**
 *
 * @author cpl
 */
public final class Configuration {
  
  private static AuthToken authToken;
  private static String sharedSecret;
  
  private Configuration() {}
  
  public static void initialize(
          String portalURI,
          String userName,
          String userPwd,
          String secret) {
    initialize(new HttpPrincipal(portalURI, userName, userPwd), secret);
  }
  
  public static void initialize(HttpPrincipal principal, String secret) {
    // set shared secret first!
    sharedSecret = secret;
    PropsUtil.set(PropsKeys.TUNNELING_SERVLET_SHARED_SECRET, sharedSecret);
    // create the authetication token
    authToken = new AuthToken(principal);
    // setup PrincipalThreadLocal to satisfy model classes
    PrincipalThreadLocal.setName(authToken.getUserId());
    PrincipalThreadLocal.setPassword(principal.getPassword());
  }
  
  public static boolean isValid() {
    return authToken != null && sharedSecret != null && sharedSecret.length() == 16;
  }
  
  public static void checkValid() {
    if (!isValid()) throw new RuntimeException("Invalid configuration");
  }
  
  public static HttpPrincipal getPrincipal() {
    return getToken().getPrincipal();
  }
  
  public static AuthToken getToken() {
    authToken.ensureCompanyId();
    return authToken;
  }
  
  public static String getSharedSecret() {
    return sharedSecret;
  }
   
}
