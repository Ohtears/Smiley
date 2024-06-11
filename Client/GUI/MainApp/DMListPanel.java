package Client.GUI.MainApp;

import javax.swing.*;

import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.Models.CurrentUser;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestType;

public class DMListPanel extends JPanel {

    public DMListPanel(PanelSwitchListener listener) {
        setBorder(BorderFactory.createTitledBorder("DM List"));
        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 

        User currentUser = CurrentUser.getInstance().getUser();

        List<User> userList = new ArrayList<>();
        userList.add(currentUser);
        JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.GETCHATLIST);
        JSONObject responseServer = RequestHandler.call(jsonRequest);

        List<User> users = JsonConverter.jsonToUsers(responseServer);

        // List<User> users = MYSQLHandler.getChatList(currentUser.getID());
        for (User user : users) {
            // ImageIcon userIcon = new ImageIcon("Untitled.jpeg");  
            ChatPanel chatPanel = new ChatPanel(user, listener);
            Dimension maxPanelSize = new Dimension(300, 50); 
            chatPanel.setMaximumSize(maxPanelSize);
            add(chatPanel);
        }
    }
}
