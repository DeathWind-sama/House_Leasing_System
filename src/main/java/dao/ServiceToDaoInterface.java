package dao;

import object.*;

import java.util.ArrayList;

/**
 * 若返回类型为boolean，则意为返回“xx是否成功”
 * 查询结果放在以“Result”为结尾的参数上
 * 若要返回对象，一定要保证此对象的所有属性均已赋值到位
 */
public interface ServiceToDaoInterface {
    //People    注意判断两种人
    boolean matchPeopleToLogin(String ID, String psw,boolean isHomeowner);//查看是否有该用户已被登记
    boolean registerPeople(People people,String password);//注册（登记）
    People getPeople(String ID,boolean isHomeowner);//查询
//    boolean getAllHomeowner(ArrayList<Homeowner> homeownerArrayList);//返回所有房主（管理员权限）
//    boolean getAllTenant(ArrayList<Tenant> tenantArrayList);//返所有租赁者（管理员权限）

    //House
    boolean addHouse(House house);//添加。要生成房屋的唯一识别码。房子默认设为不可检索
    boolean delHouse(String houseID);//删除
    House getHouse(String houseID);//查询
    ArrayList<House> getOwnHouses(String ID);//获取一个房主登记的所有房子
    //    boolean getAllHouses(ArrayList<House> houseArrayList);//返回所有房子（管理员权限）
    ArrayList<House> getAllPayedHouses();//返回可被检索到的所有房子
    ArrayList<House> getSomePayedHouses(int quantity);//返回quantity个可被检索到的房子
    boolean unlockHouse(String houseID);//通过费用单信息将对应房屋设为可被检索

    //Expense Sheet
    boolean addExpenseSheet(ExpenseSheet expenseSheet);//添加。要生成唯一识别码
    boolean delExpenseSheet(String sheetID);//删除
    ArrayList<ExpenseSheet> getOwnExpenseSheets(People people);//返回一个人的所有账单，无论有没有付款
    //    boolean getAllExpenseSheets(ArrayList<ExpenseSheet> expenseSheetArrayList);//返回所有账单（管理员权限）
    boolean payExpenseSheet(String sheetID);//付钱了，修改isPayed

    //Communication Authority
    boolean addCommunicationAuthority(CommunicationAuthority communicationAuthority);//添加
    boolean delCommunicationAuthority(String authorityID);//删除
    ArrayList<CommunicationAuthority> getOwnCommunicationAuthorities(String ID);//返回一个人的所有交流许可
    boolean modifyCommunicationAuthority(String ID,String appointedTime,String appointedPlace);//修改时间地点并自动把权限转换
    CommunicationAuthority getCommunicationAuthority(String communicationID);

    //Visit Record
    boolean addVisitRecord(VisitRecord visitRecord);//添加
    ArrayList<VisitRecord> getOwnVisitRecords(String ID);//返回一个人的所有的看房记录
//    boolean getAllVisitRecord(ArrayList<VisitRecord> visitRecordArrayList);//返回所有的看房记录（管理员权限）

    //Contract
    boolean addContract(Contract contract);//添加。要生成唯一识别码
    boolean delContract(String contractID);//删除
    Contract getContract(String contractID);//查询
    ArrayList<Contract> getOwnContracts(People people);//返回一个人的所有合同


//    boolean getAllContract(ArrayList<Contract> contractArrayList);//返回所有的合同（管理员权限）
}
