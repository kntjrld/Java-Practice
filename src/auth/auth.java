package auth;

public class auth {
    private String username;
    private String password;

    public auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
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
