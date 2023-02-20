package JDBConnnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConn {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "SYSTEM";
    private static final String DB_PASSWORD = "admin";
    private static Connection conn = null;

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                System.out.println("Connection to Oracle database has been closed.");
            } catch (SQLException ex) {
                System.out.println("Error closing database connection: " + ex.getMessage());
            }
        }
    }

    public Connection getConnection() throws SQLException {
        if (conn == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Connection to Oracle database has been established.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Could not find database driver class.");
            }
        }
        return conn;
    }
}