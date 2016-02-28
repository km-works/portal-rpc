/*
 * Copyright (C) 2005-2014 Christian P. Lerch <christian.p.lerch [at] gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.liferay.portal.service.http;

import com.liferay.portal.model.Country;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Christian P. Lerch
 */
public class CountryServiceHttpTest extends HttpTestUtil {
  
  public CountryServiceHttpTest() {
  }

  @Test
  public void testGetCountries_HttpPrincipal() throws Exception {
    configure();
    List<Country> countries = CountryServiceHttp.getCountries(getToken().getPrincipal());
    assertTrue(!countries.isEmpty());
    System.out.println("*** " + countries.size() + " countries retrieved.");
  }

  
}
