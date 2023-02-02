package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.Collections;
import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;

public class TransactionLogicFacadeImpl implements TransactionLogicFacade{

    private final TransactionEntityFacade transactionEntityFacade;
    private final AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private final int minvalue = 0;

    public TransactionLogicFacadeImpl(TransactionEntityFacadeDB transactionEntityFacadeDB) {
        this.transactionEntityFacade = transactionEntityFacadeDB;
    }

    @Override
    public String credit(long accountKey, long amount) {
        if(amount < 0){
         return "FAILED";
        }
        if (accountEntityFacade.updateHoldings(accountKey, amount,"CREDIT", minvalue)){
            return "OK";
        }
        return "FAILED";
    }


    @Override
    public String debit(long accountKey, long amount) {
        if (amount < 0) {
           return "FAILED";
        }
        if(accountEntityFacade.updateHoldings(accountKey, amount,"DEBIT", minvalue)){
            return "OK";
        }
        return "FAILED";
    }
    
    @Override
    public List<Transaction> findAllTransactions(long accountKey) {
        return transactionEntityFacade.findAllTransactions(accountKey);
    }



}
