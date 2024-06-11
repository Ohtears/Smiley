package Client.GUI.MainApp;

import javax.swing.*;

import org.json.JSONObject;

import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.Models.CurrentUser;
import Client.Models.Message;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChatPanel extends JPanel {

    @SuppressWarnings("unused")
    private PanelSwitchListener listener; 
    @SuppressWarnings("unused")
    private User user;

    public ChatPanel(User user, PanelSwitchListener listener) {
        this.listener = listener; 
        this.user = user;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // JLabel iconLabel = new JLabel(userIcon);
        JLabel nameLabel = new JLabel(user.getName());

        // add(iconLabel, BorderLayout.WEST);
        add(nameLabel, BorderLayout.CENTER);

        JButton chatButton = new JButton("Chat");
        chatButton.addActionListener(e -> {

            User currentUser = CurrentUser.getInstance().getUser();

            List<User> userListC = new ArrayList<>();
            userListC.add(currentUser);
            userListC.add(user);
            JSONObject jsonRequest = JsonConverter.usersToJson(userListC, RequestType.STARTCHAT);            
            RequestHandler.call(jsonRequest);

            JSONObject jsonRequest2 = JsonConverter.usersToJson(userListC, RequestType.GETCHATBETWEENUSERS);
            JSONObject responseServer2 = RequestHandler.call(jsonRequest2);
    
            List<Message> messages = JsonConverter.jsonToMessages(responseServer2);
            // MYSQLHandler.getChatBetweenUsers(currentUser.getID(), user.getID())
            // MYSQLHandler.startChat(currentUser.getID(), user.getID());
            listener.onPanelSwitch(new Chat(user, messages)); 

        });


        add(chatButton, BorderLayout.EAST);
    }
}
