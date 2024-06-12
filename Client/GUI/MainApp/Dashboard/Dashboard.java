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

        if (user != null){
            
            //1st panel

            JPanel profilePanel = new JPanel();
            profilePanel.setLayout(new BorderLayout());
    
            JPanel labelsPanel = new JPanel();
            labelsPanel.setLayout(new GridLayout(4, 1));
            JLabel nameLabel = new JLabel("Name: " + user.getName());
            JLabel usernameLabel = new JLabel("Username: " + user.Username);
            JLabel bioLabel = new JLabel("Bio: " + user.getBio());

            TimeDate birth = new TimeDate(user.getBirthday());

            JLabel birthdayLabel = new JLabel("Birthday: " + birth.toString());
            labelsPanel.add(nameLabel);
            labelsPanel.add(usernameLabel);
            labelsPanel.add(bioLabel);
            labelsPanel.add(birthdayLabel);
    
            JButton followbutton = new JButton("Follow");
            JButton messagebutton = new JButton("Message");

    
            profilePanel.add(labelsPanel, BorderLayout.CENTER);
            profilePanel.add(followbutton, BorderLayout.EAST);
            profilePanel.add(messagebutton, BorderLayout.SOUTH);

            //2nd panel

            JPanel postsDashboardPanel = new JPanel();
            postsDashboardPanel.setLayout(new BorderLayout());
    
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new GridLayout(1, 3));
    
            JPanel postsHeaderPanel = new JPanel();
            postsHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            postsHeaderPanel.add(new JLabel("Posts"));
    
            JPanel likesHeaderPanel = new JPanel();
            likesHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            likesHeaderPanel.add(new JLabel("Likes"));
    
            JPanel repliesHeaderPanel = new JPanel();
            repliesHeaderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            repliesHeaderPanel.add(new JLabel("Replies"));
    
            headerPanel.add(postsHeaderPanel);
            headerPanel.add(likesHeaderPanel);
            headerPanel.add(repliesHeaderPanel);
    
            postsDashboardPanel.add(headerPanel, BorderLayout.NORTH);
    
            JPanel mainPanel = new JPanel();
    
            postsDashboardPanel.add(mainPanel, BorderLayout.CENTER);
    
            add(profilePanel);
            add(postsDashboardPanel);


            followbutton.addActionListener(e -> {

                User currentUser = CurrentUser.getInstance().getUser();
                List<User> userListA = new ArrayList<>();
                userListA.add(currentUser);
                userListA.add(user);

                JsonConverter.usersToJson(userListA, RequestType.ADDFOLLOWER);

                // MYSQLHandler.addFollower(user.getID(), currentUser.getID());

            });

            messagebutton.addActionListener(e -> {

                User currentUser = CurrentUser.getInstance().getUser();
                List<User> userListB = new ArrayList<>();
                userListB.add(currentUser);
                userListB.add(user);
                JSONObject jsonRequest =  JsonConverter.usersToJson(userListB, RequestType.STARTCHAT);
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

                JSONObject jsonRequest1 =  JsonConverter.usersToJson(userListB, RequestType.GETCHATBETWEENUSERS);
                requestHandler.sendRequestAsync(jsonRequest1.toString(), new Callback() {
                    @Override
                    public void onSuccess(String response1) {
                        JSONObject responseServer1 = new JSONObject(response1);
                        List <Message> messages = JsonConverter.jsonToMessages(responseServer1);
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