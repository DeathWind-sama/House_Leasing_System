package Dao;

import Object.*;
import Tools.CallbackClass;

public interface ServiceToDaoInterface {
    //login
    boolean matchUserToLogin(String ID, String psw, CallbackClass checkLoginSuccess);//查看是否有该用户已被登记，若已被登记则返回true，否则返回false
    //register
    boolean registerHomeowner(Homeowner homeowner);
    boolean registerTenant(Tenant tenant);
}
