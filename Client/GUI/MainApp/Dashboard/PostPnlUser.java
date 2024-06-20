package Client.GUI.MainApp.Dashboard;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Client.GUI.MainApp.Style.CustomScrollPane;
import Client.Models.Post;

import java.awt.*;

public class PostPnlUser extends JPanel{

    @SuppressWarnings("unused")
    private Post post;

    public PostPnlUser(Post post) {
        this.post = post;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(64, 68, 75));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); 
        Dimension size = new Dimension(600, 100);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        JLabel usernameLabel = new JLabel(post.getuser().getName());
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBackground(new Color(64, 68, 75));
        topPanel.add(usernameLabel);
        add(topPanel, BorderLayout.NORTH);

        JTextArea contentTextArea = new JTextArea(post.getContent());
        contentTextArea.setEditable(false);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setForeground(Color.WHITE);
        contentTextArea.setBackground(new Color(64, 68, 75));
        CustomScrollPane contentScrollPane = new CustomScrollPane(contentTextArea);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(64, 68, 75));
        centerPanel.add(contentScrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JLabel timestampLabel = new JLabel(post.getTimestamp().toString());
        timestampLabel.setForeground(Color.GRAY);
        timestampLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bottomPanel.setBackground(new Color(64, 68, 75));
        bottomPanel.add(timestampLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        contentTextArea.addMouseWheelListener(e -> {
            e.getComponent().getParent().dispatchEvent(e);
        });

        contentScrollPane.addMouseWheelListener(e -> {
            e.getComponent().getParent().dispatchEvent(e);
        });
    }
}
