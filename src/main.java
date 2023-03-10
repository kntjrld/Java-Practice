import JDBConnnection.OracleConn;
import auth.auth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Main extends JFrame {
    private JPanel PMain;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JButton LoginBtn;
    private JTextField UName;
    private JPasswordField UPass;
    private JPanel BtnPanel;
    private JButton BtnRegister;
    private JLabel IconChecker;

    public Main() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(PMain);
        //#set icon
        ImageIcon success = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/success.png")));
        ImageIcon error = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/retry.png")));

        //# LoginBtn - for authentication
        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = UName.getText();
                String password = UPass.getText();
                auth authentication = new auth(username, password);
                try {
                    if (authenticate(authentication.setUsername(username), authentication.setPassword(password))) {
                        IconChecker.setIcon(success);
                        Dashboard dashboard = new Dashboard();
                        dashboard.pack();
                        dashboard.setVisible(true);
                        dashboard.setResizable(false);
                        dashboard.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        IconChecker.setIcon(error);
                        JOptionPane.showMessageDialog(null,
                                "Invalid Credentials",
                                "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                        System.out.println("Invalid username or password...");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        BtnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                register.pack();
                register.setVisible(true);
                register.setResizable(false);
                register.setLocationRelativeTo(null);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.pack();
        m.setVisible(true);
        m.setResizable(false);
        m.setLocationRelativeTo(null);
    }

    public boolean authenticate(String username, String password) throws SQLException {
        auth authentication = new auth(username, password);
        //#MYSQL JDBC
        //--MysqlConn conn = new MysqlConn();
        //--String sql = "SELECT COUNT(*) as count FROM users WHERE username = ? and password = ?";
        //#ORACLE JDBC
        OracleConn conn = new OracleConn();
        String sql = "SELECT COUNT(*) as count FROM SYS.users WHERE username = ? and password = ?";

        PreparedStatement stmt = conn.getConnection().prepareStatement(sql);

        stmt.setString(1, authentication.getUsername());
        stmt.setString(2, authentication.getPassword());

        // Execute the query and get the results
        ResultSet rs = stmt.executeQuery();
        // Process the result
        if (rs.next()) {
            int count = rs.getInt("count");
            return count > 0;
        }

        // Close the resources
        rs.close();
        stmt.close();
        OracleConn.closeConnection();

        return false;
    }
}
