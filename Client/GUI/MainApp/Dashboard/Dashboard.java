package Client.GUI.MainApp.Dashboard;

import Client.Models.TimeDate;
import Client.Models.User;

import java.awt.*;
import javax.swing.*;

public class Dashboard extends JPanel {

    public Dashboard(User user) {
        setBorder(BorderFactory.createTitledBorder("Profile"));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(2, 1)); 

        if (user != null){
            
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
    
            JButton button = new JButton("Update Profile");
    
            profilePanel.add(labelsPanel, BorderLayout.CENTER);
            profilePanel.add(button, BorderLayout.SOUTH);
    
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


        }
    }

}