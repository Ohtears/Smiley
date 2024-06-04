package Client.GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends BasePanel {
    private final JFrame parentFrame;

    public Dashboard(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Use appropriate layout manager

        JLabel welcomeLabel = new JLabel("Welcome to the Dashboard!");
        welcomeLabel.setBounds(300, 50, 200, 30);
        add(welcomeLabel);

        JButton postButton = new JButton("Create Post");
        postButton.setBounds(300, 100, 200, 30);
        add(postButton);

        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle post creation logic
                parentFrame.getContentPane().removeAll();
                Post postPanel = new Post(parentFrame);
                parentFrame.add(postPanel);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // Add more components and their logic as needed
    }
}
