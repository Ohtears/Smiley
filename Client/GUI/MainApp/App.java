package Client.GUI.MainApp;

import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.Models.CurrentUser;
import Client.Network.KeepAlive;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App extends JFrame implements PanelSwitchListener {

    private JPanel mainPanel;

    public void setDisplayPanel(JPanel desiredPanel) {
        refreshMainPanel(desiredPanel);
    }

    public App() {

        KeepAlive HeartBeat = new KeepAlive(CurrentUser.getInstance().getUser());
        HeartBeat.startSendingRequests();

        setTitle("Smiley");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                disconnectAndClose();
            }
        });
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        HeaderPanel headerPanel = new HeaderPanel(this, this);
        add(headerPanel, BorderLayout.NORTH);

        refreshMainPanel(new PostsListPanel());  
        int minPanelWidth = (int) ((0.5 + 1.5 + 5.0 + 3.0) / (0.5 + 1.5 + 5.0 + 3.0) * 1280); 
        setMinimumSize(new Dimension(minPanelWidth, 720));
    }
    private void disconnectAndClose() {
        int confirm = JOptionPane.showOptionDialog(
            this,
            "Are you sure you want to close the application?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, null, null);
        
        if (confirm == JOptionPane.YES_OPTION) {
            
            dispose();
        }
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
        DMListPanel dmListPanel = new DMListPanel(this);
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

    @Override
    public void onPanelSwitch(JPanel newPanel) {
        refreshMainPanel(newPanel);
    }
}
