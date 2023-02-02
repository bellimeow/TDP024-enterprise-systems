package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class TransactionEntityFacadeDB implements TransactionEntityFacade {

    @Override //make bool
    public Transaction createTransaction(Account account, long amount, String type, int minvalue, String status, EntityManager em) {

        Transaction transaction = new TransactionDB();

        transaction.setAmount(amount);
        transaction.setType(type);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        transaction.setCreated(now.format(format));

        transaction.setAccount(account);
        transaction.setStatus(status);

        em.persist(transaction);

        return transaction;

    }

    @Override
    public List<Transaction> findAllTransactions(long accountKey) {

        EntityManager em = EMF.getEntityManager();
        Account account = em.find(AccountDB.class, accountKey);

        TypedQuery<Transaction> query = em.createQuery("SELECT t FROM TransactionDB t WHERE t.account = :account", Transaction.class);
        query.setParameter("account", account);
        return query.getResultList();

    }
}
