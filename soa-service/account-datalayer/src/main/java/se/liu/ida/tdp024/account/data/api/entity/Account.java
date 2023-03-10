package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    
    public long getId();
    public void setId(long id);

    public String getPersonKey();
    public void setPersonKey(String personKey);

    public String getBankKey();
    public void setBankKey(String bankKey);
    
    public String getAccountType();
    public void setAccountType(String accountType);

    public long getHoldings();
    public void setHoldings(long holdings);
}

