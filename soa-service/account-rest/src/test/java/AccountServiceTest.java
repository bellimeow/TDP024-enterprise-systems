import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.util.AccountJsonSerializer;
import se.liu.ida.tdp024.account.logic.util.AccountJsonSerializerImpl;
import service.AccountService;

import java.util.List;

public class AccountServiceTest {

    AccountService accountService = new AccountService();
    AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    @Rule
    public TestName name = new TestName();

    @After
    public void tearDown() {
        StorageFacade storageFacade = new StorageFacadeDB();
        storageFacade.emptyStorage();
    }

    @Test
    public void testCreateAccount(){
        System.out.println(name.getMethodName());

        String response1 = accountService.createAccount("CHECK", "1", "SBAB");
        String response2 = accountService.createAccount(null, "1", "HANDELSBANKEN");
        String response3 = accountService.createAccount("SAVINGS", null, "HANDELSBANKEN");
        String response4 = accountService.createAccount("SAVINGS", "1", null);
        String response5 = accountService.createAccount("SAVINGS","mushi", "CITIBANK");

        Assert.assertEquals("OK", response1);
        Assert.assertEquals("FAILED", response2);
        Assert.assertEquals("FAILED", response3);
        Assert.assertEquals("FAILED", response4);
        Assert.assertEquals("FAILED", response5);

    }

    @Test
    public void testFind(){
        System.out.println(name.getMethodName());

        accountService.createAccount("CHECK", "1", "SBAB");
        String response1 = accountService.find("1");
        String responseNull = accountService.find(null);

        Assert.assertNotEquals("[]", response1);
        Assert.assertEquals("[]", responseNull);
    }



    @Test
    public void testDebit() {
        System.out.println(name.getMethodName());

        Account account = accountEntityFacade.createAccount("CHECK", "4", "CITIBANK");
        long id = account.getId();

        String response = accountService.debit(id, 100);
        Assert.assertEquals("FAILED", response);

    }

    @Test
    public void testCredit() {
        System.out.println(name.getMethodName());

        Account account = accountEntityFacade.createAccount("SAVINGS", "5", "SBAB");
        long id = account.getId();

        String response = accountService.credit(id, 100);
        Assert.assertEquals("OK", response);
    }

    @Test
    public void testTransactions() {
        Account account = accountEntityFacade.createAccount("CHECK", "3", "IKANOBANKEN");
        long id = account.getId();

        String creditResponse = accountService.credit(id, 100);
        String debitResponse = accountService.debit(id, 50);
        List<Transaction> transactions = accountService.getTransactions(id);

        Assert.assertEquals("OK",creditResponse);
        Assert.assertEquals("OK", debitResponse);
        Assert.assertEquals(2, transactions.size());
    }
}
