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
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.HttpPrincipal;
import java.net.URI;
import java.net.URISyntaxException;
import org.kmworks.portal.rpc.client.RPCExtensionsHttp;

/** 
 *
 * @author Christian P. Lerch
 */
public class AuthToken {
  
  private final HttpPrincipal principal;
  
  private User user = null;
  private Long repositoryId = null;
  
  public AuthToken(HttpPrincipal principal) {
    this.principal = principal;
  }
  
  public HttpPrincipal getPrincipal() {
    return principal;
  }
  
  public String getVirtualHost() throws URISyntaxException {
    return new URI(principal.getUrl()).getHost();
  }
  
  public long getCompanyId() {
    ensureCompanyId();
    return principal.getCompanyId();
  }
  
  public User getUser() {
    ensureCompanyId();
    ensureUser();
    return user;
  }
  
  public long getUserId() {
    return getUser().getUserId();
  }
  
  public long getRepositoryId() throws PortalException, SystemException {
    ensureCompanyId();
    ensureRepositoryId();
    return repositoryId;
  }
  
  public static HttpPrincipal clonePrincipal(HttpPrincipal principal) {
    return new HttpPrincipal(principal.getUrl(), principal.getLogin(), principal.getPassword());
  }
  
  public void ensureCompanyId() {
    if (principal.getCompanyId() == 0L) {
      principal.setCompanyId(RPCExtensionsHttp.getCompanyId(this));
    }
  }
  
  private void ensureUser() {
    if (user == null) {
      user = RPCExtensionsHttp.getUserfromToken(this);
    }    
  }
  
  private void ensureRepositoryId() {
    if (repositoryId == null) {
      repositoryId = RPCExtensionsHttp.getRepositoryId(this);
    }
  }
  
}
