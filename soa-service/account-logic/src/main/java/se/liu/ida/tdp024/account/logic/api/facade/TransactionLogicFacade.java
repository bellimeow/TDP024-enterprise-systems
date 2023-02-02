package se.liu.ida.tdp024.account.logic.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface TransactionLogicFacade {

    public String credit(long accountKey, long amount);

    public String debit(long accountKey, long amount);

    public List<Transaction> findAllTransactions(long account);
}
