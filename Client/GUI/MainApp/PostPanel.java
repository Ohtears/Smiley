package Client.GUI.MainApp;

import javax.swing.*;
import java.awt.*;

import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.GUI.MainApp.Style.CustomButton;
import Client.Models.Post;

public class PostPanel extends JPanel {
    @SuppressWarnings("unused")
    private Post post;
    @SuppressWarnings("unused")
    private PanelSwitchListener listener;


    public PostPanel(Post post, PanelSwitchListener listener) {
        this.post = post;
        setLayout(new BorderLayout());
        setBackground(new Color(64, 68, 75));
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        Dimension size = new Dimension(610, 150);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(64, 68, 75));

        JLabel profileLabel = new JLabel();
        profileLabel.setPreferredSize(new Dimension(40, 40));
        profileLabel.setOpaque(true);
        profileLabel.setBackground(Color.GRAY);
        profileLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        headerPanel.add(profileLabel);

        String userInfo = post.getuser().getName() + " @" + post.getuser().Username + " " + post.getTimestamp().toString();
        JLabel userInfoLabel = new JLabel(userInfo);
        userInfoLabel.setForeground(Color.WHITE);
        userInfoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        headerPanel.add(userInfoLabel);

        add(headerPanel, BorderLayout.NORTH);

        JTextArea contentTextArea = new JTextArea(post.getContent());
        contentTextArea.setEditable(false);
        contentTextArea.setLineWrap(true);
        contentTextArea.setWrapStyleWord(true);
        contentTextArea.setForeground(Color.WHITE);
        contentTextArea.setBackground(new Color(64, 68, 75));
        JScrollPane contentScrollPane = new JScrollPane(contentTextArea);
        add(contentScrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(new Color(64, 68, 75));

        CustomButton repliesButton = new CustomButton("Replies");
        footerPanel.add(repliesButton);

        CustomButton likesButton = new CustomButton("Likes");
        footerPanel.add(likesButton);

        add(footerPanel, BorderLayout.SOUTH);

        repliesButton.addActionListener(e -> {
            listener.onPanelSwitch(new CommentSectionPanel(post, listener));
        });


        contentTextArea.addMouseWheelListener(e -> {
            e.getComponent().getParent().dispatchEvent(e);
        });

        contentScrollPane.addMouseWheelListener(e -> {
            e.getComponent().getParent().dispatchEvent(e);
        });
    }
}
