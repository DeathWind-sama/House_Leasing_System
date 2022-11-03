package dao;

import object.*;
import object.enums.GenderEnum;
import object.enums.HouseTypeEnum;

import java.sql.SQLException;
import java.util.ArrayList;


public class Test {
    public static void main(String[] args) throws SQLException {
        ServiceToDaoRealization s=new ServiceToDaoRealization();
        /*测试matchPeopleToLogin
       boolean ok=s.matchPeopleToLogin("1","2372932",false);*/

        /*测试registerPeople
        Tenant tenant=new Tenant("Jack","13124","陕西","13812742974","2002-01-03", GenderEnum.FEMALE);
        Homeowner homeowner=new Homeowner("Rose","2839249","陕西","218392743");
        boolean ok=s.registerPeople(tenant,"9308204");
        boolean ok=s.registerPeople(homeowner,"9203824");
        boolean ok=s.registerPeople(tenant,"9308204"); // 已经注册返回false*/

        /*//测试getPeople
        People people = null;
        boolean ok=s.getPeople("13124",false,people); //查询租赁者Jack,true
        boolean ok=s.getPeople("2839249",true,people); //查询房东Rose,true
        boolean ok=s.getPeople("2839249",false,people); //查询不到，false*/


        /* 测试addHouse
        House house=new House("128297","2839249",false,false,"陕西",HouseTypeEnum.DETACHED_HOUSES,4,3723);
        boolean ok= s.addHouse(house);*/

        /* 测试delHouse
        boolean ok = s.delHouse("21773626");*/

        /*测试getHouse
        House house=null;
        boolean ok=s.getHouse("128297",house);*/

        /*测试getOwnHouses
        ArrayList<House> houses=new ArrayList<>();
       boolean ok=s.getOwnHouses("122",houses); //true
        boolean ok=s.getOwnHouses("21898",houses); //false*/

        /*测试getAllPayedHouses和getSomePayedHouses
        ArrayList<House> houses=new ArrayList<>();
        boolean ok=s.getAllPayedHouses(houses);
        boolean ok=s.getSomePayedHouses(2,houses);*/


        /*测试unlockHouse
        ExpenseSheet expenseSheet=new ExpenseSheet("82932","2378344","Jack",false,238237,false,"1212736");
        boolean ok=s.unlockHouse(expenseSheet); // true*/

       /* 测试addExpenseSheet
        ExpenseSheet expenseSheet=new ExpenseSheet("82932","2378344","Wang",false,238237,false,"1212736");
        boolean ok=s.addExpenseSheet(expenseSheet); //true*/

        /*测试delExpenseSheet
        ExpenseSheet expenseSheet=new ExpenseSheet("82932","2378344","Wang",false,238237,false,"1212736");
        boolean ok=s.delExpenseSheet("82932");*/


        /* 测试getOwnExpenseSheets
        ArrayList<ExpenseSheet> expenseSheets=new ArrayList<>();
        Tenant tenant=new Tenant("Jack","13124","陕西","13812742974","2002-01-03", GenderEnum.FEMALE);
        boolean ok=s.getOwnExpenseSheets(tenant,expenseSheets);*/

        /*测试payExpenseSheet
        boolean ok=s.payExpenseSheet("24354");*/


        /* 测试 addCommunicationAuthority
        CommunicationAuthority communicationAuthority=new CommunicationAuthority("122","234","298497","645467");
        boolean ok=s.addCommunicationAuthority(communicationAuthority);*/


        /*测试delCommunicationAuthority
        boolean ok=s.delCommunicationAuthority("298497"); //将前面创建的CommunicationAuthority删掉*/

        /* 测试getOwnCommunicationAuthorities
       Tenant tenant=new Tenant("Jack","13124","陕西","13812742974","2002-01-03", GenderEnum.FEMALE);
        Homeowner homeowner=new Homeowner("Rose","2839249","陕西","218392743");
        ArrayList<CommunicationAuthority> communicationAuthorities=new ArrayList<>();
        boolean ok=s.getOwnCommunicationAuthorities("13124",communicationAuthorities);
        System.out.println(communicationAuthorities.get(0).getAuthorityID());*/

        /*测试addVisitRecord
        VisitRecord visitRecord=new VisitRecord("1212736","222","25346","2022-3-12","2022-9-12");
        boolean ok=s.addVisitRecord(visitRecord);*/

        /*测试getOwnVisitRecords
        ArrayList<VisitRecord> visitRecords=new ArrayList<>();
        boolean ok=s.getOwnVisitRecords("222",visitRecords);
        System.out.println(visitRecords.get(0).getTenantID());  //输出TenantID 25346*/

        /*测试addContract
        Contract contract=new Contract("17382","122","1",1831,821,"12821313");
        boolean ok=s.addContract(contract);*/


        /*测试getContract
        Contract contract=null;
        boolean ok=s.getContract("17382",contract);*/

        /*测试delContract
        //boolean ok=s.delContract("173282"); //没有这个ID false
        boolean ok=s.delContract("17382"); //删除刚刚创建的Contract true*/

        /*测试getOwnContract
        ArrayList<Contract> contracts=new ArrayList<>();
        Homeowner homeowner=new Homeowner("Jerry","122","华盛顿","12832083");
        boolean ok=s.getOwnContract(homeowner,contracts);
        System.out.println(contracts.get(0).getContractID()); //输出查询到的ID 17382*/



        //System.out.println(ok);


    }
}
