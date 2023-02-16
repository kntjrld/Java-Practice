import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Records extends JFrame{
    private JPanel RJPanel;
    private JButton BtnDashboard;
    private JButton recordsButton;
    private JButton reportsButton;
    private JButton settingsButton;
    private JButton BtnLogout;
public Records() {
    super();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(RJPanel);

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
}
}
