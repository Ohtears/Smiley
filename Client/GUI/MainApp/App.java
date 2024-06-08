package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

import Client.Models.User;
import Client.GUI.MainApp.Dashboard.Dashboard;

public class App extends JFrame {

    private boolean displayDashboard = false; 
    private JPanel mainPanel;

    public void setDisplayDashboard(boolean displayDashboard, User targetuser) {
        this.displayDashboard = displayDashboard;
        refreshMainPanel(targetuser);  
    }

    public App() {
        setTitle("Smiley");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        HeaderPanel headerPanel = new HeaderPanel(this);
        add(headerPanel, BorderLayout.NORTH);

        refreshMainPanel(null);  
        int minPanelWidth = (int) ((0.5 + 1.5 + 5.0 + 3.0) / (0.5 + 1.5 + 5.0 + 3.0) * 1280); 
        setMinimumSize(new Dimension(minPanelWidth, 720));
    }

    private void refreshMainPanel(User targetuser) {
        if (mainPanel != null) {
            remove(mainPanel);  
        }

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        ForumsListPanel forumsListPanel = new ForumsListPanel();
        DMListPanel dmListPanel = new DMListPanel();
        MiscellaneousPanel miscellaneousPanel = new MiscellaneousPanel();
        Dashboard dashboard = new Dashboard(targetuser);
        PostsListPanel postsListPanel = new PostsListPanel();

        gbc.weightx = 0.5;
        gbc.gridx = 0;
        mainPanel.add(forumsListPanel, gbc);

        gbc.weightx = 1.5;
        gbc.gridx = 1;
        mainPanel.add(dmListPanel, gbc);

        if (displayDashboard) {
            gbc.weightx = 5.0;
            gbc.gridx = 2;
            mainPanel.add(dashboard, gbc);
        } else {
            gbc.weightx = 5.0;
            gbc.gridx = 2;
            mainPanel.add(postsListPanel, gbc);
        }

        gbc.weightx = 3.0;
        gbc.gridx = 3;
        mainPanel.add(miscellaneousPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);  
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
