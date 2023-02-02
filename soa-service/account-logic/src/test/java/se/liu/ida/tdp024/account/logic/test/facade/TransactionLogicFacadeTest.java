package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.*;
import org.junit.rules.TestName;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;


import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class TransactionLogicFacadeTest {

    private final TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());
    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());

    @Rule
    public TestName name = new TestName();

    @After
    public void tearDown() {
        StorageFacade storageFacade = new StorageFacadeDB();
        storageFacade.emptyStorage();
    }

    //@Ignore
    @Test
    public void testCredit(){
        EntityManager manager = EMF.getEntityManager();
        System.out.println(name.getMethodName());
        Account accountKey = accountLogicFacade.createAccount("SAVINGS", "1", "NORDEA");

        String response1 = transactionLogicFacade.credit(accountKey.getId(), 1000);
        Assert.assertEquals(response1, "OK");

        Account account = manager.find(AccountDB.class, accountKey.getId());
        Assert.assertEquals(1000, account.getHoldings());
    }


    //@Ignore
    @Test
    public void testDebit(){
        System.out.println(name.getMethodName());
        Account aid = accountLogicFacade.createAccount("CHECK", "3", "SWEDBANK");
        transactionLogicFacade.credit(aid.getId(), 100);

        String responseOK = transactionLogicFacade.debit(aid.getId(), 50);
        String responseFAILED = transactionLogicFacade.debit(aid.getId(), 100);

        Assert.assertEquals("OK", responseOK);
        Assert.assertEquals("FAILED", responseFAILED);
    }

    //@Ignore
    @Test
    public void testFindTransactionsSuccess(){
        System.out.println(name.getMethodName());
        Account aid = accountLogicFacade.createAccount("CHECK", "3", "HANDELSBANKEN");

        transactionLogicFacade.credit(aid.getId(), 500);
        transactionLogicFacade.debit(aid.getId(), 100);
        transactionLogicFacade.debit(aid.getId(), 50);

        List<Transaction> transactions = transactionLogicFacade.findAllTransactions(aid.getId());
        Assert.assertEquals(3, transactions.size());

    }



}

