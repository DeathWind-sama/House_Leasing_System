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
    //People    注意判断两种人
    boolean matchPeopleToLogin(String ID, String psw);//查看是否有该用户已被登记
    boolean registerPeople(People people,String password);//注册（登记）
    boolean getPeople(String ID,People peopleResult);//查询
//    boolean getAllHomeowner(ArrayList<Homeowner> homeownerArrayList);//返回所有房主（管理员权限）
//    boolean getAllTenant(ArrayList<Tenant> tenantArrayList);//返回所有租赁者（管理员权限）

    //House
    boolean addHouse(House house);//添加。要生成房屋的唯一识别码。房子默认设为不可检索
    boolean delHouse(String houseID);//删除
    boolean getHouse(String houseID,House houseResult);//查询
    boolean getOwnHouses(String ID,ArrayList<House> houseArrayListResult);//获取一个房主登记的所有房子
//    boolean getAllHouses(ArrayList<House> houseArrayList);//返回所有房子（管理员权限）
    boolean getAllPayedHouses(ArrayList<House> houseArrayListResult);//返回可被检索到的所有房子
    boolean getSomePayedHouses(int quantity,ArrayList<House> houseArrayListResult);//返回quantity个可被检索到的房子
    boolean unlockHouse(ExpenseSheet expenseSheet);//通过费用单信息将对应房屋设为可被检索

    //Expense Sheet
    boolean addExpenseSheet(ExpenseSheet expenseSheet);//添加。要生成唯一识别码
    boolean delExpenseSheet(String sheetID);//删除
    boolean getOwnExpenseSheets(People people,ArrayList<ExpenseSheet> expenseSheetArrayListResult);//返回一个人的所有账单，无论有没有付款
//    boolean getAllExpenseSheets(ArrayList<ExpenseSheet> expenseSheetArrayList);//返回所有账单（管理员权限）
    boolean payExpenseSheet(String sheetID);//付钱了，修改isPayed

    //Communication Authority
    boolean addCommunicationAuthority(CommunicationAuthority communicationAuthority);//添加
    boolean delCommunicationAuthority(String authorityID);//删除
    boolean getOwnCommunicationAuthorities(String ID,ArrayList<CommunicationAuthority> communicationAuthorityArrayListResult);//返回一个人的所有交流许可

    //Visit Record
    boolean addVisitRecord(VisitRecord visitRecord);//添加
    boolean getOwnVisitRecords(String ID,ArrayList<VisitRecord> visitRecordArrayList);//返回一个人的所有的看房记录
//    boolean getAllVisitRecord(ArrayList<VisitRecord> visitRecordArrayList);//返回所有的看房记录（管理员权限）

    //Contract
    boolean addContract(Contract contract);//添加。要生成唯一识别码
    boolean delContract(String contractID);//删除
    boolean getContract(String contractID,Contract contractResult);//查询
    boolean getOwnContract(People people,ArrayList<Contract> contractArrayListResult);//返回一个人的所有合同
//    boolean getAllContract(ArrayList<Contract> contractArrayList);//返回所有的合同（管理员权限）
}
