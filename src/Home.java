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
        Home home = new Home();
    }
    public Home() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainJPanel);
        BtnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                main m =  new main();
                m.pack();
                m.setVisible(true);
                m.setResizable(false);
                m.setLocationRelativeTo(null);
            }
        });
    }
}
