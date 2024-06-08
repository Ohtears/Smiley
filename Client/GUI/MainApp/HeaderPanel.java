package Client.GUI.MainApp;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import Client.Models.User;
import Server.Database.MYSQLHandler;
import Server.Database.RegexHandler;

public class HeaderPanel extends JPanel {
    private JTextField searchBar;
    private JPopupMenu searchResultsPopup;
    private Timer timer = new Timer();
    private App appInstance;

    public HeaderPanel(App appInstance) {
        this.appInstance = appInstance;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());

        Dimension preferredSize = new Dimension(1280, 50);
        setPreferredSize(preferredSize);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchBar = new JTextField();
        searchBar.setPreferredSize(new Dimension(530, 30));
        searchPanel.add(searchBar);
        add(searchPanel, BorderLayout.SOUTH);

        searchResultsPopup = new JPopupMenu();
        searchResultsPopup.setPopupSize(new Dimension(530, 200));

        searchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timer.cancel();
                timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String query = searchBar.getText();
                        if (!query.isEmpty()) {
                            showSearchResults(query);
                        } else {
                            searchResultsPopup.setVisible(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    private void showSearchResults(String query) {
        searchResultsPopup.removeAll();

        List<User> users = new ArrayList<>(MYSQLHandler.getAllUsers());
        List<String> usernames = new ArrayList<>();

        for (User user : users) {
            usernames.add(user.Username);
        }

        List<String> matchedUsernames = new ArrayList<>(RegexHandler.findTopMatches(usernames, query, 5));

        for (String result : matchedUsernames) {
            JMenuItem resultItem = new JMenuItem(result);
            resultItem.addActionListener(e -> {
                searchResultsPopup.setVisible(false);
                appInstance.setDisplayDashboard(true);  // Set displayDashboard to true
            });
            searchResultsPopup.add(resultItem);
        }

        if (!searchResultsPopup.isVisible()) {
            searchResultsPopup.show(searchBar, 0, searchBar.getHeight());
        }
    }
}
