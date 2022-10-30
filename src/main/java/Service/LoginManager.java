package Service;

import Dao.ServiceToDaoInterface;
import Dao.ServiceToDaoRealization;
import Object.*;
import Object.Enums.GenderEnum;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 管理登录、注册的类，登录完成后可销毁
 */
public class LoginManager {
    //使无法new对象，并要求避开意外的实例化
//    private LoginManager(){
//        throw new AssertionError();
//    }

    public static void main(String[] args) {
//        People people=new Tenant("John","114","NewYork city","1919-810","1998.06.08", GenderEnum.MALE);
//        System.out.println(register(people,"456"));

        System.out.println(login("114","456",true));
        System.out.println(login("114","466",true));
        System.out.println(login("114","456",true));
        System.out.println(login("314","456",true));
    }

    static ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
    public static boolean login(String ID,String psw,boolean isHomeowner) {
        System.out.println("Login...");
        //使用异步以把主进程留给其他请求
//        boolean isSuccess=ServiceMainLogic.serviceToDaoInterface.matchPeopleToLogin(ID, psw);
        boolean isSuccess=serviceToDaoInterface.matchPeopleToLogin(ID, psw,isHomeowner);
        //登录操作完成，根据是否成功进行分支
        if(isSuccess) {
            System.out.println("Login Success");
        }else{
            System.out.println("Login Failed");
        }
        return isSuccess;
    }

    public static boolean register(People people,String psw){
        boolean isSuccess=serviceToDaoInterface.registerPeople(people, psw);
        return isSuccess;
    }

    private String encrypt(String originalCode){
        try{
            MessageDigest md=MessageDigest.getInstance("sha-1");
            md.update(originalCode.getBytes());
            byte[] shaAns=md.digest();
            return Base64.getEncoder().encodeToString(shaAns);
        }catch (NoSuchAlgorithmException ex){
            System.err.println("ERROR: Encryption NoSuchAlgorithmException.");
            return "ERROR";
        }
    }
}
