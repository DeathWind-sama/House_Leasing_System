import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import org.junit.Assert;
import org.junit.Test;
import service.LoginServlet;

public class LoginManagerTest {
    @Test
    public void testLogin(){
        ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();
        boolean isSuccess = serviceToDaoInterface.matchPeopleToLogin("114", "456",true);
        Assert.assertEquals(true,isSuccess);
    }
}
