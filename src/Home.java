import MysqlConnection.MysqlConn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame{
    private JPanel MainJPanel;
    private JButton BtnDashboard;
    private JButton BtnLogout;
    private JButton reportsButton;
    private JButton recordsButton;
    private JButton settingsButton;

    public static void main(String[] args) {
//        code here
    }
    public Home() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainJPanel);
        BtnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main m =  new main();
                m.pack();
                m.setVisible(true);
                m.setResizable(false);
                m.setLocationRelativeTo(null);

//              Disconnect database
                MysqlConn mysqlConn = new MysqlConn();
                mysqlConn.closeConnection();
                dispose();
            }
        });
    }
}
