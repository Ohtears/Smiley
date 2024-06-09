package Client.GUI.MainApp;

import javax.swing.*;

import Client.GUI.MainApp.Dashboard.Chat;
import Client.GUI.MainApp.Dashboard.PanelSwitchListener;

import Client.Models.User;

import java.awt.*;


public class ChatPanel extends JPanel {

    private PanelSwitchListener listener; 
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

            listener.onPanelSwitch(new Chat(user)); 


        });


        add(chatButton, BorderLayout.EAST);
    }
}
