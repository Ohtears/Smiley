package Client.GUI.MainApp.Dashboard;

import java.awt.*;
import javax.swing.*;

public class Dashboard extends JPanel {

    public Dashboard() {

        setBorder(BorderFactory.createTitledBorder("trhrhtrh"));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(2, 1)); // Split screen top and bottom

        // Profile Panel
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());

        // Labels and Button
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new GridLayout(4, 1));
        JLabel nameLabel = new JLabel("Name:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel bioLabel = new JLabel("Bio:");
        JLabel birthdayLabel = new JLabel("Birthday:");
        labelsPanel.add(nameLabel);
        labelsPanel.add(usernameLabel);
        labelsPanel.add(bioLabel);
        labelsPanel.add(birthdayLabel);

        JButton button = new JButton("Button");

        profilePanel.add(labelsPanel, BorderLayout.CENTER);
        profilePanel.add(button, BorderLayout.SOUTH);

        // Posts Dashboard Panel
        JPanel postsDashboardPanel = new JPanel();
        postsDashboardPanel.setLayout(new BorderLayout());

        // Header Panel
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
