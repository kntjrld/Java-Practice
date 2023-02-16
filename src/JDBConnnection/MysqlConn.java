package JDBConnnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConn {
    private Connection connection;

    public MysqlConn() {
        String url = "jdbc:mysql://localhost:3306/intellijdb";
        String user = "root";
        String password = "admin";

        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to MySQL database has been established.");
        } catch (SQLException e) {
            System.out.println("Connection to MySQL database has failed: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection to MySQL database has been closed.");
        } catch (SQLException e) {
            System.out.println("Closing the connection to MySQL database has failed: " + e.getMessage());
        }
    }
}
