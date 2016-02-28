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

import org.kmworks.portal.rpc.Configuration;
import com.liferay.portal.service.http.HttpTestUtil;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cpl
 */
public class ConfigurationTest {
  
  public ConfigurationTest() {
  }

  @Test
  public void testInitialize1() {
    Configuration.initialize(
            HttpTestUtil.PORTAL_URI, 
            HttpTestUtil.USER_NAME, 
            HttpTestUtil.USER_PWD, 
            HttpTestUtil.SHARED_SECRET);
    assertTrue(Configuration.isValid());
  }

}
