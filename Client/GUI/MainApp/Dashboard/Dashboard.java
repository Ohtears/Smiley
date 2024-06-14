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

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Dashboard extends JPanel {

    public Dashboard(User user, PanelSwitchListener listener) {
        setBorder(BorderFactory.createTitledBorder("Profile"));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(2, 1));

        if (user != null) {
            JPanel profilePanel = new JPanel();
            profilePanel.setLayout(new BorderLayout());
            profilePanel.setBackground(new Color(54, 57, 63));

            JPanel followersFollowingPanel = new JPanel(new GridLayout(1, 2));
            followersFollowingPanel.setBackground(new Color(54, 57, 63));

            JButton followersButton = new JButton("Following");
            followersButton.setBackground(new Color(29, 161, 242));
            followersButton.setForeground(Color.WHITE);
            followersButton.setPreferredSize(new Dimension(80, 25));
            followersButton.addActionListener(e -> {
                fetchAndDisplayFollowers(user);
            });
            followersFollowingPanel.add(followersButton);

            JButton followingButton = new JButton("Followers");
            followingButton.setBackground(new Color(29, 161, 242));
            followingButton.setForeground(Color.WHITE);
            followingButton.setPreferredSize(new Dimension(80, 25));
            followingButton.addActionListener(e -> {
                fetchAndDisplayFollowing(user);
            });
            followersFollowingPanel.add(followingButton);

            profilePanel.add(followersFollowingPanel, BorderLayout.NORTH);

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

            // Posts, Likes, Replies 
            JPanel postsDashboardPanel = new JPanel();
            postsDashboardPanel.setLayout(new BorderLayout());
            postsDashboardPanel.setBackground(new Color(54, 57, 63));

            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new GridLayout(1, 3));
            headerPanel.setBackground(new Color(54, 57, 63));

            JPanel postsHeaderPanel = new JPanel();
            postsHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            postsHeaderPanel.setBackground(new Color(54, 57, 63));
            postsHeaderPanel.setForeground(Color.WHITE);
            postsHeaderPanel.add(new JLabel("Posts"));
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
                JSONObject jsonRequest = JsonConverter.usersToJson(userListA, RequestType.ADDFOLLOWER);
                RequestHandler requestHandler = new RequestHandler();
                requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
                    @Override
                    public void onSuccess(String response) {
                    }

                    @Override
                    public void onFailure(IOException e) {
                        JOptionPane.showMessageDialog(null, "Failed to follow user", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
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
                        JOptionPane.showMessageDialog(null, "Failed to start chat", "Error", JOptionPane.ERROR_MESSAGE);
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
                        JOptionPane.showMessageDialog(null, "Failed to get chat history", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            });
        }
    }

    private void fetchAndDisplayFollowers(User user) {
        CurrentUser currentUser = CurrentUser.getInstance();
        List<User> userList = new ArrayList<>();
        userList.add(currentUser.getUser());

        RequestHandler handler = new RequestHandler();
        JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.GETALLFOLLOWERS);
        handler.sendRequestAsync(jsonRequest.toString(), new Callback() {
            @Override
            public void onSuccess(String response) {
                List<User> followers = JsonConverter.jsonToUsers(new JSONObject(response));
                displayUsersList("Followers", followers);
            }

            @Override
            public void onFailure(IOException e) {
            }
        });
    }

    private void fetchAndDisplayFollowing(User user) {
        CurrentUser currentUser = CurrentUser.getInstance();
        List<User> userList = new ArrayList<>();
        userList.add(currentUser.getUser());

        RequestHandler handler = new RequestHandler();
        JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.GETALLFOLLOWING);
        handler.sendRequestAsync(jsonRequest.toString(), new Callback() {
            @Override
            public void onSuccess(String response) {
                List<User> following = JsonConverter.jsonToUsers(new JSONObject(response));
                displayUsersList("Following", following);
            }

            @Override
            public void onFailure(IOException e) {
            }
        });
    }

    private void displayUsersList(String title, List<User> users) {
        JDialog usersDialog = new JDialog();
        usersDialog.setTitle(title);
        usersDialog.setLayout(new BorderLayout());

        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new BoxLayout(usersPanel, BoxLayout.Y_AXIS));
        usersPanel.setBackground(Color.WHITE);

        for (User user : users) {
            JLabel userInfoLabel = new JLabel("@" + user.Username + " - " + user.getName());
            userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            userInfoLabel.setForeground(Color.BLACK);
            userInfoLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            userInfoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            userInfoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    JOptionPane.showMessageDialog(null, "Clicked on @" + user.Username);
                }
            });
            usersPanel.add(userInfoLabel);
        }

        JScrollPane scrollPane = new JScrollPane(usersPanel);
        usersDialog.add(scrollPane, BorderLayout.CENTER);

        usersDialog.setSize(400, 300);
        usersDialog.setLocationRelativeTo(null);
        usersDialog.setVisible(true);
    }
}
