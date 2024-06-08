package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

public class App extends JFrame {

    private JPanel mainPanel;

    public void setDisplayPanel(JPanel desiredPanel) {
        refreshMainPanel(desiredPanel);
    }

    public App() {
        setTitle("Smiley");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        HeaderPanel headerPanel = new HeaderPanel(this);
        add(headerPanel, BorderLayout.NORTH);

        refreshMainPanel(new PostsListPanel());  
        int minPanelWidth = (int) ((0.5 + 1.5 + 5.0 + 3.0) / (0.5 + 1.5 + 5.0 + 3.0) * 1280); 
        setMinimumSize(new Dimension(minPanelWidth, 720));
    }

    private void refreshMainPanel(JPanel DesiredPanel) {
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

        gbc.weightx = 0.5;
        gbc.gridx = 0;
        mainPanel.add(forumsListPanel, gbc);

        gbc.weightx = 1.5;
        gbc.gridx = 1;
        mainPanel.add(dmListPanel, gbc);

        gbc.weightx = 5.0;
        gbc.gridx = 2;
        mainPanel.add(DesiredPanel, gbc);

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
