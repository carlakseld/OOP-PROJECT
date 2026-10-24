import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel containerPanel;
    public MainFrame() {
        setTitle("Barangay Document Request System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        //Instantiate Panels
        LoginPanel loginPanel = new LoginPanel(this::showLogin);
        DashboardPanel dashboardPanel = new DashboardPanel(this::showDashboard);


        containerPanel.add(loginPanel, "LOGIN");
        containerPanel.add(dashboardPanel, "DASHBOARD");

        add(containerPanel);
        cardLayout.show(containerPanel, "LOGIN");

    }

    private void showDashboard() {
        cardLayout.show(containerPanel, "DASHBOARD");
    }

    private void showLogin() {
        cardLayout.show(containerPanel, "DASHBOARD");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new MainFrame().setVisible(true));


    }
}

