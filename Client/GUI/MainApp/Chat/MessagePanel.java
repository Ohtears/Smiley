package Client.GUI.MainApp.Chat;

import Client.Models.Message;


import java.awt.*;
import javax.swing.*;

public class MessagePanel extends JPanel {
    public MessagePanel(Message message) {
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());
        
        Dimension maxsize = new Dimension(900, 100);
        setMaximumSize(maxsize);

        JLabel userIdLabel = new JLabel("User ID: " + message.getSenderId());
        JLabel messageLabel = new JLabel("Message: " + message.getContent());

        add(userIdLabel, BorderLayout.NORTH);
        add(messageLabel, BorderLayout.CENTER);
    }
}