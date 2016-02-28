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

import com.liferay.portal.model.Company;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Christian P. Lerch
 */
public class CompanyServiceHttpTest extends HttpTestUtil {
  
  public CompanyServiceHttpTest() {
  }
  
  @Test
  public void testGetCompany() throws Exception {
    configure();
    Company company1 = CompanyServiceHttp.getCompanyByVirtualHost(getToken().getPrincipal(), getToken().getVirtualHost());
    long companyId1 = company1.getCompanyId();
    Company company2 = CompanyServiceHttp.getCompanyById(getToken().getPrincipal(), companyId1);
    long companyId2 = company2.getCompanyId();
    assertEquals(companyId1, companyId2);
    pln("Company_Name: " + company1.getName());  // requires local stub of AccountLocalServiceUtil
  }

}
