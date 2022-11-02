package dao;

import object.*;
import object.enums.GenderEnum;
import object.enums.HouseTypeEnum;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

public  class ServiceToDaoRealization implements ServiceToDaoInterface {

    @Override
    //查看是否有该用户已被登记
    public boolean matchPeopleToLogin(String ID,String pwd,boolean isHomeowner){
        DBUtils.init_connection();

        //默认该客户已登记
        boolean isSuccess=true;

        ResultSet resultSet=null;
        String sql="select * FROM Tenant where Tenant.ID=? and Tenant.password=?";
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

        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean delHouse(String houseID) {
        //默认房子删除失败
        boolean isSuccess=false;
        DBUtils.init_connection();

        String sql="DELETE FROM house where house.houseID=? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,houseID);

            //处理返回结果集
            int result=preparedStatement.executeUpdate();
            if(result==1)
            {
                isSuccess=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();
        return isSuccess;

    }

    @Override
    public boolean delExpenseSheet(String sheetID)
    {
        //默认删除失败
        boolean isSuccess=false;
        DBUtils.init_connection();

        String sql="DELETE FROM expensesheet where expensesheet.sheetID=? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,sheetID);

            //处理返回结果集
            int result=preparedStatement.executeUpdate();
            if(result==1)
            {
                isSuccess=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();
        return isSuccess;

    }

    @Override
    public boolean payExpenseSheet(String sheetID) {
        //默认修改失败
        boolean isSuccess=false;
        DBUtils.init_connection();

        String sql="UPDATE `expensesheet` SET `isPayed`=TRUE WHERE sheetID= ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,sheetID);

            //处理返回结果集
            int result=preparedStatement.executeUpdate();
            if(result==1)
            {
                isSuccess=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean delCommunicationAuthority(String authorityID)
    {

        //默认删除失败
        boolean isSuccess=false;
        DBUtils.init_connection();

        String sql="DELETE FROM communicationauthority where authorityID=? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,authorityID);

            //处理返回结果集
            int result=preparedStatement.executeUpdate();
            if(result==1)
            {
                isSuccess=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();
        return isSuccess;

    }

    @Override
    public boolean delContract(String contractID)
    {
        //默认删除失败
        boolean isSuccess=false;
        DBUtils.init_connection();

        String sql="DELETE FROM contract where ID=? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,contractID);

            //处理返回结果集
            int result=preparedStatement.executeUpdate();
            if(result==1)
            {
                isSuccess=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean getOwnContract(People people, ArrayList<Contract> contractArrayListResult)
    {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            String sql="select * FROM contract where homeownerID=? or tenantID=?";
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,people.getID());
            preparedStatement.setString(2,people.getID());


            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }

            else
            {
                do {
                    Contract contract=new Contract(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4),resultSet.getDouble(5));
                    contractArrayListResult.add(contract);
                }while (resultSet.next());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();

        return isSuccess;
    }

    @Override
    public boolean getContract(String contractID, Contract contractResult)
    {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            String sql="select * FROM contract where ID=? ";
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,contractID);


            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }

            else
            {
                Contract contract=new Contract(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getDouble(4),resultSet.getDouble(5));
                contractResult=contract;
                System.out.println(contract.getHomeownerID());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();

        return isSuccess;
    }

    @Override
    public boolean addContract(Contract contract)
    {
        DBUtils.init_connection();
        //默认注册成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            //查询是否已经被申请
            String sql_ask="select * FROM Contract where ID=?";
            preparedStatement = DBUtils.connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,contract.getContractID());


            //处理返回结果集
            resultSet=preparedStatement.executeQuery();

            //如果已被注册
            if(resultSet.next())
            {
                isSuccess=false;
            }
            else
            {
                //若该没有注册过，则注册成功
                String sql="INSERT INTO Contract(`ID`,`houseID`,`homeownerID`,`tenantID`,`payTime`,`appointedTime`) VALUES(?,?,?,?,?)";
                preparedStatement = DBUtils.connection.prepareStatement(sql);
                preparedStatement.setString(1,contract.getContractID());
                preparedStatement.setString(2,contract.getHomeownerID());
                preparedStatement.setString(3,contract.getTenantID());
                preparedStatement.setDouble(4,contract.getMonthlyRent());
                preparedStatement.setDouble(5,contract.getRentArrears());

                preparedStatement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean getOwnVisitRecords(String ID, ArrayList<VisitRecord> visitRecordArrayList)
    {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            String sql="select * FROM visitrecord where tenantID=? ";
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);


            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }

            else
            {
                do {
                    VisitRecord visitRecord=new VisitRecord(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
                    visitRecordArrayList.add(visitRecord);


                }while (resultSet.next());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();

        return isSuccess;
    }

    @Override
    public boolean addVisitRecord(VisitRecord visitRecord)
    {
        DBUtils.init_connection();
        //默认注册成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            //查询是否已经被申请
            String sql_ask="select * FROM communicationauthority where houseID=?and tenantID=?";
            preparedStatement = DBUtils.connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,visitRecord.getHouseID());
            preparedStatement.setString(2,visitRecord.getTenantID());

            //处理返回结果集
            resultSet=preparedStatement.executeQuery();

            //如果已被注册
            if(resultSet.next())
            {
                isSuccess=false;
            }
            else
            {
                //若该没有注册过，则注册成功
                String sql="INSERT INTO `visitrecord`(`houseID`,`homeownerID`,`tenantID`,`payTime`,`appointedTime`) VALUES(?,?,?,?,?)";
                preparedStatement = DBUtils.connection.prepareStatement(sql);
                preparedStatement.setString(1,visitRecord.getHouseID());
                preparedStatement.setString(2,visitRecord.getHomeownerID());
                preparedStatement.setString(3,visitRecord.getTenantID());
                preparedStatement.setString(4,visitRecord.getPayTime());
                preparedStatement.setString(5,visitRecord.getAppointedTime());


                preparedStatement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean getOwnCommunicationAuthorities(String ID, ArrayList<CommunicationAuthority> communicationAuthorityArrayListResult)
    {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            String sql="select * FROM communicationauthority where tenantID=? ";
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);


            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }

            else
            {
                do {
                    CommunicationAuthority communicationAuthority=new CommunicationAuthority(resultSet.getString(1), resultSet.getString(2));
                    communicationAuthorityArrayListResult.add(communicationAuthority);

                }while (resultSet.next());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();

        return isSuccess;
    }

    @Override
    public boolean addCommunicationAuthority(CommunicationAuthority communicationAuthority)
    {
        DBUtils.init_connection();
        //默认注册成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            //查询communicationAuthority是否已经被申请
            String sql_ask="select * FROM communicationauthority where homeownerID=? and tenantID=?";
            preparedStatement = DBUtils.connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,communicationAuthority.getHomeownerID());
            preparedStatement.setString(2,communicationAuthority.getTenantID());

            //处理返回结果集
            resultSet=preparedStatement.executeQuery();

            //如果已被注册
            if(resultSet.next())
            {
                isSuccess=false;
            }
            else
            {
                //若该没有注册过，则注册成功
                String sql="INSERT INTO communicationauthority(homeownerID,tenantID) VALUES (?,?)";
                preparedStatement = DBUtils.connection.prepareStatement(sql);
                preparedStatement.setString(1,communicationAuthority.getHomeownerID());
                preparedStatement.setString(2,communicationAuthority.getTenantID());


                preparedStatement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean getOwnExpenseSheets(People people, ArrayList<ExpenseSheet> expenseSheetArrayListResult)
    {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            String sql="select * FROM expensesheet where payerID=? ";
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,people.getID());


            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }

            else
            {
                do {
                    ExpenseSheet expenseSheet=new ExpenseSheet(resultSet.getString(6), resultSet.getString(1),resultSet.getString(2),resultSet.getBoolean(3), resultSet.getDouble(4),resultSet.getBoolean(7),resultSet.getString(5));
                    expenseSheetArrayListResult.add(expenseSheet);


                }while (resultSet.next());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();

        return isSuccess;
    }

    @Override
    public boolean addExpenseSheet(ExpenseSheet expenseSheet)
    {

        DBUtils.init_connection();
        //默认注册成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            //查询该sheetID是否已被注册
            String sql_ask="select * FROM expensesheet where sheetID=?";
            preparedStatement = DBUtils.connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,expenseSheet.getSheetID());

            //处理返回结果集
            resultSet=preparedStatement.executeQuery();

            //如果该sheetID已被注册
            if(resultSet.next())
            {
                isSuccess=false;
            }
            else
            {
                //若该sheetID没有注册过，则注册成功
                String sql="INSERT INTO `expensesheet`(`sheetID`,`payerID`,`payerName`,`isHomeowner`,`payAmount`,`isPayed`,'houseID') VALUES (?,?,?,?,?,?,?)";
                preparedStatement = DBUtils.connection.prepareStatement(sql);
                preparedStatement.setString(1,expenseSheet.getSheetID());
                preparedStatement.setString(2,expenseSheet.getPayerID());
                preparedStatement.setString(3,expenseSheet.getPayerName());
                preparedStatement.setBoolean(4,expenseSheet.getIsHomeOwner());
                preparedStatement.setDouble(5,expenseSheet.getPayAmount());
                preparedStatement.setBoolean(6,expenseSheet.getIsPayed());
                preparedStatement.setString(7,expenseSheet.getHouseID());

                preparedStatement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean unlockHouse(ExpenseSheet expenseSheet)
    {
        //默认修改失败
        boolean isSuccess=false;
        DBUtils.init_connection();

        String sql="UPDATE `expensesheet` SET `isPayed`=TRUE WHERE houseID= ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,expenseSheet.getHouseID());

            //处理返回结果集
            int result=preparedStatement.executeUpdate();
            if(result==1)
            {
                isSuccess=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean getSomePayedHouses(int quantity, ArrayList<House> houseArrayListResult)
    {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;
        int s=0;
        ResultSet resultSet=null;
        String sql="select * FROM house where house.isLeased=TRUE ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);



            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }

            else
            {
                do {
                    House house=new House(resultSet.getString(1), resultSet.getString(2),resultSet.getBoolean(3), resultSet.getBoolean(4),resultSet.getString(5),HouseTypeEnum.valueOf((String) resultSet.getObject(6)),resultSet.getInt(7),resultSet.getInt(8));
                    houseArrayListResult.add(house);
                    s++;

                }while (resultSet.next()&&s<quantity);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();

        return isSuccess;
    }

    @Override
    public boolean getAllPayedHouses(ArrayList<House> houseArrayListResult)
    {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;

        ResultSet resultSet=null;
        String sql="select * FROM house where house.isLeased=TRUE ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);



            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }

            else
            {
                do {
                    House house=new House(resultSet.getString(1), resultSet.getString(2),resultSet.getBoolean(3), resultSet.getBoolean(4),resultSet.getString(5),HouseTypeEnum.valueOf((String) resultSet.getObject(6)),resultSet.getInt(7),resultSet.getInt(8));
                    houseArrayListResult.add(house);

                }while (resultSet.next());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();

        return isSuccess;
    }

    @Override
    public boolean getOwnHouses(String ID, ArrayList<House> houseArrayListResult) {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;

        ResultSet resultSet=null;
        String sql="select * FROM house where house.ownerID=? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,ID);


            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }

            else
            {
                do {
                    House house=new House(resultSet.getString(1), resultSet.getString(2),resultSet.getBoolean(3), resultSet.getBoolean(4),resultSet.getString(5),HouseTypeEnum.valueOf((String) resultSet.getObject(6)),resultSet.getInt(7),resultSet.getInt(8));
                    houseArrayListResult.add(house);

                }while (resultSet.next());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();

        return isSuccess;
    }

    @Override
    public boolean getHouse(String houseID, House houseResult) {
        DBUtils.init_connection();

        //默认查询成功
        boolean isSuccess=true;

        ResultSet resultSet=null;
        String sql="select * FROM house where house.houseID=? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBUtils.connection.prepareStatement(sql);
            preparedStatement.setString(1,houseID);


            //处理返回结果集
            resultSet=preparedStatement.executeQuery();
            if(!resultSet.next())
            {
                isSuccess=false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        DBUtils.close();
        return isSuccess;
    }

    @Override
    public boolean addHouse(House house) {

        DBUtils.init_connection();
        //默认注册成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;

        try {
            //查询该house是否已被注册
            String sql_ask="select * FROM house where houseID=?";
            preparedStatement = DBUtils.connection.prepareStatement(sql_ask);
            preparedStatement.setString(1,house.getHouseID());

            //处理返回结果集
            resultSet=preparedStatement.executeQuery();

            //如果该house已被注册
            if(resultSet.next())
            {
                isSuccess=false;
            }
            else
            {
                //若该house没有注册过，则注册成功
                String sql="INSERT INTO `house`(`houseID`) VALUES (?)";
                preparedStatement = DBUtils.connection.prepareStatement(sql);
                preparedStatement.setString(1,house.getHouseID());

                preparedStatement.execute();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtils.close();
        return isSuccess;
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
            String sql_ask="select * FROM Tenant where Tenant.ID=?";
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
                String sql="INSERT INTO Tenant(`ID`,`password`,`name`,`telNumber`,`address`) VALUES (?,?,?,?,?)";
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

    @Override
    public boolean getPeople(String ID,People peopleResult,boolean isHomeowner)
    {
        DBUtils.init_connection();
        //默认注册成功
        boolean isSuccess=true;

        ResultSet resultSet=null;

        PreparedStatement preparedStatement = null;
        String sql_ask_1="select * FROM Tenant where Tenant.ID=?";
        String sql_ask_2="select * FROM Tenant where houseowner.ID=?";

        try {
            //查询改用户是否已被注册
            if(!isHomeowner)
            {
                preparedStatement = DBUtils.connection.prepareStatement(sql_ask_1);
                preparedStatement.setString(1,ID);
                //处理返回结果集
                resultSet=preparedStatement.executeQuery();

                //如果没有查询到该用户
                if(!resultSet.next())
                {
                    isSuccess=false;
                }
                else
                {
                    Tenant tenant=new Tenant(resultSet.getString(2),resultSet.getString(1),resultSet.getString(4),resultSet.getString(3),resultSet.getString(6),GenderEnum.valueOf((String)resultSet.getObject(7)));
                    peopleResult=tenant;
                }
            }
            else
            {
                preparedStatement = DBUtils.connection.prepareStatement(sql_ask_2);
                preparedStatement.setString(1,ID);
                //如果没有查询到该用户
                if(!resultSet.next())
                {
                    isSuccess=false;
                }
                else
                {
                    Homeowner homeowner=new Homeowner(resultSet.getString(2),resultSet.getString(1),resultSet.getString(4),resultSet.getString(3));
                    peopleResult=homeowner;
                }
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtils.close();
        return isSuccess;
    }
}
