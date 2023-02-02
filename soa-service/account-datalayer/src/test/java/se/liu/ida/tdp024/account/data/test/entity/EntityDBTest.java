package se.liu.ida.tdp024.account.data.test.entity;


import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class EntityDBTest {

    private Account account = new AccountDB();
    private Transaction transaction = new TransactionDB();

    @After
    public void tearDown() {
        StorageFacade storageFacade = new StorageFacadeDB();
        storageFacade.emptyStorage();
    }
    //@Ignore
    @Test
    public void testAccountId(){
        long id = 1;
        account.setId(id);
        
        long getId = account.getId();
        Assert.assertEquals(id, getId);
    }

    //@Ignore
    @Test
    public void testPersonKey() {
        String key = "2";
        account.setPersonKey(key);
        
        String getKey = account.getPersonKey();
        Assert.assertEquals(key, getKey);
    }

    //@Ignore
    @Test
    public void testBankKey(){
        String key = "4";
        account.setBankKey(key);

        String getKey = account.getBankKey();
        Assert.assertEquals(key, getKey);
    }

    //@Ignore
    @Test
    public void testHoldings(){
        long holding = 100;
        account.setHoldings(holding);

        long getHolding = account.getHoldings();
        Assert.assertEquals(holding, getHolding);
    }

    //@Ignore
    @Test
    public void testTransactionId(){
        long id = 20;
        transaction.setId(id);

        long getID = transaction.getId();
        Assert.assertEquals(id, getID);
    }

    //@Ignore
    @Test
    public void testTransactionType(){
        String type = "SAVINGS";
        transaction.setType(type);

        String getType = transaction.getType();
        Assert.assertEquals(type, getType);
    }

    //@Ignore
    @Test
    public void testTransactionAmount(){
        long amount = 100;
        transaction.setAmount(amount);

        long getAmount = transaction.getAmount();
        Assert.assertEquals(amount, getAmount);
    }

    //@Ignore
    @Test
    public void testTransactionCreated(){
        String created = "2022-10-14";
        transaction.setCreated(created);

        String getCreated = transaction.getCreated();
        Assert.assertEquals(created, getCreated);
    }

    //@Ignore
    @Test
    public void testTransactionStatus(){
        String status = "OK";
        transaction.setStatus(status);

        String getStatus = transaction.getStatus();
        Assert.assertEquals(status, getStatus);

    }

    //@Ignore
    @Test
    public void testTransactionAccount(){
        transaction.setAccount(account);

        Account a = transaction.getAccount();
        Assert.assertEquals(account, a);

    }
}

