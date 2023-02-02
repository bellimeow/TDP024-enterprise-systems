package se.liu.ida.tdp024.account.data.test.facade;

import org.junit.*;
import org.junit.rules.TestName;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.facade.TransactionEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


public class TransactionEntityFacadeTest {

    private final AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private final TransactionEntityFacade transactionEntityFacade = new TransactionEntityFacadeDB();

    @Rule
    public TestName name = new TestName();

    @After
    public void tearDown() {
        StorageFacade storageFacade = new StorageFacadeDB();
        storageFacade.emptyStorage();
    }

    //@Ignore
    @Test
    public void testCreateTransaction() {
        System.out.println(name.getMethodName());
        Account account = accountEntityFacade.createAccount("SAVINGS", "1", "SWEDBANK");

        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();

        Assert.assertNotNull(transactionEntityFacade.createTransaction(account, 100, "CREDIT", 0, "OK", em));
        em.getTransaction().commit();

    }


    //@Ignore
    @Test
    public void testFindTransactions() throws Exception {
        System.out.println(name.getMethodName());
        Account account = accountEntityFacade.createAccount("CHECK", "3", "NORDEA");
        Account noTransactionsAccount = accountEntityFacade.createAccount("SAVINGS", "3", "HANDELSBANKEN");

        EntityManager em = EMF.getEntityManager();
        em.getTransaction().begin();

        transactionEntityFacade.createTransaction(account, 100, "CREDIT", 0, "OK", em);
        transactionEntityFacade.createTransaction(account, 100, "CREDIT", 0, "OK", em);
        transactionEntityFacade.createTransaction(account, 100, "DEBIT", 0, "OK", em);

        em.getTransaction().commit();

        List<Transaction> transactionsFound = transactionEntityFacade.findAllTransactions(account.getId());
        Assert.assertEquals(3, transactionsFound.size());

        List<Transaction> noTransactions = transactionEntityFacade.findAllTransactions(noTransactionsAccount.getId());
        Assert.assertEquals(0, noTransactions.size());
    }


}