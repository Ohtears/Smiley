package Client.GUI.MainApp.Dashboard;
import javax.swing.*;

import Client.Models.User;

import java.awt.*;

public class Chat extends JPanel {

    public Chat(User user) {
        setBorder(BorderFactory.createTitledBorder("Chat"));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        HeaderPanel headerPanel = new HeaderPanel(user.getName());
        add(headerPanel, BorderLayout.NORTH);

        MainPanel mainPanel = new MainPanel();
        add(mainPanel, BorderLayout.CENTER);

        FooterPanel footerPanel = new FooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }

    private class HeaderPanel extends JPanel {
        public HeaderPanel(String userName) {
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel nameLabel = new JLabel(userName);
            add(nameLabel);
        }
    }

    private class MainPanel extends JPanel {
        public MainPanel() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JTextArea chatArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(chatArea);
            scrollPane.setPreferredSize(new Dimension(200, 150)); 
            add(scrollPane);
        }
    }

    private class FooterPanel extends JPanel {
        public FooterPanel() {
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JTextField inputField = new JTextField(20);
            JButton sendButton = new JButton("Send");

            add(inputField);
            add(sendButton);
        }
    }
}
