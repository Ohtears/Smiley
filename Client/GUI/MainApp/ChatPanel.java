package Client.GUI.MainApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {

    public ChatPanel(String userName, ImageIcon userIcon) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel iconLabel = new JLabel(userIcon);
        JLabel nameLabel = new JLabel(userName);

        add(iconLabel, BorderLayout.WEST);
        add(nameLabel, BorderLayout.CENTER);

        JButton chatButton = new JButton("Chat");
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start chat with " + userName);
            }
        });

        add(chatButton, BorderLayout.EAST);
    }
}
