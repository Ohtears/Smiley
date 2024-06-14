package Client.GUI.MainApp;

import javax.swing.*;
import java.awt.*;
import Client.Models.Post;

public class PostPanel extends JPanel {
    @SuppressWarnings("unused")
    private Post post;

    public PostPanel(Post post) {
        this.post = post;
        setLayout(new BorderLayout());
        setBackground(new Color(64, 68, 75));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Dimension size = new Dimension(450, 100);
        setPreferredSize(size); 
        setMaximumSize(size); 
        setMinimumSize(size); 


        JLabel contentLabel = new JLabel("<html>" + post.getContent() + "</html>");
        contentLabel.setForeground(Color.WHITE);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(contentLabel, BorderLayout.CENTER);

        JLabel timestampLabel = new JLabel(post.getTimestamp().toString());
        timestampLabel.setForeground(Color.GRAY);
        timestampLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        add(timestampLabel, BorderLayout.SOUTH);
    }
}
