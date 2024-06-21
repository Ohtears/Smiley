package Client.GUI.MainApp;

import javax.swing.*;
import java.awt.*;
import Client.Models.Comment;
import Client.Tools.ImageHandler;

public class CommentPanel extends JPanel {
    @SuppressWarnings("unused")
    private Comment comment;

    public CommentPanel(Comment comment) {
        this.comment = comment;
        setLayout(new BorderLayout());
        setBackground(new Color(64, 68, 75));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 
        Dimension size = new Dimension(610, 150);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageHandler profileImageHandler = new ImageHandler("/Client/Static/Images/smiley.png");
                profileImageHandler.drawCircularImage(g, 5, 5, 40);
            }
        };
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(64, 68, 75));
        headerPanel.setPreferredSize(new Dimension(610, 50));

        JPanel profileImagePlaceholder = new JPanel();
        profileImagePlaceholder.setPreferredSize(new Dimension(50, 50));
        profileImagePlaceholder.setBackground(new Color(64, 68, 75));
        headerPanel.add(profileImagePlaceholder);

        String userInfo = comment.getUser().getName() + " @" + comment.getUser().Username + " " + comment.getTimestamp().toString();
        JLabel userInfoLabel = new JLabel(userInfo);
        userInfoLabel.setForeground(Color.WHITE);
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        headerPanel.add(userInfoLabel);

        add(headerPanel, BorderLayout.NORTH);

        JTextArea contentTextArea = new JTextArea(comment.getContent());
        contentTextArea.setEditable(false);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setForeground(Color.WHITE);
        contentTextArea.setBackground(new Color(64, 68, 75));
        JScrollPane contentScrollPane = new JScrollPane(contentTextArea);
        add(contentScrollPane, BorderLayout.CENTER);

        contentTextArea.addMouseWheelListener(e -> {
            e.getComponent().getParent().dispatchEvent(e);
        });

        contentScrollPane.addMouseWheelListener(e -> {
            e.getComponent().getParent().dispatchEvent(e);
        });
    }
}
