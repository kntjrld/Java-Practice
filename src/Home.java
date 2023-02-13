import javax.swing.*;

public class Home extends JFrame{
    private JPanel MainJPanel;

    public static void main(String[] args) {
        Home home = new Home();
    }
    public Home() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainJPanel);
    }
}
