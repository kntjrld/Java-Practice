package auth;

import java.sql.SQLException;

public class auth {
    private String username;
    private String password;

    public auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() throws SQLException {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String setUsername(String username) {
        this.username = username;
        return username;
    }

    public String setPassword(String password) {
        this.password = password;
        return password;
    }
}
