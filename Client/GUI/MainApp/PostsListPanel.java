package Client.GUI.MainApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Client.GUI.MainApp.Style.CustomScrollPane;
import Client.Models.CurrentUser;
import Client.Models.Post;
import Client.Models.User;

public class PostsListPanel extends JPanel {
    private List<Post> posts;
    private JPanel postsContainer;

    public PostsListPanel() {
        posts = new ArrayList<>();
        setBorder(BorderFactory.createTitledBorder("Posts List"));
        setBackground(new Color(54, 57, 63));
        setLayout(new BorderLayout());

        postsContainer = new JPanel();
        postsContainer.setLayout(new BoxLayout(postsContainer, BoxLayout.Y_AXIS));
        postsContainer.setBackground(new Color(54, 57, 63));

        CustomScrollPane scrollPane = new CustomScrollPane(postsContainer);

        add(scrollPane, BorderLayout.CENTER);

        JButton addPostButton = new JButton("Add Post");
        addPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddPostFrame();
            }
        });
        add(addPostButton, BorderLayout.SOUTH);
    }

    private void openAddPostFrame() {
        JFrame addPostFrame = new JFrame("Add New Post");
        addPostFrame.setSize(300, 200);
        addPostFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addPostFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        addPostFrame.add(panel);

        JTextArea postTextArea = new JTextArea();
        panel.add(new JScrollPane(postTextArea), BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = postTextArea.getText().trim();
                if (!content.isEmpty()) {
                    addNewPost(content);
                    addPostFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(addPostFrame, "Post content cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(submitButton, BorderLayout.SOUTH);

        addPostFrame.setVisible(true);
    }

    private void addNewPost(String content) {
        User currentUser = CurrentUser.getInstance().getUser();
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Post newPost = new Post(posts.size() + 1, currentUser, content, timestamp);
        posts.add(newPost);

        PostPanel postPanel = new PostPanel(newPost);
        postsContainer.add(postPanel);
        postsContainer.revalidate();
        postsContainer.repaint();
    }


}
