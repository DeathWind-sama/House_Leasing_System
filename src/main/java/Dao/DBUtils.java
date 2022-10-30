package Dao;


import java.sql.*;
import java.util.ResourceBundle;

public abstract class DBUtils {

    public static Connection connection = null;

    public static void init_connection() {
        //资源绑定器
        ResourceBundle bundle = ResourceBundle.getBundle("db");
        String driver = bundle.getString("driver");
        String url = bundle.getString("url");
        String user = bundle.getString("user");
        String password = bundle.getString("password");

        Statement stmt = null;
        ResultSet rs = null;
        try {

            //1.注册驱动
            Class.forName(driver);

            //2.获取数据库连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
