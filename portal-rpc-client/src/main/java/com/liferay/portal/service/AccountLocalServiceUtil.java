/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.liferay.portal.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Account;
import org.kmworks.liferay.rpc.utils.RPCTunneling;

/**
 *
 * @author cpl
 */
public class AccountLocalServiceUtil {

  public static Account addAccount(Account account)
          throws SystemException {
    return (Account) RPCTunneling.invoke(SELF, "addAccount",
            new Class[]{Account.class},
            account);
  }
  
	public static Account getAccount(long companyId, long accountId)
          throws SystemException {
    return (Account) RPCTunneling.invoke(SELF, "getAccount",
            new Class[]{ long.class, long.class },
            companyId, accountId);
  }
  
  public static Account updateAccount(Account account)
          throws SystemException {
    return (Account) RPCTunneling.invoke(SELF, "updateAccount",
            new Class[]{Account.class},
            account);
  }
  
  private static final Class<?> SELF = AccountLocalServiceUtil.class;
  private static final Log LOG = LogFactoryUtil.getLog(SELF);
}
