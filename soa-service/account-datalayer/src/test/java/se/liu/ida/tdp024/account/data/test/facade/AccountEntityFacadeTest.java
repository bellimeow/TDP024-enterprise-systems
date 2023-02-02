package se.liu.ida.tdp024.account.data.test.facade;

import java.util.Collections;
import java.util.List;

import org.junit.*;

import org.junit.rules.TestName;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.data.api.entity.Account;


public class AccountEntityFacadeTest {

    private final AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();

    @Rule
    public TestName name = new TestName();

    @After
    public void tearDown() {
        StorageFacade storageFacade = new StorageFacadeDB();
        storageFacade.emptyStorage();
    }

    //@Ignore
    @Test
    public void testCreateAccount(){
        System.out.println(name.getMethodName());
        Assert.assertNotNull(accountEntityFacade.createAccount("CHECK", "1", "SWEDBANK"));
        Assert.assertNotNull(accountEntityFacade.createAccount("SAVINGS", "1", "HANDELSBANKEN"));
    }

    //@Ignore
    @Test
    public void testFindAccounts(){
        System.out.println(name.getMethodName());

        accountEntityFacade.createAccount("CHECK", "2", "SWEDBANK");
        accountEntityFacade.createAccount("SAVINGS", "2", "NORDEA");

        List<Account> accounts = accountEntityFacade.findAccounts("2");
        Assert.assertEquals(2, accounts.size());

        List<Account> accountsNullPerson = accountEntityFacade.findAccounts(null);
        Assert.assertEquals(0, accountsNullPerson.size());

        List<Account> noAccounts = accountEntityFacade.findAccounts("3");
        Assert.assertEquals(0, noAccounts.size());
    }

    @Ignore
    @Test
    public void testUpdateHoldings() {
        System.out.println(name.getMethodName());

        Account account = accountEntityFacade.createAccount("SAVINGS", "4", "DANSKEBANK");
        Assert.assertTrue(accountEntityFacade.updateHoldings(account.getId(), 100,"CREDIT", 0));
        Assert.assertTrue(accountEntityFacade.updateHoldings(account.getId(), 50,"DEBIT", 0));
        Assert.assertTrue(accountEntityFacade.updateHoldings(account.getId(), 75, "CREDIT",0));
        Assert.assertTrue(accountEntityFacade.updateHoldings(account.getId(), 100, "DEBIT" ,0));

        Assert.assertFalse(accountEntityFacade.updateHoldings(88, 100,"CREDIT", 0));
    }

}



