/*
 * Copyright (C) 2005-2016 Christian P. Lerch <christian.p.lerch [at] gmail [dot] com>
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
package org.kmworks.liferay.portal.configuration;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.Filter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A light-weight implementation of the portal Configuration interface
 * suitable for stand-alone usage. 
 * <p>
 * This class is a direct replacement for 
 * <code>com.liferay.portal.configuration.ConfigurationImpl</code> which 
 * heavily depends on the easyconf library and the arrangements of files 
 * and directories found in web apps and application servers. 
 * <p>
 * In a stand-alone scenario we do not need most of these features and 
 * really wanted to avoid the huge number dependencies which easyconf is 
 * drawing in.
 * 
 * @author Christian P. Lerch
 */
public final class RemoteClientConfiguration implements Configuration {
  
  private final Map<String, String> values_;
  
  public RemoteClientConfiguration() {
    values_ = new HashMap<>();
  }
  
  public RemoteClientConfiguration(Map<String, String> props) {
    values_ = props;
  }

  public RemoteClientConfiguration(Properties props) {
    this();
    addProperties(props);
  }

  /**
   * Add a set of properties to the configuration.
   * 
   * @param props 
   */
  @Override
  public void addProperties(Properties props) {
    for (String key : props.stringPropertyNames()) {
      values_.put(key, props.getProperty(key));
    }
  }

  /**
   * Since there is no caching used by this implementation this method simply 
   * does nothing.
   */
  @Override
  public void clearCache() {
  }

  /**
   * Checks if the configuration contains the given key.
   * 
   * @param key
   * @return    true if key is present, otherwise false
   */
  @Override
  public boolean contains(String key) {
    return get(key) == null;
  }

  /**
   * Return the property value from the given key.
   * 
   * @param key
   * @return    if key is present, the property value as a String,  
   * otherwise null
   */
  @Override
  public String get(String key) {
    String result = values_.get(key);
    return (result == null) ? result : result.trim();
  }

  /**
   * Return the property value from an effective key derived from the key and 
   * filter parameters.
   * The effective key used to retrieve the property value is build from the 
   * expression <code>key + "[" + filter + "]"</code>
   * @param key
   * @param filter
   * @return  if effective key is present, the property value as a String, 
   * otherwise null
   */
  @Override
  public String get(String key, Filter filter) {
    return get(filterKey(key, filter));
  }

  /**
   * Get a property value formatted as a comma-separated list as an array of 
   * Strings.
   * @param key
   * @return  if key is present, property value split into String elements 
   * by <code>","</code>, otherwise an empty array of Strings
   */
  @Override
  public String[] getArray(String key) {
    String value = get(key);
    if (value == null) {
      return EMPTY_ARRAY;
    } 
    if (value.endsWith(COMMA)) value = value.substring(0, value.length()-1);
    final String[] result = value.split(COMMA);
    for (int i=0; i<result.length; i++) {
      result[i] = result[i].trim();
    }
    return result;
  }

  /**
   * Return the property value formatted as a comma-separated list from an 
   * effective key derived from the key and filter parameters as an array of 
   * Strings.
   * The effective key used to retrieve the property value is build from the 
   * expression <code>key + "[" + filter + "]"</code>
   * @param key
   * @param filter
   * @return  if effective key is present, the property value split into String 
   * elements by <code>","</code>, otherwise an empty array of Strings
   */
  @Override
  public String[] getArray(String key, Filter filter) {
    return getArray(filterKey(key, filter));
  }

  /**
   * Return all properties in this configuration.
   * @return  a Properties object contaiing all properties of this Configuration
   */
  @Override
  public Properties getProperties() {
    Properties result = new Properties();
    for (String key : values_.keySet()) {
      result.setProperty(key, values_.get(key));
    }
    return result;
  }

  /**
   * Not implemented. Will throw an UnsupportedOperationException.
   * @param string
   * @param bln
   * @return 
   */
  @Override
  public Properties getProperties(String string, boolean bln) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * Not implemented. Will throw an UnsupportedOperationException.
   * @param props 
   */
  @Override
  public void removeProperties(Properties props) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * Set a property by the given key and value. If the key already exists
   * the existing value will be overwritten by the new value.
   * @param key
   * @param value 
   */
  @Override
  public void set(String key, String value) {
    values_.put(key, value);
  }
  
  private String filterKey(String key, Filter filter) {
    String[] selectors = filter.getSelectors();
    StringBuilder sb = new StringBuilder();
    sb.append(key).append('[').append(selectors[0]);
    for (int i=1; i<selectors.length; i++) {
      sb.append(',').append(selectors[i]);
    }
    return sb.append(']').toString();
  }
  
  private static final String COMMA = ",";
  private static final String[] EMPTY_ARRAY = new String[0];
}
