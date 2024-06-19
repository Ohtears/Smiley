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
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); 
        Dimension size = new Dimension(610, 100);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        JLabel usernameLabel = new JLabel(post.getuser().getName());
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(usernameLabel, BorderLayout.NORTH);

        JTextArea contentTextArea = new JTextArea(post.getContent());
        contentTextArea.setEditable(false);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setForeground(Color.WHITE);
        contentTextArea.setBackground(new Color(64, 68, 75));
        JScrollPane contentScrollPane = new JScrollPane(contentTextArea);
        add(contentScrollPane, BorderLayout.CENTER);

        JLabel timestampLabel = new JLabel(post.getTimestamp().toString());
        timestampLabel.setForeground(Color.GRAY);
        timestampLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        add(timestampLabel, BorderLayout.SOUTH);

        contentTextArea.addMouseWheelListener(e -> {
            e.getComponent().getParent().dispatchEvent(e);
        });

        contentScrollPane.addMouseWheelListener(e -> {
            e.getComponent().getParent().dispatchEvent(e);
        });
    }
}
