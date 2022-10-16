package Service;

import Object.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
        LoginManager l=new LoginManager();
        l.login("abc","123");
    }

    public void login(String ID,String psw) {
        System.out.println("Login...");
        //使用异步以把主进程留给其他请求
        new Thread(() -> {
            try {
                AtomicBoolean isSuccess = new AtomicBoolean(false);
                //创建新进程以插队运行等待请求回复
                Thread thread = new Thread(() -> {
                    //请求数据库进行匹配
                    isSuccess.set(ServiceMainLogic.serviceToDaoInterface.matchUserToLogin(ID, psw));
                });
                thread.start();
                thread.join();//匹配过程中阻塞当前进程
                checkLoginSuccess(isSuccess.get());//登录操作完成，根据是否成功进行分支
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
