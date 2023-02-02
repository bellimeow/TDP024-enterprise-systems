package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.*;
import org.junit.rules.TestName;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

import java.util.List;

public class AccountLogicFacadeTest {

    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());

    @Rule
    public TestName name = new TestName();

    @After
    public void tearDown() {
        StorageFacade storageFacade = new StorageFacadeDB();
        storageFacade.emptyStorage();
    }

    //@Ignore
    @Test
    public void testCreateSuccess(){
        System.out.println(name.getMethodName());

        Account response1 = accountLogicFacade.createAccount("CHECK", "1", "SWEDBANK");
        Assert.assertNotNull(response1);

        Account response2 = accountLogicFacade.createAccount("SAVINGS", "1", "HANDELSBANKEN");
        Assert.assertNotNull(response2);
    }


    //@Ignore
    @Test
    public void testCreateFail(){
        System.out.println(name.getMethodName());

        // Testing unvalid parameters because they do not exist
        Account wrongAccountTypeResponse = accountLogicFacade.createAccount("NOT A VALID ACCOUNT TYPE", "3", "SWEDBANK");
        Assert.assertNull("Account name does not exist for account type", wrongAccountTypeResponse);

        Account notValidBankResponse = accountLogicFacade.createAccount("CHECK", "2", "GRINGOTTS");
        Assert.assertNull("Bank name does not exist", notValidBankResponse);

        Account notValidPersonResponse = accountLogicFacade.createAccount("SAVINGS", "Harry", "HANDELSBANKEN");
        Assert.assertNull("Person key does not exist", notValidPersonResponse);
    }

    //@Ignore
    @Test
    public void testNullInput(){
        System.out.println(name.getMethodName());

        Account nullValueResponse1 = accountLogicFacade.createAccount(null, "4", "NORDEA");
        Assert.assertNull("Invalid type: can't use null parameter for account type", nullValueResponse1);

        Account nullValueResponse2 = accountLogicFacade.createAccount("CHECK", null , "NORDEA");
        Assert.assertNull("Invalid type: can't use null parameter for person key", nullValueResponse2);

        Account nullValueResponse3 = accountLogicFacade.createAccount("CHECK", "4", null);
        Assert.assertNull("Invalid type: can't use null parameter for bank name", nullValueResponse3);
    }


    //@Ignore
    @Test   //This works
    public void testFindAccountsSuccess(){
        System.out.println(name.getMethodName());

        accountLogicFacade.createAccount("CHECK", "1", "SWEDBANK");
        accountLogicFacade.createAccount("SAVINGS", "1", "NORDEA");

        List<Account> accounts = accountLogicFacade.findAccounts("1");
        Assert.assertEquals(2, accounts.size());

    }
    //@Ignore
    @Test   //This works
    public void testFindNoAccounts(){
        System.out.println(name.getMethodName());
        List<Account> accounts = accountLogicFacade.findAccounts("5");
        Assert.assertEquals("No accounts found", 0, accounts.size());
    }

    //@Ignore
    @Test   //This works
    public void testFindAccountNullParam(){
        System.out.println(name.getMethodName());
        Assert.assertNull(accountLogicFacade.findAccounts(null));
    }

}

