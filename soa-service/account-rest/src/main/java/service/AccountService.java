package service;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.facade.TransactionEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.api.facade.TransactionLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.impl.facade.TransactionLogicFacadeImpl;
import se.liu.ida.tdp024.account.logic.util.*;

/* 
 * topics: rest-requests, transactions
 */

@RestController
@RequestMapping("/account-rest/account")
public class AccountService {

    private final AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    private final TransactionLogicFacade transactionLogicFacade = new TransactionLogicFacadeImpl(new TransactionEntityFacadeDB());

    @GetMapping("/create/")
    public String createAccount(@RequestParam(value="accounttype", defaultValue = "no_type") String accounttype,
    @RequestParam(value="person", defaultValue = "no_person") String personKey,
    @RequestParam(value="bank", defaultValue = "no_bank") String bankname){

        KafkaLogger.sendToKafka("rest-requests",
                "GET account-rest/account/create/?accounttype=" + accounttype +
                "&person=" + personKey + "&bank=" + bankname);

        if(accounttype==null||personKey==null||bankname==null||accounttype.equals("no_type")
                ||personKey.equals("no_person")||bankname.equals("no_bank")){
            return "FAILED";
        }

        Account response = accountLogicFacade.createAccount(accounttype, personKey, bankname);
        if (response == null){
            return "FAILED";
        }
        return "OK";

    }

    @GetMapping("/find/person")
    public String find(@RequestParam(value="person") String personKey){

        KafkaLogger.sendToKafka("rest-requests",
                "GET account-rest/account/find/person?person=" + personKey);

        AccountJsonSerializer accountJsonSerializer = new AccountJsonSerializerImpl();

        if(personKey==null){
            return "[]";
        }

        List<Account> accounts = accountLogicFacade.findAccounts(personKey);
        return accountJsonSerializer.toJson(accounts);

    }

    @GetMapping("/debit")
    public String debit(@RequestParam(value="id") long id,
    @RequestParam(value="amount") long amount){
        KafkaLogger.sendToKafka("rest-requests", "GET account-rest/account/debit?id=" + id + "&amount=" + amount);
        return transactionLogicFacade.debit(id, amount);
    }

    @GetMapping("/credit")
    public String credit(@RequestParam(value="id") long id,
    @RequestParam(value="amount") long amount){
        KafkaLogger.sendToKafka("rest-requests", "GET account-rest/account/credit?id=" + id + "&amount=" + amount);
        return transactionLogicFacade.credit(id, amount);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam(value="id") long id){
        KafkaLogger.sendToKafka("rest-requests", "GET account-rest/account/transactions?id=" + id);
        return transactionLogicFacade.findAllTransactions(id);
    } 
}
