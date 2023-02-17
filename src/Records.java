import JDBConnnection.OracleConn;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Records extends JFrame{
    private JPanel RJPanel;
    private JButton BtnDashboard;
    private JButton recordsButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton BtnLogout;
    private JTable tbl;
    private JPanel TblPanel;
    private JButton AddBtn;
    private JTextField TxtName;
    private JTextField txtAddress;
    private JButton EditBtn;
    private JTextField txtEmail;

    public Records() {
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(RJPanel);
    //#DATE PICKERs
    //-JDatePicker datePicker = new JDatePicker();
    //-JDate.add(datePicker);

        String[] columnNames = {"ID", "Name", "Age","Address","Email","Birthday","Status"};
        Object[][] data = {
                {1, "John", 30,"xyz 203, ph","john@gmail.com","June 29, 2000", "Active"},
                {2, "Jane", 25,"ect sfd, sc","jane@gmail.com","May 09, 2002", "Active"}
        };
        DefaultTableModel model = new DefaultTableModel(data,columnNames);
        tbl.setModel(model);

    BtnDashboard.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Dashboard dashboard = new Dashboard();
            dashboard.pack();
            dashboard.setVisible(true);
            dashboard.setResizable(false);
            dashboard.setLocationRelativeTo(null);
            dispose();
        }
    });
        BtnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main m =  new Main();
                m.pack();
                m.setVisible(true);
                m.setResizable(false);
                m.setLocationRelativeTo(null);
                //#Disconnect oracle database
                OracleConn.closeConnection();
                dispose();
            }
        });
    }
}
