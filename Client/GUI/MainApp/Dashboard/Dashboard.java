package Client.GUI.MainApp.Dashboard;

import Client.GUI.MainApp.Chat;
import Client.Models.CurrentUser;
import Client.Models.Message;
import Client.Models.TimeDate;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestType;
import Server.Database.MYSQLHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

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
                JsonConverter.usersToJson(userListB, RequestType.STARTCHAT);
                // MYSQLHandler.startChat(currentUser.getID(), user.getID());

                listener.onPanelSwitch(new Chat(user, MYSQLHandler.getChatBetweenUsers(currentUser.getID(), user.getID()))); 

            });   
    }

    }

}