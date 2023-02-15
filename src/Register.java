import MysqlConnection.MysqlConn;
import auth.auth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends JFrame{

    private JPanel LeftPanel;
    private JPanel LeftPanel2;
    private JLabel LeftLabel2;
    private JLabel LeftLabel3;
    private JPanel FooterPanel;
    private JLabel copyright;
    private JPanel RightPanel;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JLabel lbl_username;
    private JLabel lbl_password;
    private JPanel BtnPanel;
    private JButton RegisterBtn;
    private JButton BtnLogin;
    private JPanel MainPanel;
    private JPasswordField txtRPassword;

    public static void main(String[] args) {
//        code here
    }
    public Register(){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);

        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtPassword.getText().toString().equals(txtRPassword.getText().toString())){
                    String username = txtUsername.getText();
                    String password = txtPassword.getText().toString();
                    try {
                        auth authentication = new auth(username,password);
                        saveDB(authentication.getUsername(), authentication.getPassword());
                        JOptionPane.showMessageDialog(null,
                                "Success registration",
                                "System Message",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Something went wrong",
                                "Error Message",
                                JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }else{
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
                main m =  new main();
                m.pack();
                m.setVisible(true);
                m.setResizable(false);
                m.setLocationRelativeTo(null);
                dispose();
            }
        });
    }
    public void saveDB(String username, String password) throws SQLException {
        auth authentication = new auth(username,password);
        MysqlConn conn = new MysqlConn();
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement stmt = conn.getConnection().prepareStatement(sql);

        stmt.setString(1, authentication.getUsername());
        stmt.setString(2, authentication.getPassword());

        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
            conn.closeConnection();
        }
    }
}
