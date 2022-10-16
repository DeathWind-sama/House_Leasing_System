package Dao;

import Object.*;
import Tools.CallbackClass;

public interface ServiceToDaoInterface {
    //login
    public boolean matchUserToLogin(String ID, String psw);//查看是否有该用户已被登记，若已被登记则另isSuccess[0]=true
//    {
//        System.out.println("match");
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        isSuccess[0]=true;
//
//    }
    //register
    public boolean registerHomeowner(Homeowner homeowner);
    public boolean registerTenant(Tenant tenant);
}
