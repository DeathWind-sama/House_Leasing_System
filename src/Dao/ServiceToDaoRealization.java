package Dao;

import Object.*;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceToDaoRealization implements ServiceToDaoInterface {

    @Override
    //查看是否有该用户已被登记
    public boolean matchPeopleToLogin(String ID,String pwd){
        DBUtils.init_connection();

        //默认该客户已登记
        boolean isSuccess=true;

        ResultSet resultSet=null;
        String sql="select * FROM Customer where customer.ID=? and customer.password=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);
            preparedStatement.setString(2,pwd);

            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isSuccess;
    }

    @Override
    public boolean delHouse(String houseID) {
        return false;
    }

    @Override
    public boolean delExpenseSheet(String sheetID) {
        return false;
    }

    @Override
    public boolean payExpenseSheet(String sheetID) {
        return false;
    }

    @Override
    public boolean delCommunicationAuthority(String authorityID) {
        return false;
    }

    @Override
    public boolean delContract(String contractID) {
        return false;
    }

    @Override
    public boolean getOwnContract(People people, ArrayList<Contract> contractArrayListResult) {
        return false;
    }

    @Override
    public boolean getContract(String contractID, Contract contractResult) {
        return false;
    }

    @Override
    public boolean addContract(Contract contract) {
        return false;
    }

    @Override
    public boolean getOwnVisitRecords(String ID, ArrayList<VisitRecord> visitRecordArrayList) {
        return false;
    }

    @Override
    public boolean addVisitRecord(VisitRecord visitRecord) {
        return false;
    }

    @Override
    public boolean getOwnCommunicationAuthorities(String ID, ArrayList<CommunicationAuthority> communicationAuthorityArrayListResult) {
        return false;
    }

    @Override
    public boolean addCommunicationAuthority(CommunicationAuthority communicationAuthority) {
        return false;
    }

    @Override
    public boolean getOwnExpenseSheets(People people, ArrayList<ExpenseSheet> expenseSheetArrayListResult) {
        return false;
    }

    @Override
    public boolean addExpenseSheet(ExpenseSheet expenseSheet) {
        return false;
    }

    @Override
    public boolean unlockHouse(ExpenseSheet expenseSheet) {
        return false;
    }

    @Override
    public boolean getSomePayedHouses(int quantity, ArrayList<House> houseArrayListResult) {
        return false;
    }

    @Override
    public boolean getAllPayedHouses(ArrayList<House> houseArrayListResult) {
        return false;
    }

    @Override
    public boolean getOwnHouses(String ID, ArrayList<House> houseArrayListResult) {
        return false;
    }

    @Override
    public boolean getHouse(String houseID, House houseResult) {
        return false;
    }

    @Override
    public boolean addHouse(House house) {
        return false;
    }

    @Override
    public boolean getPeople(String ID, People peopleResult) {
        return false;
    }

    @Override
    //注册（登记）
    public boolean registerPeople(People people, String password) {
        DBUtils.init_connection();
        //默认注册成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            //查询改用户是否已被注册
            String sql_ask="select * FROM customer where customer.ID=?";
            preparedStatement = DBUtils.connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,people.getID());

            //处理返回结果集
            resultSet=preparedStatement.executeQuery();

            //如果该用户已被注册
            if(resultSet.next())
            {
                isSuccess=false;
            }
            else
            {
                //若用户没有注册过，则注册成功
                String sql="INSERT INTO customer(`ID`,`password`,`name`,`telNumber`,`address`) VALUES (?,?,?,?,?)";
                preparedStatement = DBUtils.connection.prepareStatement(sql);
                preparedStatement.setString(1,people.getID());
                preparedStatement.setString(2,password);
                preparedStatement.setString(3,people.getName());
                preparedStatement.setString(4,people.getTelNumber());
                preparedStatement.setString(5,people.getAddress());
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       DBUtils.close();
        return isSuccess;
    }
}
