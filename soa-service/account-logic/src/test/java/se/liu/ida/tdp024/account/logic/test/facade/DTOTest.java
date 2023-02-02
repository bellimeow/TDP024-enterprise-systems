package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.ida.tdp024.account.logic.impl.dto.PersonDTO;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

import java.util.List;

public class DTOTest {

    private Account account = new AccountDB();

    @After
    public void tearDown() {
        StorageFacade storageFacade = new StorageFacadeDB();
        storageFacade.emptyStorage();
    }

    @Test
    public void testPerson() {
        String key = "2";
        PersonDTO person = new PersonDTO();
        person.setKey(key);
        String personKey = person.getKey();
        Assert.assertEquals(key, personKey);

        String name = "Person";
        person.setName(name);
        String personName = person.getName();
        Assert.assertEquals(name, personName);
    }

    @Test
    public void testBank(){
        String key = "4";
        BankDTO bank = new BankDTO();
        bank.setKey(key);
        String bankKey = bank.getKey();
        Assert.assertEquals(key, bankKey);

        String name = "Bank";
        bank.setName(name);
        String bankName = bank.getName();
        Assert.assertEquals(name, bankName);
    }

}

