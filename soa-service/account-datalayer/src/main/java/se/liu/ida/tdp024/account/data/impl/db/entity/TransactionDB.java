package se.liu.ida.tdp024.account.data.impl.db.entity;

import javax.persistence.*;

import lombok.ToString;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

@ToString
@Entity
public class TransactionDB implements Transaction{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private Long amount;
    private String created;
    private String status;
    @ManyToOne(targetEntity = AccountDB.class)
    private Account account;

    @Override
    public long getId(){
        return id;
    }

    @Override
    public void setId(long id){
        this.id = id;
    }

    @Override
    public String getType(){
        return type;
    }

    @Override
    public void setType(String type){
        this.type = type;
    }

    @Override
    public long getAmount(){
        return amount;
    }

    @Override
    public void setAmount(long amount){
        this.amount = amount;
    }

    @Override
    public String getCreated(){
        return created;
    }

    @Override
    public void setCreated(String created){
        this.created = created;
    }

    @Override
    public String getStatus(){
        return status;
    }

    @Override
    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public Account getAccount(){
        return account;
    }

    @Override
    public void setAccount(Account account){
        this.account = account;
    }

}



// {
//     "id": 144,
//     "type": "DEBIT",
//     "amount": 8,
//     "created": "2013-06-30 14:49:32",
//     "status": "OK",
//     "account" : {
//       "id": 5,
//       "personKey": "ahRzfmVudGVycHJpc2Utc3lzdGVtc3INCxIGUGVyc29uGLNtDA",
//       "accountType": "CHECK",
//       "bankKey": "ahRzfmVudGVycHJpc2Utc3lzdGVtc3ILCxIEQmFuaxiJJww",
//       "holdings": 42
//     }
// }