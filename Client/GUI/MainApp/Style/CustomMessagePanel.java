package Client.GUI.MainApp.Style;

import javax.swing.*;
import java.awt.*;
import Client.Models.Message;

public class CustomMessagePanel extends JPanel {
    
    public CustomMessagePanel(Message message) {
        setBackground(new Color(54, 57, 63)); // Discord-like background color
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());

        Dimension size = new Dimension(900, 100);
        setMaximumSize(size);
        setPreferredSize(size);
        setMinimumSize(size);

        JLabel userIdLabel = new JLabel(message.getSender().getName() + "     " + message.getTimestamp().toString());
        JLabel messageLabel = new JLabel("<html>" + message.getContent().replaceAll("\n", "<br>") + "</html>");

        userIdLabel.setFont(new Font("Whitney", Font.BOLD, 14)); // Discord-like font
        userIdLabel.setForeground(new Color(114, 137, 218)); // Discord-like color for usernames
        messageLabel.setFont(new Font("Whitney", Font.PLAIN, 16)); // Discord-like font for message
        messageLabel.setForeground(Color.WHITE); // White color for message text

        add(userIdLabel, BorderLayout.NORTH);
        add(messageLabel, BorderLayout.CENTER);
    }
}
