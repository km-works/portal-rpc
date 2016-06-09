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
package org.kmworks.portal.rpc;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.util.PropsUtil;
import java.util.concurrent.TimeUnit;
import org.kmworks.portal.rpc.utils.AuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    initialize(portalURI, userName, userPwd, secret, 0);
  }
  
  public static boolean initialize(
          String portalURI,
          String userName,
          String userPwd,
          String secret,
          int waitForServerStartupTimeoutSec) {
    return initialize(new HttpPrincipal(portalURI, userName, userPwd), secret, waitForServerStartupTimeoutSec);
  }
  
  private static boolean initialize(HttpPrincipal principal, String secret, int waitForServerStartupTimeoutSec) {
    // set shared secret first!
    sharedSecret = secret;
    PropsUtil.set(PropsKeys.TUNNELING_SERVLET_SHARED_SECRET, sharedSecret);
    // create the authetication token
    authToken = new AuthToken(principal);
    // try to retrieve associated user id
    long userId = getUserIdWaitFor(authToken, waitForServerStartupTimeoutSec);
    // setup PrincipalThreadLocal to satisfy Liferay model classes
    if (userId != 0L) {
      PrincipalThreadLocal.setName(userId);
      PrincipalThreadLocal.setPassword(principal.getPassword());
      return true;
    } else {
      return false;
    }
  }
  
  private static long getUserIdWaitFor(AuthToken authToken, int seconds) {
    if (seconds == 0L) {
      // requires Liferay to be up and running; if not throws exception:
      // com.liferay.portal.kernel.exception.SystemException: java.net.ConnectException: Connection refused: connect
      return authToken.getUserId();  
    } else {
      // waits for Liferay to be up an running for given number of seconds
      // returns 0L on timeout
      long result = 0L;
      // turn logging off temporarily
      //Level currLevel = Logger.getGlobal().getLevel();
      //Logger.getGlobal().setLevel(Level.OFF);
      while (seconds > 0L) {
        try {
          result = authToken.getUserId();
          break;
        } catch (Exception e) {
          seconds -= 1;
        }
        try { TimeUnit.SECONDS.sleep(1); } catch (Exception e) {}
      }
      // turn logging on again
      //Logger.getGlobal().setLevel(currLevel);
      return result;
    }
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
  
  private static final Class<?> SELF = Configuration.class;
  private static final Logger LOG = LoggerFactory.getLogger(SELF);
}
