import JDBConnnection.OracleConn;
import auth.auth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Register extends JFrame {

    ImageIcon success = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/success.png")));
    ImageIcon error = new ImageIcon(Objects.requireNonNull(getClass().getResource("icons/retry.png")));
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPanel BtnPanel;
    private JButton RegisterBtn;
    private JButton BtnLogin;
    private JPanel MainPanel;
    private JPasswordField txtRPassword;
    private JLabel IconMessage;

    public Register() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);
        //#set icon
        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtPassword.getText().toString().equals(txtRPassword.getText().toString())) {
                    String username = txtUsername.getText();
                    String password = txtPassword.getText().toString();
                    try {
                        auth authentication = new auth(username, password);
                        saveDB(authentication.getUsername(), authentication.getPassword());
                    } catch (SQLException ex) {
                        IconMessage.setIcon(error);
                        JOptionPane.showMessageDialog(null,
                                "Something went wrong",
                                "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                } else {
                    IconMessage.setIcon(error);
                    txtPassword.setText("");
                    txtRPassword.setText("");
                    JOptionPane.showMessageDialog(null,
                            "Password not match",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        BtnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main m = new Main();
                m.pack();
                m.setVisible(true);
                m.setResizable(false);
                m.setLocationRelativeTo(null);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
//        code here
    }

    public void saveDB(String username, String password) throws SQLException {
        auth authentication = new auth(username, password);
        //#Mysql JDBC
        //--MysqlConn conn = new MysqlConn();
        //#ORACLE JDBC
        OracleConn conn = new OracleConn();

        // Check if username is unique
        String check = "SELECT COUNT(*) as count FROM SYS.users WHERE username = ?";
        PreparedStatement stmt_check = conn.getConnection().prepareStatement(check);
        stmt_check.setString(1, authentication.getUsername());
        ResultSet rs = stmt_check.executeQuery();
        // Process the result
        if (rs.next()) {
            int count = rs.getInt("count");
            if (count > 0) {
                txtUsername.setText("");
                JOptionPane.showMessageDialog(null,
                        "Username is not available",
                        "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                OracleConn.closeConnection();
            } else {
                String sql = "INSERT INTO SYS.users (username, password) VALUES (?, ?)";
                PreparedStatement stmt = conn.getConnection().prepareStatement(sql);

                stmt.setString(1, authentication.getUsername());
                stmt.setString(2, authentication.getPassword());

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    IconMessage.setIcon(success);
                    txtUsername.setText("");
                    txtPassword.setText("");
                    txtRPassword.setText("");
                    JOptionPane.showMessageDialog(null,
                            "Success registration",
                            "System Message",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("A new user was inserted successfully!");
                    OracleConn.closeConnection();
                }
            }
        }
    }
}
