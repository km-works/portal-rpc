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
package com.liferay.portal.util;

import com.liferay.portal.kernel.util.PropsKeys;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author cpl
 */
public class PropsValuesTest {
  
  public PropsValuesTest() {
  }

  @Test
  public void testConfig1() {
    String mySharedSecret = "A8B7C6D5E4F3G2H1";
    
    // This property is not set in portal.properties
    String sharedSecret1 = PropsValues.TUNNELING_SERVLET_SHARED_SECRET;
    assertNull(sharedSecret1);
    
    // We set this proprty manually
    PropsUtil.set(PropsKeys.TUNNELING_SERVLET_SHARED_SECRET, mySharedSecret);

    // Manually set properties cannot be found in PropsValues
    String sharedSecret3 = PropsValues.TUNNELING_SERVLET_SHARED_SECRET;
    assertNull(sharedSecret3);
    
    // Manually set properties can only be read with PropsUtil.get
    String sharedSecret2 = PropsUtil.get(PropsKeys.TUNNELING_SERVLET_SHARED_SECRET);
    assertEquals(sharedSecret2, mySharedSecret);
  }
  
}
