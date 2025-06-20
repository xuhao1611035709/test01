package com.jwvdp.books.utils;



import java.sql.*;
import java.util.Properties;

public class DbUtil {

    private static Properties reqProperties = PropertyUtil.getFileInstance("/DB.properties");

    public static Connection getConnection(){
        Connection conn=null;
        try {
            String url=reqProperties.getProperty("mysql.tstd.url");
            String user=reqProperties.getProperty("mysql.tstd.username");
            String password=reqProperties.getProperty("mysql.tstd.password");
            String driver = reqProperties.getProperty("mysql.tstd.driverClassName");
            Class.forName(driver);//加载数据驱动
            conn = DriverManager.getConnection(url, user, password);// 连接数据库
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("加载数据库驱动失败");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("连接数据库失败");
        }

        return conn;
    }
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(ps!=null){
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
