package Client.GUI.MainApp.Chat;

import java.awt.*;
import javax.swing.*;
import java.util.List;

import Client.Models.Message;

public class MainPanel extends JPanel {

    public MainPanel(List<Message> messages) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        for (Message message : messages) {
            MessagePanel messagePanel = new MessagePanel(message);
            add(messagePanel);
        }

    }
}