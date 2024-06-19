package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

import Client.GUI.MainApp.Dashboard.PanelSwitchListener;

public class ForumsListPanel extends JPanel {

    public ForumsListPanel(PanelSwitchListener listner) {
        setBorder(BorderFactory.createTitledBorder("Forums List"));
        setBackground(new Color(32, 34, 37));
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        topPanel.setBackground(new Color(32, 34, 37));

        HomeButton homeButton = new HomeButton(listner);
        topPanel.add(homeButton);

        JPanel bottomPanel = new JPanel(new GridLayout(0, 1));
        bottomPanel.setBackground(new Color(32, 34, 37));

        for (int i = 0; i < 5; i++) {
            JLabel forumButton = new JLabel("Forum " + (i + 1)); 
            forumButton.setHorizontalAlignment(SwingConstants.CENTER);
            forumButton.setForeground(Color.WHITE); 
            forumButton.setPreferredSize(new Dimension(75, 75));
            bottomPanel.add(forumButton);
        }

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

}
