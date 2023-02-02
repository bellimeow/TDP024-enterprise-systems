package se.liu.ida.tdp024.account.logic.util;

public interface AccountJsonSerializer {

    public <T> T fromJson(String json, Class<T> clazz);
    
    public String toJson(Object object);
}
