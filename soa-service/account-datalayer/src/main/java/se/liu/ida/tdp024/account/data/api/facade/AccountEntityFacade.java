package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;

public interface AccountEntityFacade {

    Account getAccount(long aid);
    Account createAccount(String accountType, String personKey, String bankKey);
    List<Account> findAccounts(String personKey);
    boolean updateHoldings(long accountId, long amount,String type, int minvalue);
    //long readHoldings(long aid);
}
