package cn.sakuraxiafan.util;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class DBUtil {
    public final static ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    private static HikariDataSource dataSource;

    public static Connection getConnection(){
        Connection connection = THREAD_LOCAL.get();
        if (connection == null){
            try {
                if (dataSource==null){
                    dataSource  = new HikariDataSource(new HikariConfig("/hikari.properties"));
                }
                connection = dataSource.getConnection();
                if (connection==null){
                    throw new RuntimeException("获取连接异常");
                }
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                THREAD_LOCAL.set(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet){
        if (connection!=null){
            try {
                connection.close();
                THREAD_LOCAL.remove();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
