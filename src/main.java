import MysqlConnection.MysqlConn;
import auth.auth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class main extends JFrame{
    private JPanel PMain;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JButton LoginBtn;
    private JTextField UName;
    private JPasswordField UPass;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JPanel BtnPanel;
    private JButton BtnRegister;
    private JLabel LeftLabel2;
    private JLabel LeftLabel3;
    private JPanel LeftPanel2;
    private JLabel copyright;
    private JPanel FooterPanel;

    public static void main(String[] args) {
        main m =  new main();
        m.pack();
        m.setVisible(true);
        m.setResizable(false);
        m.setLocationRelativeTo(null);
    }

    public main(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(PMain);
//======> LoginBtn - for authentication
        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = UName.getText();
                String password = UPass.getText().toString();

                auth authentication = new auth(username,password);
                try {
                    if(authenticate(authentication.setUsername(username), authentication.setPassword(password))){
                        Home home = new Home();
                        home.pack();
                        home.setVisible(true);
                        home.setResizable(false);
                        home.setLocationRelativeTo(null);
                        dispose();
                    }else{
                        MysqlConn mysqlConn = new MysqlConn();
                        mysqlConn.closeConnection();

                        JOptionPane.showMessageDialog(null,
                                "Invalid credentials",
                                "Error Message",
                                JOptionPane.ERROR_MESSAGE);
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

    public boolean authenticate(String username, String password) throws SQLException {
        auth authentication = new auth(username, password);
        MysqlConn conn = new MysqlConn();
        String sql = "SELECT COUNT(*) as count FROM users WHERE username = ? and password = ?";
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
        conn.closeConnection();

        return false;
    }
}
