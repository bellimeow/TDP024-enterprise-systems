package se.liu.ida.tdp024.account.data.impl.db.entity;

import lombok.ToString;
import se.liu.ida.tdp024.account.data.api.entity.Account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Entity //JPA annotation to make this object ready for storage in a JPA-based data store
public class AccountDB implements Account {

    @Id //specifies primary key
    @GeneratedValue(strategy = GenerationType.AUTO) //Indicates that the ID should be automatically generated
    private long id;
    private String personKey;
    private String accountType;
    private String bankKey;
    private long holdings;

// --- Getters and Setters ---//
    @Override
    public long getId(){
        return id;
    }

    @Override
    public void setId(long id){
        this.id = id;
    }

    @Override
    public String getPersonKey(){
        return personKey;
    }

    @Override
    public void setPersonKey(String personKey){
        this.personKey = personKey;
    }

    @Override
    public String getAccountType(){
        return accountType;
    }

    @Override
    public void setAccountType(String accountType){
        this.accountType = accountType;
    }
    @Override
    public String getBankKey(){
        return bankKey;
    }

    @Override
    public void setBankKey(String bankKey){
        this.bankKey = bankKey;
    }

    @Override
    public long getHoldings(){
        return holdings;
    }

    @Override
    public void setHoldings(long holdings){
        this.holdings = holdings;
    }

}

  

