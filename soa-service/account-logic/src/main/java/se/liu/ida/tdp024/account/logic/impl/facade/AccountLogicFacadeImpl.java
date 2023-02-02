package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.Collections;
import java.util.List;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.dto.BankDTO;
import se.liu.ida.tdp024.account.logic.impl.dto.PersonDTO;
import se.liu.ida.tdp024.account.logic.util.*;


public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private final AccountEntityFacade accountEntityFacade;

    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }

    @Override
    public Account createAccount(String accountType, String personKey, String bankName) {

        /*Validate Parameters*/
        if(accountType == null || personKey ==  null  || bankName == null){
            return null;
        }

        if (!accountType.equals("CHECK") && !accountType.equals("SAVINGS")){
            return null;
        }

        // validate that person and bank exist and create account for said person on said bank
        PersonDTO person = getPerson(personKey);
        BankDTO bank = getBank(bankName);

        if(person == null  || bank == null){
            return null;
        }

        String bankKey = bank.getKey();
        return accountEntityFacade.createAccount(accountType, personKey, bankKey);

    }

    @Override
    public List<Account> findAccounts(String personKey) {

        if(personKey == null){
            System.out.println("please enter a person");
            return null;
        }
        //Validate that person exist
        PersonDTO p = getPerson(personKey);
        if(p == null){
            return Collections.emptyList();
        }

        return accountEntityFacade.findAccounts(personKey);

    }


    private PersonDTO getPerson(String key) {

        if(key == null){
            return null;
        }

        String personServiceURL = "http://localhost:8060/person/find.key";
        HTTPHelper httphelper = new HTTPHelperImpl();
        String personServiceResponse = httphelper.get(personServiceURL, "key", key);

        if (personServiceResponse.equals("null")){
            System.out.println("person with specified key does not exist");
            return null;
        }

        AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
        return jsonSerializer.fromJson(personServiceResponse, PersonDTO.class);
    }

    private BankDTO getBank(String name) {

        if(name == null){
            return null;
        }
        
        String bankServiceURL = "http://localhost:8070/bank/find.name";
        HTTPHelper httphelper = new HTTPHelperImpl();
        String bankServiceResponse = httphelper.get(bankServiceURL, "name", name);

        if (bankServiceResponse.equals("null")||bankServiceResponse.isEmpty()){
            System.out.println("could not find bank " + name);
            return null;
        }

        AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
        return jsonSerializer.fromJson(bankServiceResponse, BankDTO.class);
    }

}
