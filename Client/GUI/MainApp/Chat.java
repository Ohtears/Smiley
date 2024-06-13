package Client.GUI.MainApp;

import javax.swing.*;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.io.IOException;

import Client.Models.CurrentUser;
import Client.Models.Message;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Network.RequestType;

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

    private class HeaderPanel extends JPanel {
        public HeaderPanel(String userName) {
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel nameLabel = new JLabel(userName);
            add(nameLabel);
        }
    }

    private class MainPanel extends JPanel {

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
    
    private class MessagePanel extends JPanel {
        public MessagePanel(Message message) {
            setBackground(Color.LIGHT_GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            setLayout(new BorderLayout());
            
            Dimension maxsize = new Dimension(900, 100);
            setMaximumSize(maxsize);

            JLabel userIdLabel = new JLabel("User ID: " + message.getSender().getName());
            JLabel messageLabel = new JLabel("Message: " + message.getContent());
    
            add(userIdLabel, BorderLayout.NORTH);
            add(messageLabel, BorderLayout.CENTER);
        }
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

                    List<User> userList = new ArrayList<>();
                    userList.add(currentUser);
                    userList.add(user);
                    JSONObject jsonRequest0 = JsonConverter.usersToJson(userList, RequestType.STARTCHAT);            
                    JSONObject jsonRequest = JsonConverter.usersToJson(userList, messageContent, RequestType.SENDMESSAGESCHAT);
                    JSONObject jsonRequest1 = JsonConverter.usersToJson(userList, RequestType.GETUSERSTATUS);
                    RequestHandler requestHandler = new RequestHandler();
                    requestHandler.sendRequestAsync(jsonRequest0.toString(), new Callback() {
                        @Override
                        public void onSuccess(String response) {
                            
                        }
                
                        @Override
                        public void onFailure(IOException e) {
                            JOptionPane.showMessageDialog(null, "failed", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });   
                    requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
                        @Override
                        public void onSuccess(String response) {
                            
                        }
                
                        @Override
                        public void onFailure(IOException e) {
                            JOptionPane.showMessageDialog(null, "failed", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });   

                    requestHandler.sendRequestAsync(jsonRequest1.toString(), new Callback() {
                        @Override
                        public void onSuccess(String response) {
                            
                        }
                
                        @Override
                        public void onFailure(IOException e) {
                            JOptionPane.showMessageDialog(null, "failed", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });   

                                
                    inputField.setText("");
                    
                    refreshChatPanel(user, messageContent);
                }
            });
        }
    }
    
    private void refreshChatPanel(User user, String messageContent) {

        User currentUser = CurrentUser.getInstance().getUser();

        List<User> userList = new ArrayList<>();
        userList.add(currentUser);
        userList.add(user);
        JSONObject jsonRequest0 = JsonConverter.usersToJson(userList, RequestType.GETCHATBETWEENUSERS);            
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.sendRequestAsync(jsonRequest0.toString(), new Callback() {
            @Override
            public void onSuccess(String response) {

                JSONObject responseServer = new JSONObject(response);
                List<Message> messages = JsonConverter.jsonToMessages(responseServer);
                
                Message lastmsg = messages.get(messages.size() - 1);
                
                MessagePanel messagePanel = new MessagePanel(lastmsg);
                mainPanel.add(messagePanel);
                
                mainPanel.revalidate();
                mainPanel.repaint();
                
            }
    
            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });   

        // Message sentMessage = new Message(0, currentUser, user, messageContent, null);
        // TO DO FOR FUTUTE. MAKE IT SO THAT the send message will be opacity low then it will get high 
    }
}
