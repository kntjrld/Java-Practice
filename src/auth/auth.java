package auth;

import MysqlConnection.MysqlConn;

public class auth {
    private String username;
    private String password;

    MysqlConn mysqlConn = new MysqlConn();

    public auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        mysqlConn.getConnection();
//        Statement stmt = mysqlConn.createStatement();
//        try {
//            ResultSet rs = stmt.executeQuery("SELECT * FROM intellijdb");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        this.username = "username";
        return username;
    }

    public String getPassword() {
        this.password = "password";
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
