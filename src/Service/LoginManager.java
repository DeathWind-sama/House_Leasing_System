package Service;

import Object.*;

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

    public void login(String ID,String psw) {
        System.out.println("Login...");
        //使用异步以把主进程留给其他请求
        new Thread(() -> {
            AtomicBoolean isSuccess = new AtomicBoolean(false);
            isSuccess.set(ServiceMainLogic.serviceToDaoInterface.matchUserToLogin(ID, psw));
            checkLoginSuccess(isSuccess.get());//登录操作完成，根据是否成功进行分支
        }).start();
    }

    public void register(People people){
        if(people instanceof Homeowner){

        }
    }

    private void checkLoginSuccess(boolean isSuccess) {
        if(isSuccess) {
            System.out.println("Login Success");
        }else{
            System.out.println("Login Failed");
        }
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
