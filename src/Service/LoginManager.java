package Service;

//import java.security.MessageDigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 管理登录、注册的类，登录完成后可销毁
 */
public class LoginManager {
    //使无法new对象，并要求避开意外的实例化
    private LoginManager(){
        throw new AssertionError();
    }

    public boolean login(String ID,String psw){
        //待开发----------
        return true;
    }

    public void register(){
        //待开发----------
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
