package Client.GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Post extends BasePanel {
    private JFrame parentFrame;

    public Post(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Use appropriate layout manager

        JLabel postLabel = new JLabel("Create a Post");
        postLabel.setBounds(300, 50, 200, 30);
        add(postLabel);

        JTextArea postTextArea = new JTextArea();
        postTextArea.setBounds(300, 100, 200, 100);
        add(postTextArea);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(300, 210, 200, 30);
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle post submission logic
                parentFrame.getContentPane().removeAll();
                Dashboard dashboardPanel = new Dashboard(parentFrame);
                parentFrame.add(dashboardPanel);
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // Add more components and their logic as needed
    }
}
