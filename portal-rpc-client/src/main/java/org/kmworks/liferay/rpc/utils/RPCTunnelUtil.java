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
package org.kmworks.liferay.rpc.utils;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.http.TunnelUtil;
import static com.liferay.portal.service.http.TunnelUtil.getSharedSecretKey;
import com.liferay.util.Encryptor;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import org.kmworks.portal.rpc.io.HttpUtil;

/**
 *
 * @author Christian P. Lerch
 */
public class RPCTunnelUtil extends TunnelUtil {

	public static Object invoke(HttpPrincipal httpPrincipal, MethodHandler methodHandler)
		throws Exception {

    HttpPrincipal principal = AuthToken.clonePrincipal(httpPrincipal);
    principal.setPassword(Encryptor.encrypt(getSharedSecretKey(), principal.getLogin()));

    final HttpURLConnection httpURLConnection = HttpUtil.getConnection(
            principal.getUrl() + "/rpc-server-hook/rpc/api/do", 
            principal.getLogin(), 
            principal.getPassword(),
            ContentTypes.APPLICATION_X_JAVA_SERIALIZED_OBJECT);
    
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(httpURLConnection.getOutputStream())) {
      ObjectValuePair<HttpPrincipal, MethodHandler> ovp = new ObjectValuePair<>(httpPrincipal, methodHandler);
      objectOutputStream.writeObject(ovp);
      objectOutputStream.flush();
    }
    
		Object returnObject = null;
		try (ObjectInputStream objectInputStream = new ObjectInputStream(httpURLConnection.getInputStream())) {
        returnObject = objectInputStream.readObject();
		} catch (EOFException eofe) {
			if (_LOG.isDebugEnabled()) {
				_LOG.debug("Unable to read object from object stream", eofe);
			}
		} catch (IOException ioe) {
			String ioeMessage = ioe.getMessage();
			if (ioeMessage != null && ioeMessage.contains("HTTP response code: 401")) {
				throw new PrincipalException(ioeMessage);
			} else {
				throw ioe;
			}
		}

		if (returnObject != null && returnObject instanceof Exception) {
			throw (Exception)returnObject;
		}

		return returnObject;
	}

  /*
	private static HttpURLConnection _getConnection(String baseUrl, String login, String password, String contentType)
		throws IOException {

    checkNotNull(baseUrl);
    checkNotNull(login);
    checkNotNull(password);

///-URL url = new URL(httpPrincipal.getUrl() + "/api/liferay/do");
		final URL url = new URL(baseUrl);  ///+
		HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);

		if (!_VERIFY_SSL_HOSTNAME && httpURLConnection instanceof HttpsURLConnection) {
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection)httpURLConnection;
      
			httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {

					@Override
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				}
			);
		}

		httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, contentType);
		httpURLConnection.setUseCaches(false);
		httpURLConnection.setRequestMethod(HttpMethods.POST);

    final String userNameAndPassword = login + StringPool.COLON + password;
    httpURLConnection.setRequestProperty(HttpHeaders.AUTHORIZATION,
      HttpServletRequest.BASIC_AUTH + StringPool.SPACE + Base64.encode(userNameAndPassword.getBytes()));

		return httpURLConnection;
	}

	private static final boolean _VERIFY_SSL_HOSTNAME = GetterUtil.getBoolean(
		PropsUtil.get(TunnelUtil.class.getName() + ".verify.ssl.hostname"));

  */
  
	private static final Log _LOG = LogFactoryUtil.getLog(RPCTunnelUtil.class);

}
