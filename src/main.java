import auth.auth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton BtnForgot;
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
                if(authenticate(UName.getText(), UPass.getText().toString())){
                    Home home = new Home();
                    home.pack();
                    home.setVisible(true);
                    home.setResizable(false);
                    home.setLocationRelativeTo(null);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Invalid credentials",
                            "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

    public boolean authenticate(String username, String password) {
        auth authentication = new auth(username,password);
        return authentication.getUsername().equals(username) && authentication.getPassword().equals(password);
    }
}
