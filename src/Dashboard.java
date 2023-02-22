import JDBConnnection.OracleConn;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Dashboard extends JFrame {
    private JButton BtnDashboard;
    private JButton recordsButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton BtnLogout;
    private JPanel JPChart;
    private JPanel MainJPanel;

    public Dashboard() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainJPanel);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        //#Add data to the dataset
        dataset.addValue(30, "Series 1", "Category 1");
        dataset.addValue(40, "Series 1", "Category 2");
        dataset.addValue(5, "Series 1", "Category 3");
        dataset.addValue(40, "Series 1", "Category 4");
        dataset.addValue(50, "Series 1", "Category 5");

        dataset.addValue(20, "Series 2", "Category 1");
        dataset.addValue(30, "Series 2", "Category 2");
        dataset.addValue(40, "Series 2", "Category 3");
        dataset.addValue(50, "Series 2", "Category 4");
        dataset.addValue(60, "Series 2", "Category 5");
        //#Create the JFreeChart object (replace with your own chart creation code)
        JFreeChart chart = ChartFactory.createBarChart(
                "My Chart",  // Chart title
                "Category",  // X-axis label
                "Value",     // Y-axis label
                dataset,     // Chart data
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        //#Customize the chart
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRenderer(new StackedBarRenderer());
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);
        ChartPanel chartPanel = new ChartPanel(chart);
        //#Add the ChartPanel to JPanel
        if (JPChart.getLayout() instanceof GridBagLayout) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 1.0;
            constraints.weighty = 1.0;
            JPChart.add(chartPanel, constraints);
        } else {
            JPChart.add(chartPanel);
        }

        BtnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main m = new Main();
                m.pack();
                m.setVisible(true);
                m.setResizable(false);
                m.setLocationRelativeTo(null);
                //#Disconnect mysql database
                //--MysqlConn mysqlConn = new MysqlConn();
                //--mysqlConn.closeConnection();
                //#Disconnect oracle database
                OracleConn.closeConnection();
                dispose();
            }
        });
        recordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Records records = null;
                try {
                    records = new Records();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                records.pack();
                records.setVisible(true);
                records.setResizable(false);
                records.setLocationRelativeTo(null);
                dispose();
            }
        });
    }
}

