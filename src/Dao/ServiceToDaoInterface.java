package Dao;

import Object.*;

import java.util.ArrayList;

/**
 * 若返回类型为boolean，则意为返回“xx是否成功”
 * 若返回值为ArrayList等引用类型，请每次都new一个新引用再赋值，使得旧表与新表独立
 * 虽然删除某项要以整个类作为参数，但实际上只要配对ID即可
 * 若要返回对象，一定要保证此对象的所有属性均已赋值到位
 */
public interface ServiceToDaoInterface {
    //People
    boolean matchUserToLogin(String ID, String psw);//查看是否有该用户已被登记
    boolean registerHomeowner(People people,String password);//注册（登记）
//    ArrayList<Homeowner> getAllHomeowner();//返回所有房主（管理员权限）
//    ArrayList<Tenant> getAllTenant();//返回所有租赁者（管理员权限）

    //House
    boolean addHouse(House house);//添加。要生成房屋的唯一识别码。房子默认设为不可检索
    boolean delHouse(House house);//删除
    ArrayList<House> getOwnHouses(Homeowner homeowner);//获取一个房主登记的所有房子
//    ArrayList<House> getAllHouses();//返回所有房子（管理员权限）
    ArrayList<House> getAllPayedHouses();//返回可被检索到的所有房子
    boolean unlockHouse(ExpenseSheet expenseSheet);//房主付钱后，通过费用单信息将对应房屋设为可被检索

    //Expense Sheet
    boolean addExpenseSheet(ExpenseSheet expenseSheet);//添加。要生成唯一识别码,并调用setSheetID修改对象中的ID以供前端使用
    boolean delExpenseSheet(ExpenseSheet expenseSheet);//删除
    ArrayList<ExpenseSheet> getOwnExpenseSheets(People people);//返回一个人的所有账单，无论有没有付款
//    ArrayList<ExpenseSheet> getAllExpenseSheets();//返回所有账单（管理员权限）
    boolean payExpenseSheet(ExpenseSheet expenseSheet);//付钱了，修改isPayed，并调用setIsPayed

    //Communication Authority
    boolean addCommunicationAuthority(CommunicationAuthority communicationAuthority);//租赁者给看房申请付钱后，添加交流许可
//    boolean delCommunicationAuthority(CommunicationAuthority communicationAuthority);//删除（管理员权限）
    ArrayList<CommunicationAuthority> getCommunicationAuthority(People people);//返回一个人的所有交流许可

    //Visit Record
    boolean addVisitRecord(VisitRecord visitRecord);//添加。要删除对应的交流许可
    ArrayList<VisitRecord> getOwnVisitRecord(People people);//返回一个人的所有的看房记录
//    ArrayList<VisitRecord> getAllVisitRecord();//返回所有的看房记录（管理员权限）

    //Contract
    boolean addContract(Contract contract);//添加
    boolean delContract(Contract contract);//删除
    boolean getOwnContract(People people);//返回一个人的所有合同
//    ArrayList<Contract> getContract();//返回所有的合同（管理员权限）
}

//    boolean matchUserToLogin(String ID, String psw);
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
