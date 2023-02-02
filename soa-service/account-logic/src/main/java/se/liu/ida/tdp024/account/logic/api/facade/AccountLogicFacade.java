package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;


import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountLogicFacade {
    public Account createAccount(String accountType, String personKey, String bankname);

    public List<Account> findAccounts(String personKey);
}
