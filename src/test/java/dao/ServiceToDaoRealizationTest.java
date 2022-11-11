package dao;

import object.*;
import object.enums.GenderEnum;
import object.enums.HouseTypeEnum;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ServiceToDaoRealizationTest {
    private final ServiceToDaoInterface serviceToDaoRealization = new ServiceToDaoRealization();

    @Test
    public void testLogin() {
        boolean isSucceed;
        //register
        Homeowner homeowner = new Homeowner("Lucy", "999", "Moon", "17634341234");
        isSucceed = serviceToDaoRealization.registerPeople(homeowner, "pswLucy");

        Tenant tenant = new Tenant("David", "888", "NightCity", "15325256789", "2020-9-9", GenderEnum.MALE);
        isSucceed = serviceToDaoRealization.registerPeople(homeowner, "pswDavid");

        //login
        isSucceed = serviceToDaoRealization.matchPeopleToLogin("udsjkladjkladj", "dsakicdcn", true);
        Assert.assertFalse(isSucceed);

        isSucceed = serviceToDaoRealization.matchPeopleToLogin("999", "pswLucy", true);
        Assert.assertTrue(isSucceed);

        isSucceed = serviceToDaoRealization.matchPeopleToLogin("888", "pswDavid", true);
        Assert.assertTrue(isSucceed);

        isSucceed = serviceToDaoRealization.matchPeopleToLogin("999", "dearAdam", true);
        Assert.assertFalse(isSucceed);
    }

    @Test
    public void matchPeopleToLogin() {
        boolean ok = serviceToDaoRealization.matchPeopleToLogin("1", "2372932", false);
    }

    @Test
    public void delHouse() {
    }

    @Test
    public void delExpenseSheet() {
        ExpenseSheet expenseSheet = new ExpenseSheet("82932", "2378344", "Wang", false, 238237, false, "1212736");
        boolean ok = serviceToDaoRealization.delExpenseSheet("82932");
    }

    @Test
    public void payExpenseSheet() {
        boolean ok = serviceToDaoRealization.payExpenseSheet("24354");
    }

    @Test
    public void delCommunicationAuthority() {
        /*测试delCommunicationAuthority*/
        boolean ok = serviceToDaoRealization.delCommunicationAuthority("298497");
    }

    @Test
    public void delContract() {
        /*测试delContract*/
        //boolean ok=s.delContract("173282"); //没有这个ID false
        boolean ok = serviceToDaoRealization.delContract("17382"); //删除刚刚创建的Contract true
    }

    @Test
    public void getOwnContract() {
        /*测试getOwnContract*/
        ArrayList<Contract> contracts = new ArrayList<>();
        Homeowner homeowner = new Homeowner("Jerry", "122", "华盛顿", "12832083");
        boolean ok = serviceToDaoRealization.getOwnContract(homeowner, contracts);
        System.out.println(contracts.get(0).getContractID()); //输出查询到的ID 17382*/

    }

    @Test
    public void getContract() {
        /*测试getContract*/
        Contract contract = null;
        boolean ok = serviceToDaoRealization.getContract("17382", contract);
    }

    @Test
    public void addContract() {
        /*测试addContract*/
        Contract contract = new Contract("17382", "122", "1", 1831, 821, "12821313");
        boolean ok = serviceToDaoRealization.addContract(contract);
    }

    @Test
    public void getOwnVisitRecords() {
        /*测试getOwnVisitRecords*/
        ArrayList<VisitRecord> visitRecords = new ArrayList<>();
        boolean ok = serviceToDaoRealization.getOwnVisitRecords("222", visitRecords);
        System.out.println(visitRecords.get(0).getTenantID());  //输出TenantID 25346*/
    }

    @Test
    public void VisitRecord() {
        /*测试addVisitRecord*/
        VisitRecord visitRecord = new VisitRecord("1212736", "222", "25346", "2022-3-12", "2022-9-12");
        boolean ok = serviceToDaoRealization.addVisitRecord(visitRecord);
    }

    @Test
    public void CommunicationAuthorities() {
        /* 测试getOwnCommunicationAuthorities*/
        Tenant tenant = new Tenant("Jack", "13124", "陕西", "13812742974", "2002-01-03", GenderEnum.FEMALE);
        Homeowner homeowner = new Homeowner("Rose", "2839249", "陕西", "218392743");
        ArrayList<CommunicationAuthority> communicationAuthorities = new ArrayList<>();
        boolean ok = serviceToDaoRealization.getOwnCommunicationAuthorities("13124", communicationAuthorities);
        System.out.println(communicationAuthorities.get(0).getAuthorityID());
    }

    @Test
    public void addCommunicationAuthority() {
        CommunicationAuthority communicationAuthority = new CommunicationAuthority("122", "234", "298497", "645467");
        boolean ok = serviceToDaoRealization.addCommunicationAuthority(communicationAuthority);
    }

    @Test
    public void getOwnExpenseSheets() {
        ArrayList<ExpenseSheet> expenseSheets = new ArrayList<>();
        Tenant tenant = new Tenant("Jack", "13124", "陕西", "13812742974", "2002-01-03", GenderEnum.FEMALE);
        boolean ok = serviceToDaoRealization.getOwnExpenseSheets(tenant, expenseSheets);
    }

    @Test
    public void addExpenseSheet() {
        ExpenseSheet expenseSheet = new ExpenseSheet("82932", "2378344", "Wang", false, 238237, false, "1212736");
        boolean ok = serviceToDaoRealization.addExpenseSheet(expenseSheet);
    }

    @Test
    public void unlockHouse() {
        ExpenseSheet expenseSheet = new ExpenseSheet("82932", "2378344", "Jack", false, 238237, false, "1212736");
        boolean ok = serviceToDaoRealization.unlockHouse(expenseSheet);
    }

    @Test
    public void getSomePayedHouses() {
    }

    @Test
    public void getAllPayedHouses() {
        ArrayList<House> houses = new ArrayList<>();
        boolean ok = serviceToDaoRealization.getAllPayedHouses(houses);
//        boolean ok=s.getSomePayedHouses(2,houses);
    }

    @Test
    public void getOwnHouses() {
        ArrayList<House> houses = new ArrayList<>();
        boolean ok = serviceToDaoRealization.getOwnHouses("122", houses); //true
//        boolean ok=s.getOwnHouses("21898",houses); //false*/
    }

    @Test
    public void getHouse() {
        House house = serviceToDaoRealization.getHouse("128297");
    }

    @Test
    public void addHouse() {
        House house = new House("128297", "2839249", false, false, "陕西", HouseTypeEnum.DETACHED_HOUSES, 4, 3723);
        boolean ok = serviceToDaoRealization.addHouse(house);
    }

    @Test
    public void registerPeople() {
        Tenant tenant = new Tenant("Jack", "13124", "陕西", "13812742974", "2002-01-03", GenderEnum.FEMALE);
        Homeowner homeowner = new Homeowner("Rose", "2839249", "陕西", "218392743");
        boolean ok = serviceToDaoRealization.registerPeople(tenant, "9308204");
//        boolean ok=s.registerPeople(homeowner,"9203824");
//        boolean ok=s.registerPeople(tenant,"9308204"); // 已经注册返回false*/
    }

    @Test
    public void getPeople() {
        People people = serviceToDaoRealization.getPeople("13124", false); //查询租赁者Jack,true
//        boolean ok=s.getPeople("2839249",true,people); //查询房东Rose,true
//        boolean ok=s.getPeople("2839249",false,people); //查询不到，false*/
    }
}
