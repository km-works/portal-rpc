/*
 * Copyright (C) 2013 Christian P. Lerch <christian.p.lerch [at] gmail.com>
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *	!!! TODO: REVIEW + TEST !!!
 */

package com.liferay.portal.util;

///-import com.liferay.portal.configuration.ConfigurationImpl;
import org.kmworks.liferay.portal.configuration.RemoteClientConfiguration;	///+
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;


/**
 * A simplified stand-alone implementation of Liferay's PropsUtil
 * which does not depend on any external libraries (like EasyConfig, etc)
 * and should be suffient for this remote client library.
 * 
 * @author Christian P. Lerch
 */
public class PropsUtil {
  
	private static final Log log_ = LogFactoryUtil.getLog(PropsUtil.class);
  
	private static final PropsUtil instance_ = new PropsUtil();

	private final Configuration configuration_ = new RemoteClientConfiguration();
  
	private PropsUtil() {

    InputStream is = PropsUtil.class.getResourceAsStream("/portal.properties");
    if (is != null) {
      Properties portalProps = new Properties();
      try {
        portalProps.load(is);
      } catch (IOException ex) {
        log_.error("Unable to read from file portal.properties", ex);
        throw new RuntimeException(ex);
      }
      _addProperties(portalProps);
    }
    
    // We always want exactly these settings, 'cause it will work on JVMs without unlimitted strength encryption too
    configuration_.set(PropsKeys.COMPANY_ENCRYPTION_ALGORITHM, "AES");
    configuration_.set(PropsKeys.COMPANY_ENCRYPTION_KEY_SIZE,  "128");
    
    // Allow the other side (i.e. portal-service.jar) to read from our properties
    com.liferay.portal.kernel.util.PropsUtil.setProps(new PropsImpl());
  }
  
	private void _addProperties(Properties properties) {
		_getConfig().addProperties(properties);
	}

	private void _addProperties(UnicodeProperties unicodeProperties) {
		Properties properties = new Properties();

		properties.putAll(unicodeProperties);

		_addProperties(properties);
	}

	private boolean _contains(String key) {
		return _getConfig().contains(key);
	}

	private String _get(String key) {
		return _getConfig().get(key);
	}

	private String _get(String key, Filter filter) {
		return _getConfig().get(key, filter);
	}

	private String[] _getArray(String key) {
		return _getConfig().getArray(key);
	}

	private String[] _getArray(String key, Filter filter) {
		return _getConfig().getArray(key, filter);
	}

	private Configuration _getConfig() {
		return configuration_;
	}
  
	private Properties _getProperties() {
		return _getConfig().getProperties();
	}

	private Properties _getProperties(String prefix, boolean removePrefix) {
		return _getConfig().getProperties(prefix, removePrefix);
	}

	private void _removeProperties(Properties properties) {
		_getConfig().removeProperties(properties);
	}

	private void _set(String key, String value) {
		_getConfig().set(key, value);
	}
  
  /*
      Public Static Accessors
  */

  public static void addProperties(Properties properties) {
		instance_._addProperties(properties);
  }
    
	public static void addProperties(UnicodeProperties unicodeProperties) {
		instance_._addProperties(unicodeProperties);
	}

	public static boolean contains(String key) {
		return instance_._contains(key);
	}

	public static String get(String key) {
		return instance_._get(key);
	}

	public static String get(String key, Filter filter) {
		return instance_._get(key, filter);
	}

	public static String[] getArray(String key) {
		return instance_._getArray(key);
	}

	public static String[] getArray(String key, Filter filter) {
		return instance_._getArray(key, filter);
	}

	public static Properties getProperties() {
		return instance_._getProperties();
	}

	public static Properties getProperties(String prefix, boolean removePrefix) {
		return instance_._getProperties(prefix, removePrefix);
	}

	public static void removeProperties(Properties properties) {
		instance_._removeProperties(properties);
	}

	public static void set(String key, String value) {
		instance_._set(key, value);
	}

}