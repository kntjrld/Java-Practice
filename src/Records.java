import JDBConnnection.OracleConn;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Records extends JFrame {
    private JPanel RJPanel;
    private JButton BtnDashboard;
    private JButton recordsButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton BtnLogout;
    private JButton AddBtn;
    private JTextField TxtName;
    private JTextField txtAddress;
    private JButton EditBtn;
    private JScrollPane JTableDiv;
    private JTable tbl;
    private JTextField txtEmail;
    private JPanel JDate;
    private JComboBox<String> ValStatus;

    public Records() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(RJPanel);
        //#DATE PICKERs
        //-JDatePicker datePicker = new JDatePicker();
        //-JDate.add(datePicker);
        JDatePicker jDatePicker = new JDatePicker();
        JDate.add(jDatePicker);
        //#JComboBox
        ValStatus.addItem("Active");
        ValStatus.addItem("Inactive");
        //#Record info
        String name = TxtName.getText();
        String address = txtAddress.getText();
        Date birthday = (Date) jDatePicker.getModel().getValue();
        String status = ValStatus.toString();
        //#JTable
        String[] columnNames = {"ID", "Name", "Age", "Address", "Email", "Birthday", "Status"};
        Object[][] data = {
                {1, "John", 30, "xyz 203, ph", "john@gmail.com", "June 29, 2000", "Active"},
                {2, "Jane", 25, "ect sfd, sc", "jane@gmail.com", "May 09, 2002", "Active"}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
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
                Main m = new Main();
                m.pack();
                m.setVisible(true);
                m.setResizable(false);
                m.setLocationRelativeTo(null);
                //#Disconnect oracle database
                OracleConn.closeConnection();
                dispose();
            }
        });
        AddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }

    void AddMethod(String name, String address, Date birthday, int age, String status) throws SQLException {
        OracleConn conn = new OracleConn();
        String sql = "INSERT INTO tbl_records VALUES(?,?,?,?,?)";

        PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, address);
        stmt.setDate(3, (java.sql.Date) birthday);
        stmt.setInt(4, age);
        stmt.setString(5, status);


    }
}
