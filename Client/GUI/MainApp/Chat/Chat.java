package Client.GUI.MainApp.Chat;

import javax.swing.*;
import java.util.List;
import java.awt.*;

import Client.Models.CurrentUser;
import Client.Models.Message;
import Client.Models.User;
import Server.Database.MYSQLHandler;

public class Chat extends JPanel {
    
    private MainPanel mainPanel;

    public Chat(User user, List<Message> messages) {
        setBorder(BorderFactory.createTitledBorder("Chat"));
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        HeaderPanel headerPanel = new HeaderPanel(user.getName());
        add(headerPanel, BorderLayout.NORTH);

        mainPanel = new MainPanel(messages);
        JScrollPane scrollPane = new JScrollPane(mainPanel); 
        scrollPane.setPreferredSize(new Dimension(200, 150));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        add(scrollPane, BorderLayout.CENTER); 
    
        FooterPanel footerPanel = new FooterPanel(user);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private class FooterPanel extends JPanel {
        public FooterPanel(User user) {
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JTextField inputField = new JTextField(30);
            JButton sendButton = new JButton("Send");

            add(inputField);
            add(sendButton);

            sendButton.addActionListener(e -> {
                String messageContent = inputField.getText().trim();
                if (!messageContent.isEmpty()) {

                    User currentUser = CurrentUser.getInstance().getUser();


                    MYSQLHandler.sendmessageschat(currentUser.getID(), user.getID(), messageContent);
                    
                    inputField.setText("");
                    
                    refreshChatPanel(user, messageContent);
                }
            });
        }
    }
    
    private void refreshChatPanel(User user, String messageContent) {

        User currentUser = CurrentUser.getInstance().getUser();

        Message sentMessage = new Message(0, currentUser.getID(), user.getID(), messageContent, null);
        
        MessagePanel sentMessagePanel = new MessagePanel(sentMessage);
        
        mainPanel.add(sentMessagePanel);
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
