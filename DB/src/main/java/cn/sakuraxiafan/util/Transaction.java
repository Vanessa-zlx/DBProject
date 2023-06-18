package cn.sakuraxiafan.util;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {

    public static void begin() {
        try {
            DBUtil.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void commit() {
        try {
            DBUtil.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rollback() {
        try {
            DBUtil.getConnection().rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close() {
        try {
            DBUtil.getConnection().close();
            DBUtil.THREAD_LOCAL.remove();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
