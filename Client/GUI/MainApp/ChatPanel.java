package Client.GUI.MainApp;

import javax.swing.*;

import org.json.JSONObject;

import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.Models.CurrentUser;
import Client.Models.Message;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Network.RequestType;

import java.awt.*;
import java.io.IOException;
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
        setBackground(new Color(45, 45, 50));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Whitney", Font.BOLD, 16));
        add(nameLabel, BorderLayout.CENTER);

        JButton chatButton = new JButton("Chat");
        chatButton.setBackground(new Color(78, 93, 148));
        chatButton.setForeground(Color.WHITE);
        chatButton.setFont(new Font("Whitney", Font.BOLD, 12));
        chatButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        chatButton.setFocusPainted(false);

        chatButton.addActionListener(e -> {
            User currentUser = CurrentUser.getInstance().getUser();

            List<User> userListC = new ArrayList<>();
            userListC.add(currentUser);
            userListC.add(user);
            RequestHandler requestHandler = new RequestHandler();

            JSONObject jsonRequest2 = JsonConverter.usersToJson(userListC, RequestType.GETCHATBETWEENUSERS);

            requestHandler.sendRequestAsync(jsonRequest2.toString(), new Callback() {
                @Override
                public void onSuccess(String response2) {
                    JSONObject responseServer2 = new JSONObject(response2);
                    List<Message> messages = JsonConverter.jsonToMessages(responseServer2);
                    listener.onPanelSwitch(new Chat(user, messages)); 
                }
        
                @Override
                public void onFailure(IOException e) {
                    JOptionPane.showMessageDialog(null, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });

        add(chatButton, BorderLayout.EAST);
    }
}
