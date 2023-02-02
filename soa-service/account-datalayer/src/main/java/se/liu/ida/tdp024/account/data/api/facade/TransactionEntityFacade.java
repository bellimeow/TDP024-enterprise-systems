package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import javax.persistence.EntityManager;

public interface TransactionEntityFacade{

    public Transaction createTransaction(Account account, long amount, String type, int minvalue, String status, EntityManager em);

    public List<Transaction> findAllTransactions(long account);

}