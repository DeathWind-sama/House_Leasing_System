import Service.LoginManager;
import org.junit.Test;

public class LoginManagerTest {
    @Test
    public void testLogin(){
        System.out.println(LoginManager.login("114","456",true));
    }
}
