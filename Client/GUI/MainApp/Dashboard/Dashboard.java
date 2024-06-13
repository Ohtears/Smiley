package Client.GUI.MainApp.Dashboard;

import Client.GUI.MainApp.Chat;
import Client.Models.CurrentUser;
import Client.Models.Message;
import Client.Models.TimeDate;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Network.RequestType;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.json.JSONObject;

public class Dashboard extends JPanel {

    public Dashboard(User user, PanelSwitchListener listener) {

        setBorder(BorderFactory.createTitledBorder("Profile"));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(2, 1));

        if (user != null) {

            // Profile panel
            JPanel profilePanel = new JPanel();
            profilePanel.setLayout(new BorderLayout());
            profilePanel.setBackground(new Color(54, 57, 63)); 
            JPanel labelsPanel = new JPanel();
            labelsPanel.setLayout(new GridLayout(4, 1));
            labelsPanel.setBackground(new Color(54, 57, 63)); 
            JLabel nameLabel = new JLabel("Name: " + user.getName());
            nameLabel.setForeground(Color.WHITE);
            labelsPanel.add(nameLabel);

            JLabel usernameLabel = new JLabel("Username: @" + user.Username);
            usernameLabel.setForeground(Color.WHITE);
            labelsPanel.add(usernameLabel);

            JLabel bioLabel = new JLabel("Bio: " + user.getBio());
            bioLabel.setForeground(Color.WHITE);
            labelsPanel.add(bioLabel);

            TimeDate birth = new TimeDate(user.getBirthday());
            JLabel birthdayLabel = new JLabel("Birthday: " + birth.toString());
            birthdayLabel.setForeground(Color.WHITE);
            labelsPanel.add(birthdayLabel);

            JButton followButton = new JButton("Follow");
            followButton.setBackground(new Color(29, 161, 242)); 
            followButton.setForeground(Color.WHITE);
            profilePanel.add(labelsPanel, BorderLayout.CENTER);
            profilePanel.add(followButton, BorderLayout.EAST);

            JButton messageButton = new JButton("Message");
            messageButton.setForeground(Color.WHITE);
            messageButton.setBackground(new Color(45, 47, 51)); 
            profilePanel.add(messageButton, BorderLayout.SOUTH);

            // Posts, Likes, Replies panel
            JPanel postsDashboardPanel = new JPanel();
            postsDashboardPanel.setLayout(new BorderLayout());
            postsDashboardPanel.setBackground(new Color(54, 57, 63)); 

            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new GridLayout(1, 3));
            headerPanel.setBackground(new Color(54, 57, 63)); 

            JPanel postsHeaderPanel = new JPanel();
            postsHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            postsHeaderPanel.setBackground(new Color(54, 57, 63)); 
            postsHeaderPanel.add(new JLabel("Posts"));
            postsHeaderPanel.setForeground(Color.WHITE);
            headerPanel.add(postsHeaderPanel);

            JPanel likesHeaderPanel = new JPanel();
            likesHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            likesHeaderPanel.setBackground(new Color(54, 57, 63));
            likesHeaderPanel.setForeground(Color.WHITE);
            likesHeaderPanel.add(new JLabel("Likes"));
            headerPanel.add(likesHeaderPanel);

            JPanel repliesHeaderPanel = new JPanel();
            repliesHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            repliesHeaderPanel.setBackground(new Color(54, 57, 63)); 
            repliesHeaderPanel.add(new JLabel("Replies"));
            repliesHeaderPanel.setForeground(Color.WHITE);

            headerPanel.add(repliesHeaderPanel);

            postsDashboardPanel.add(headerPanel, BorderLayout.NORTH);

            JPanel mainPanel = new JPanel();
            mainPanel.setBackground(new Color(54, 57, 63)); 
            postsDashboardPanel.add(mainPanel, BorderLayout.CENTER);

            add(profilePanel);
            add(postsDashboardPanel);

            followButton.addActionListener(e -> {
                User currentUser = CurrentUser.getInstance().getUser();
                List<User> userListA = new ArrayList<>();
                userListA.add(currentUser);
                userListA.add(user);
                JsonConverter.usersToJson(userListA, RequestType.ADDFOLLOWER);
            });

            messageButton.addActionListener(e -> {
                User currentUser = CurrentUser.getInstance().getUser();
                List<User> userListB = new ArrayList<>();
                userListB.add(currentUser);
                userListB.add(user);
                JSONObject jsonRequest = JsonConverter.usersToJson(userListB, RequestType.STARTCHAT);
                RequestHandler requestHandler = new RequestHandler();
                requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
                    @Override
                    public void onSuccess(String response) {
                    }

                    @Override
                    public void onFailure(IOException e) {
                        JOptionPane.showMessageDialog(null, "failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                JSONObject jsonRequest1 = JsonConverter.usersToJson(userListB, RequestType.GETCHATBETWEENUSERS);
                requestHandler.sendRequestAsync(jsonRequest1.toString(), new Callback() {
                    @Override
                    public void onSuccess(String response1) {
                        JSONObject responseServer1 = new JSONObject(response1);
                        List<Message> messages = JsonConverter.jsonToMessages(responseServer1);
                        listener.onPanelSwitch(new Chat(user, messages));
                    }

                    @Override
                    public void onFailure(IOException e) {
                        JOptionPane.showMessageDialog(null, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            });
        }
    }
}
