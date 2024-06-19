package Client.GUI.MainApp;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import org.json.JSONObject;

import Client.GUI.MainApp.Dashboard.Dashboard;
import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Tools.RegexHandler;
import Client.Network.RequestType;

public class HeaderPanel extends JPanel {
    
    private JTextField searchBar;
    private JPopupMenu searchResultsPopup;
    private Timer timer = new Timer();
    private App appInstance;
    private PanelSwitchListener panelSwitchListener;

    public HeaderPanel(App appInstance, PanelSwitchListener panelSwitchListener) {

        this.appInstance = appInstance;
        this.panelSwitchListener = panelSwitchListener;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(new Color(44, 47, 51));
        setLayout(new BorderLayout());

        Dimension preferredSize = new Dimension(1280, 50);
        setPreferredSize(preferredSize);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchBar = new JTextField();
        searchBar.setBackground(new Color(34, 37, 41)); 
        searchBar.setPreferredSize(new Dimension(530, 30));
        searchBar.setForeground(Color.WHITE); 
        searchPanel.setBackground(new Color(34, 37, 41)); 
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

        List<User> userList = new ArrayList<>();
        JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.GETALLUSERS);
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
            @Override
            public void onSuccess(String response) {
                JSONObject responseServer = new JSONObject(response);
                List<User> users = JsonConverter.jsonToUsers(responseServer);
                List<String> usernames = new ArrayList<>();

                for (User user : users) {
                    usernames.add(user.Username);
                }
                List<String> matchedUsernames = new ArrayList<>(RegexHandler.findTopMatches(usernames, query, 5));
                for (String result : matchedUsernames) {
                    JMenuItem resultItem = new JMenuItem(result);
                    resultItem.addActionListener(e -> {
                    searchResultsPopup.setVisible(false);
                    
                    User targetuser = null;
                    for (User user: users){
                            if (user.Username.equals(resultItem.getText())){
                                targetuser = user;
                                break;
                            }
        
                        }
                    appInstance.setDisplayPanel(new Dashboard(targetuser, panelSwitchListener));  
        
        
                    });
                    searchResultsPopup.add(resultItem);
                    if (!searchResultsPopup.isVisible()) {
                        searchResultsPopup.show(searchBar, 0, searchBar.getHeight());
                    }
                }
        
            }
    
            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "Request failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


    }
}
