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
    @SuppressWarnings("unused")
    private JPanel currentmainPanel; 
    private KeepAlive HeartBeat;
    private DMListPanel dmListPanel;

    public void setDisplayPanel(JPanel desiredPanel) {
        refreshMainPanel(desiredPanel);
    }

    public App() {
        dmListPanel = new DMListPanel(this); 
        HeartBeat = new KeepAlive(CurrentUser.getInstance().getUser(), dmListPanel);
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

        PostsListPanel initialPanel = new PostsListPanel(this);
        currentmainPanel = initialPanel;
        refreshMainPanel(initialPanel);

        int minPanelWidth = 1280; 
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
            if (HeartBeat != null) {
                HeartBeat.stopSendingRequests(); 
            }
            dispose();
            System.exit(0);
        }
    }

    private void refreshMainPanel(JPanel desiredPanel) {
        if (mainPanel != null) {
            remove(mainPanel);  
        }

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        ForumsListPanel forumsListPanel = new ForumsListPanel(this);
        forumsListPanel.setPreferredSize(new Dimension(80, 720));
        forumsListPanel.setMinimumSize(new Dimension(80, 720));

        dmListPanel.setPreferredSize(new Dimension(224, 720));
        dmListPanel.setMinimumSize(new Dimension(224, 720));

        desiredPanel.setPreferredSize(new Dimension(616, 720));
        desiredPanel.setMinimumSize(new Dimension(616, 720));

        MiscellaneousPanel miscellaneousPanel = new MiscellaneousPanel();
        miscellaneousPanel.setPreferredSize(new Dimension(360, 720));
        miscellaneousPanel.setMinimumSize(new Dimension(360, 720));

        gbc.gridx = 0;
        mainPanel.add(forumsListPanel, gbc);

        gbc.gridx = 1;
        mainPanel.add(dmListPanel, gbc);

        gbc.gridx = 2;
        gbc.weightx = 1.0;
        mainPanel.add(desiredPanel, gbc);

        gbc.gridx = 3;
        mainPanel.add(miscellaneousPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);  
        revalidate();
        repaint();

        currentmainPanel = desiredPanel; 
    }

    @Override
    public void onPanelSwitch(JPanel newPanel) {
        refreshMainPanel(newPanel);
    }
}
