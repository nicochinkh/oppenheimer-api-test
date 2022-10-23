package utils;

import org.mariadb.jdbc.Connection;
import org.mariadb.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUtils {

    public static Connection connection;

    public static Connection connectToDB() {
        try {
            connection = (Connection) DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/testdb",
                    "user", "pwd"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static ResultSet query(String sqlQuery) {
        connectToDB();
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return resultSet;
    }

    public static ResultSet getLastHero() {
        return query("select * from working_class_heroes order by id desc limit 1");
    }

    public static int delete(String sql) {
        System.out.println(">>>>>> delete ops: " + sql);
        connectToDB();
        Statement statement = connection.createStatement();
        int updates = 0;
        try {
            updates = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return updates;
    }

    public static int deleteHero(String natid) {
        return delete("delete from working_class_heroes where natid = '" + natid + "'");
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
