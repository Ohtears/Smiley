package Client.GUI.MainApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.KeyAdapter;

import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.GUI.MainApp.Style.CustomMessagePanel;
import Client.GUI.MainApp.Style.CustomScrollPane;
import Client.Models.CurrentUser;
import Client.Models.Message;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Network.RequestType;
import org.json.JSONObject;

public class Chat extends JPanel implements PanelSwitchListener {

    private ScheduledExecutorService scheduler;
    private MainPanel mainPanel;
    private User targetuser;
    private Message lastmessage;
    CustomScrollPane scrollPane;

    public Chat(User user, List<Message> messages) {
        this.targetuser = user;
        setBorder(BorderFactory.createTitledBorder("Chat"));
        setBackground(new Color(54, 57, 63));
        setLayout(new BorderLayout());

        HeaderPanel headerPanel = new HeaderPanel(user.getName());
        add(headerPanel, BorderLayout.NORTH);

        mainPanel = new MainPanel(messages);
        scrollPane = new CustomScrollPane(mainPanel); 
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setPreferredSize(new Dimension(200, 150));

        add(scrollPane, BorderLayout.CENTER); 

        FooterPanel footerPanel = new FooterPanel(user);
        add(footerPanel, BorderLayout.SOUTH);

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> refreshChatPanel(), 0L, 3L, TimeUnit.SECONDS);    
    }

    public void stopScheduler() {
        scheduler.shutdown(); 
    }

    private class HeaderPanel extends JPanel {
        public HeaderPanel(String userName) {
            setBackground(Color.GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel nameLabel = new JLabel(userName);
            nameLabel.setForeground(Color.WHITE);
            add(nameLabel);
        }
    }

    private class MainPanel extends JPanel {

        public MainPanel(List<Message> messages) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            for (Message message : messages) {
                CustomMessagePanel messagePanel = new CustomMessagePanel(message);
                add(messagePanel);
            }
        }
    }

    private class FooterPanel extends JPanel {
        public FooterPanel(User user) {
            setBackground(Color.GRAY);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
            JTextField inputField = new JTextField(30);
            JButton sendButton = new JButton("Send");
            inputField.setBackground(new Color(64, 68, 75));
            inputField.setForeground(Color.WHITE);
            inputField.setCaretColor(Color.WHITE);
            sendButton.setBackground(Color.LIGHT_GRAY);
            sendButton.setForeground(Color.WHITE);
    
            add(inputField);
            add(sendButton);
    
            sendButton.addActionListener(e -> sendMessage(inputField));
    
            inputField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        sendMessage(inputField);
                    }
                }
            });
        }
    
        private void sendMessage(JTextField inputField) {
            String messageContent = inputField.getText().trim();
            if (!messageContent.isEmpty()) {
    
                User currentUser = CurrentUser.getInstance().getUser();
    
                List<User> userList = new ArrayList<>();
                userList.add(currentUser);
                userList.add(targetuser);
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
                refreshChatPanel();

            }
        }
    }
    

    private void refreshChatPanel() {

        // boolean wasScrolledToBottom = isScrolledToBottom();

        User currentUser = CurrentUser.getInstance().getUser();

        List<User> userList = new ArrayList<>();
        userList.add(currentUser);
        userList.add(targetuser);
        JSONObject jsonRequest0 = JsonConverter.usersToJson(userList, RequestType.GETCHATBETWEENUSERS);            
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.sendRequestAsync(jsonRequest0.toString(), new Callback() {
            @Override
            public void onSuccess(String response) {

                JSONObject responseServer = new JSONObject(response);
                List<Message> messages = JsonConverter.jsonToMessages(responseServer);

                Message lastmsg = messages.get(messages.size() - 1);

                if (lastmessage != null) {
                    if (lastmsg.getMessageId() == lastmessage.getMessageId()) {
                        return;
                    }
                }

                lastmessage = lastmsg;

                CustomMessagePanel messagePanel = new CustomMessagePanel(lastmsg);

                mainPanel.add(messagePanel);
                mainPanel.revalidate();
                mainPanel.repaint();
                
                
            }

            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        scrollToBottom();

    }

    // private boolean isScrolledToBottom() {
    //     JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
    //     int bottomPos = verticalScrollBar.getMaximum() - verticalScrollBar.getVisibleAmount();
    //     return verticalScrollBar.getValue() == bottomPos;
    // }

    private void scrollToBottom() {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    @Override
    public void onPanelSwitch(JPanel newPanel) {

        if (!(newPanel instanceof Chat)) {
            stopScheduler();
        }
    }
}
