/*
 *  Copyright (C) 2005-2016 Christian P. Lerch <christian.p.lerch [at] gmail.com>
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
package org.kmworks.portal.rpc.io;

import static com.google.common.base.Preconditions.checkNotNull;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.Base64;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author cpl
 */
public final class HttpUtil {
  
  private HttpUtil() {}
  
	public static HttpURLConnection getConnection(String tgtUrl, String loginName, String password, String contentType)
		throws IOException {

    checkNotNull(tgtUrl);
    checkNotNull(loginName);
    checkNotNull(password);
    checkNotNull(contentType);

		final URL url = new URL(tgtUrl);  ///+
    
		final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, contentType);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestMethod(HttpMethods.POST);

    final String userNameAndPassword = loginName + ":" + password;
    httpURLConnection.setRequestProperty(HttpHeaders.AUTHORIZATION,
      HttpServletRequest.BASIC_AUTH + " " + Base64.encode(userNameAndPassword.getBytes()));

		return httpURLConnection;
	}
  
}
