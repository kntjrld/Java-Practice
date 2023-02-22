import DML.AddRecord;
import JDBConnnection.OracleConn;
import org.jdatepicker.DatePicker;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Records extends JFrame {
    OracleConn conn = new OracleConn();
    DefaultTableModel tableModel = new DefaultTableModel();
    private JPanel RJPanel;
    private JButton BtnDashboard;
    private JButton recordsButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton BtnLogout;
    private JButton AddBtn;
    private JTextField TxtName;
    private JTextField txtAddress;
    private JScrollPane JTableDiv;
    private JTable tbl;
    private JTextField txtEmail;
    private JPanel JDate;
    private JComboBox<String> ValStatus;
    private JTextField txtAge;
    private JButton EditBtn;
    private JButton btnDelete;

    public Records() throws SQLException {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(RJPanel);
        //#Date Picker
        UtilDateModel utilDateModel = new UtilDateModel();
        DatePicker datePicker = new JDatePicker(utilDateModel);
        //#JDatePicker datePicker = new JDatePicker();
        JDate.add((Component) datePicker);
        //#JComboBox
        ValStatus.addItem("Active");
        ValStatus.addItem("Inactive");
        //# Load all record in db to J - table
        loadData();
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
                AddRecord addRecord = new AddRecord();
                addRecord.setName(TxtName.getText());
                addRecord.setAddress(txtAddress.getText());
                addRecord.setEmail(txtEmail.getText());
                addRecord.setStatus((String) ValStatus.getSelectedItem());
                addRecord.setAge(Integer.parseInt(txtAge.getText()));
                try {
                    AddMethod(addRecord.getName(), addRecord.getAge(), addRecord.getAddress(), addRecord.getEmail(), String.valueOf(datePicker.getModel().getMonth()), String.valueOf(datePicker.getModel().getDay()), String.valueOf(datePicker.getModel().getYear()), addRecord.getStatus());
                    loadData();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tbl.getSelectedRow();
                int column = tbl.getSelectedColumn();
                if (row != -1 && column != -1) { // Check if a cell is selected
                    Object value = tbl.getValueAt(row, 0);
                    String sql = "DELETE FROM sys.records WHERE ID = ?";
                    try {
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
                            stmt.setString(1, value.toString());
                            int rowDel = stmt.executeUpdate();
                            // Process the result
                            if (rowDel > 0) {
                                JOptionPane.showMessageDialog(null,
                                        "Deleted",
                                        "System Message",
                                        JOptionPane.ERROR_MESSAGE);
                                // Load table
                                loadData();
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Something went wrong",
                                        "System Message",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            System.out.println("Delete canceled");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Please select a record",
                            "System Message",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        txtAge.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
        EditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void loadData() throws SQLException {
        //Empty table every time called to avoid col and rows duplication -> alternative for tableModel.fireTableDataChanged()
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        String sql = "SELECT ID, NAME, AGE, ADDRESS, EMAIL, birth_month || '/' || birth_day || '/' || birth_year as birthday ,STATUS FROM sys.records";
        PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
        ResultSet rs = stmt.executeQuery(sql);
        // Populate the table model with the data retrieved from the ResultSet object
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            tableModel.addColumn(rs.getMetaData().getColumnName(i));
        }
        while (rs.next()) {
            Object[] row = new Object[rs.getMetaData().getColumnCount()];
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row[i - 1] = rs.getObject(i);
            }
            tableModel.addRow(row);
            tbl.setDefaultEditor(Object.class, null);
        }
        tbl.setModel(tableModel);
    }

    void AddMethod(String name, int age, String address, String email, String birth_month, String birth_day, String birth_year, String status) throws SQLException {
        String sql = "INSERT INTO SYS.records (NAME, AGE, ADDRESS, EMAIL, BIRTH_MONTH, BIRTH_DAY, BIRTH_YEAR, STATUS)VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setString(3, address);
        stmt.setString(4, email);
        stmt.setString(5, birth_month);
        stmt.setString(6, birth_day);
        stmt.setString(7, birth_year);
        stmt.setString(8, status);

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null,
                    "Success",
                    "System Message",
                    JOptionPane.ERROR_MESSAGE);
            tableModel.fireTableStructureChanged();
            System.out.println("A new record was inserted successfully!");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Error",
                    "System Message",
                    JOptionPane.ERROR_MESSAGE);
            System.out.println("Error...");
            OracleConn.closeConnection();
        }
    }
}
