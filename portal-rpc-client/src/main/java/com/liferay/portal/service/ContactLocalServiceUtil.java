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
package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Contact;
import org.kmworks.portal.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public class ContactLocalServiceUtil {

  public static Contact addContact(Contact user)
          throws SystemException {
    return (Contact) RPCTunneling.invoke(SELF, "addContact",
            new Class[]{Contact.class},
            user);
  }

  public static Contact getContact(long contactId) 
          throws SystemException {
    return (Contact) RPCTunneling.invoke(SELF, "getContact",
            new Class[]{long.class},
            contactId);
  }

  public static Contact updateContact(Contact user)
          throws SystemException {
    return (Contact) RPCTunneling.invoke(SELF, "updateContact",
            new Class[]{Contact.class},
            user);
  }

  private static final Class<?> SELF = ContactLocalServiceUtil.class;
}
