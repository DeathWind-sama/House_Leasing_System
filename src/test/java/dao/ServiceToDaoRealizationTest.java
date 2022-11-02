package dao;

import dao.ServiceToDaoInterface;
import dao.ServiceToDaoRealization;
import object.Homeowner;
import object.Tenant;
import object.enums.GenderEnum;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ServiceToDaoRealizationTest {
    private final ServiceToDaoInterface serviceToDaoInterface=new ServiceToDaoRealization();;

    @Test
    public void testLogin(){
        boolean isSucceed;
        //register
        Homeowner homeowner=new Homeowner("Lucy","999","Moon","17634341234");
        isSucceed=serviceToDaoInterface.registerPeople(homeowner, "pswLucy");

        Tenant tenant=new Tenant("David","888","NightCity","15325256789","2020-9-9", GenderEnum.MALE);
        isSucceed=serviceToDaoInterface.registerPeople(homeowner, "pswDavid");

        //login
        isSucceed = serviceToDaoInterface.matchPeopleToLogin("udsjkladjkladj", "dsakicdcn",true);
        Assert.assertFalse(isSucceed);

        isSucceed = serviceToDaoInterface.matchPeopleToLogin("999", "pswLucy",true);
        Assert.assertTrue(isSucceed);

        isSucceed = serviceToDaoInterface.matchPeopleToLogin("888", "pswDavid",true);
        Assert.assertTrue(isSucceed);

        isSucceed = serviceToDaoInterface.matchPeopleToLogin("999", "dearAdam",true);
        Assert.assertFalse(isSucceed);
    }
}
