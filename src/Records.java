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
    //#Date Picker
    UtilDateModel utilDateModel = new UtilDateModel();
    DatePicker datePicker = new JDatePicker(utilDateModel);
    AddRecord addRecord = new AddRecord();
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
    private JButton btnClear;
    private JButton BtnSave;

    public Records() throws SQLException {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(RJPanel);
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
                SetRecord();
                try {
                    AddMethod(addRecord.getName(), addRecord.getAge(), addRecord.getAddress(), addRecord.getEmail(), datePicker.getModel().getMonth(), String.valueOf(datePicker.getModel().getDay()), String.valueOf(datePicker.getModel().getYear()), addRecord.getStatus());
                    loadData();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        BtnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetRecord();
                try {
                    UpdateRecord(addRecord.getId(), addRecord.getName(), addRecord.getAge(), addRecord.getAddress(), addRecord.getEmail(), datePicker.getModel().getMonth(), String.valueOf(datePicker.getModel().getDay()), String.valueOf(datePicker.getModel().getYear()), addRecord.getStatus());
                    loadData();
                    Clear();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        EditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tbl.getSelectedRow();
                int column = tbl.getSelectedColumn();
                if (row != -1 && column != -1) { // Check if a cell is selected
                    Object value = tbl.getValueAt(row, 0);
                    String sql = "SELECT ID, NAME, AGE, ADDRESS, EMAIL, birth_month, birth_day, birth_year ,STATUS FROM sys.records WHERE ID = ?";
                    PreparedStatement stmt = null;
                    try {
                        stmt = conn.getConnection().prepareStatement(sql);
                        stmt.setString(1, value.toString());

                        // Execute the query and get the results
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            //Retrieve records from database
                            String xName = rs.getString("NAME");
                            int xAge = rs.getInt("AGE");
                            String xAddress = rs.getString("ADDRESS");
                            String xEmail = rs.getString("EMAIL");
                            String xMonth = rs.getString("birth_month");
                            String xDay = rs.getString("birth_day");
                            String xYear = rs.getString("birth_year");
                            String xStatus = rs.getString("STATUS");
                            // Forward value to class
                            addRecord.setId(rs.getInt("ID"));
                            //#Display value to input field for update
                            TxtName.setText(xName);
                            txtAge.setText(String.valueOf(xAge));
                            txtAddress.setText(xAddress);
                            txtEmail.setText(xEmail);
                            //#retrieve mm-dd-yyyy to database and set date to datePicker
                            datePicker.getModel().setDate(Integer.parseInt(xYear), Integer.parseInt(xMonth) - 1, Integer.parseInt(xDay));
                            datePicker.getModel().setSelected(true);
                            //# get the index of value and set it to selected index
                            int i;
                            if (xStatus.equals("Active")) {
                                i = 0;
                            } else {
                                i = 1;
                            }
                            ValStatus.setSelectedIndex(i);
                            //#Finally execute method
                            EditMethod();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    System.out.println("ERROR: No selected record");
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
                                // Load table and clear values
                                loadData();
                                Clear();
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
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clear();
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
    }

    private void loadData() throws SQLException {
        //Empty table every time called to avoid col and rows duplication -> alternative for tableModel.fireTableDataChanged()
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        String sql = "SELECT ID, NAME, AGE, ADDRESS, EMAIL, birth_month || '/' || birth_day || '/' || birth_year as birthday ,STATUS FROM sys.records ORDER BY ID ASC";
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

    private void AddMethod(String name, int age, String address, String email, int birth_month, String birth_day, String birth_year, String status) throws SQLException {
        String sql = "INSERT INTO SYS.records (NAME, AGE, ADDRESS, EMAIL, BIRTH_MONTH, BIRTH_DAY, BIRTH_YEAR, STATUS)VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.getConnection().prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setString(3, address);
        stmt.setString(4, email);
        stmt.setInt(5, birth_month + 1);
        stmt.setString(6, birth_day);
        stmt.setString(7, birth_year);
        stmt.setString(8, status);

        if (name == null || address == null || txtAge.getText() == null || txtEmail.getText() == null) {
            System.out.println("ERROR: Can't add a record due to missing fields");
        } else {
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null,
                        "Success",
                        "System Message",
                        JOptionPane.ERROR_MESSAGE);
                tableModel.fireTableStructureChanged();
                System.out.println("SUCCESS: A new record was inserted successfully!");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Error",
                        "System Message",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("ERROR: Failed to insert a new record");
                OracleConn.closeConnection();
            }
        }
    }

    private void UpdateRecord(int id, String name, int age, String address, String email, int birth_month, String birth_day, String birth_year, String status) throws SQLException {
        String sql = "UPDATE sys.records SET NAME = ?, AGE = ?, ADDRESS = ?, EMAIL = ?, BIRTH_MONTH = ?, BIRTH_DAY = ?, BIRTH_YEAR = ?, STATUS = ? WHERE ID = ?";
        PreparedStatement stmt = conn.getConnection().prepareStatement(sql);

        stmt.setString(1, name);
        stmt.setInt(2, age);
        stmt.setString(3, address);
        stmt.setString(4, email);
        stmt.setInt(5, birth_month + 1);
        stmt.setString(6, birth_day);
        stmt.setString(7, birth_year);
        stmt.setString(8, status);
        stmt.setInt(9, id);

        if (name == null || address == null || txtAge.getText() == null || txtEmail.getText() == null) {
            System.out.println("ERROR: Can't update a record due to missing fields");
        } else {
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null,
                        "Updated",
                        "System Message",
                        JOptionPane.ERROR_MESSAGE);
                tableModel.fireTableStructureChanged();
                System.out.println("SUCCESS: A record was updated successfully!");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Error",
                        "System Message",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("ERROR: Failed to update a record");
                OracleConn.closeConnection();
            }
        }
    }

    private void Clear() {
        TxtName.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtAge.setText("");
        ValStatus.setSelectedIndex(0);
        datePicker.getModel().setSelected(false);
        EditBtn.setText("Edit");
        btnClear.setText("Clear");
        //#Button states
        AddBtn.setEnabled(true);
        tbl.setEnabled(true);
        EditBtn.setVisible(true);
        BtnSave.setVisible(false);
    }

    private void EditMethod() {
        btnClear.setText("Cancel");
        //#Button states
        tbl.setEnabled(false);
        AddBtn.setEnabled(false);
        EditBtn.setVisible(false);
        BtnSave.setVisible(true);
    }

    private void SetRecord() {
        if (TxtName.getText().equals("") || txtAddress.getText().equals("") || txtEmail.getText().equals("") || txtAge.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Fill all fields",
                    "System Message",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            addRecord.setName(TxtName.getText());
            addRecord.setAddress(txtAddress.getText());
            addRecord.setEmail(txtEmail.getText());
            addRecord.setStatus((String) ValStatus.getSelectedItem());
            addRecord.setAge(Integer.parseInt(txtAge.getText()));
        }
    }
}
