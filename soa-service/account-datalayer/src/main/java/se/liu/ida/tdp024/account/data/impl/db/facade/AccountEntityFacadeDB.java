package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import javax.persistence.EntityManager;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.impl.db.util.KafkaLogger;


public class AccountEntityFacadeDB implements AccountEntityFacade {

    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();

    @Override
    public Account getAccount(long aid) {
        EntityManager em = EMF.getEntityManager();
        Account account = em.find(AccountDB.class,aid);
        em.close();

        return account;
    }

    @Override
    public Account createAccount(String accountType, String personKey, String bankKey) {

        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();

        Account account = new AccountDB();

        account.setAccountType(accountType);
        account.setPersonKey(personKey);
        account.setBankKey(bankKey);
        account.setHoldings(0);

        em.persist(account);
        em.getTransaction().commit();
        em.close();

        return account;
    }

    @Override
    public List<Account> findAccounts(String personKey){

        if(personKey == null){
            System.out.println("please enter a person");
            return Collections.emptyList();
        }

        EntityManager em = EMF.getEntityManager();
        TypedQuery<Account> query = em.createQuery("SELECT a FROM AccountDB a WHERE a.personKey = :personKey", Account.class);
        query.setParameter("personKey" , personKey);
        return query.getResultList();

    }

    @Override
    public boolean updateHoldings(long accountId, long amount,String type, int minval) {

        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();
        Account account = em.find(AccountDB.class, accountId, LockModeType.PESSIMISTIC_WRITE);
        if(account == null){
            System.out.println("account " + accountId + " does not exist");
            return false;
        }
        long preliminaryBalance = 0;
        if (type.equals("CREDIT")){
            preliminaryBalance = account.getHoldings() + amount;
        } else if(type.equals("DEBIT")){
            preliminaryBalance = account.getHoldings() - amount;
        }



        if(preliminaryBalance < minval){
            Transaction transaction = transactionEntityFacade.createTransaction(account, amount, type, minval, "FAILED", em);
            if (transaction==null){
                em.getTransaction().rollback();
                em.getTransaction().commit();
                return false;
            }
            KafkaLogger.sendToKafka("transactions", "Transaction created: " + transaction);
            em.getTransaction().commit();
            return false;
        } else {
            Transaction transaction = transactionEntityFacade.createTransaction(account, amount, type, minval, "OK", em);
            if(transaction == null){
                em.getTransaction().rollback();
                em.getTransaction().commit();
                return false;
            }
            account.setHoldings(preliminaryBalance);
            KafkaLogger.sendToKafka("transactions", "Transaction created: " + transaction);
            em.merge(account);
            em.getTransaction().commit();
            return true;
        }

    }

}
