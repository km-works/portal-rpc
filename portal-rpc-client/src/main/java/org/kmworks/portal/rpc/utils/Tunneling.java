/*
 * Copyright (C) 2005-2014 Christian P. Lerch <christian.p.lerch [at] gmail [dot] com>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution. If not, see <http://www.gnu.org/licenses/>.
 */
package org.kmworks.portal.rpc.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

/** A convenience wrapper around TunnelUtil that provides improved exception handling.
 *
 * @author Christian P. Lerch
 */
public final class Tunneling {

  private Tunneling() {
  }

  public static Object invoke(HttpPrincipal httpPrincipal, MethodHandler methodHandler)
          throws PortalException, SystemException {

    Object result = null;

    try {
      result = TunnelUtil.invoke(httpPrincipal, methodHandler);
    } catch (Exception e) {
      if (e instanceof PortalException) {
        throw (PortalException) e;
      }
      if (e instanceof SystemException) {
        throw (SystemException) e;
      }
      throw new SystemException(e);
    }

    return result;
  }

  public static void invoke_void(HttpPrincipal httpPrincipal, MethodHandler methodHandler)
          throws PortalException, SystemException {

    try {
      TunnelUtil.invoke(httpPrincipal, methodHandler);
    } catch (Exception e) {
      if (e instanceof PortalException) {
        throw (PortalException) e;
      }
      if (e instanceof SystemException) {
        throw (SystemException) e;
      }
      throw new SystemException(e);
    }

  }

}
