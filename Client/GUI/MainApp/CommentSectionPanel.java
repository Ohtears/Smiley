package Client.GUI.MainApp;

import javax.swing.*;

import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Client.Models.Post;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Network.RequestType;
import Client.GUI.MainApp.Dashboard.PanelSwitchListener;
import Client.GUI.MainApp.Style.CustomScrollPane;
import Client.Models.Comment;
import Client.Models.CurrentUser;

public class CommentSectionPanel extends JPanel {
    private Post post;
    private PanelSwitchListener listener;

    public CommentSectionPanel(Post post, PanelSwitchListener listener) {
        this.post = post;
        this.listener = listener;
        setLayout(new BorderLayout());
        setBackground(new Color(64, 68, 75));
        Dimension size = new Dimension(616, 720);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        // Original post panel at the top
        PostPanel postPanel = new PostPanel(post, listener);
        add(postPanel, BorderLayout.NORTH);

        // Scrollable comments panel below the original post panel
        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentsPanel.setBackground(new Color(64, 68, 75));

        CustomScrollPane commentsScrollPane = new CustomScrollPane(commentsPanel);
        commentsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(commentsScrollPane, BorderLayout.CENTER); // Takes remaining space

        // Comment input panel fixed at the bottom
        JPanel commentInputPanel = new JPanel();
        commentInputPanel.setLayout(new BorderLayout());
        commentInputPanel.setBackground(new Color(64, 68, 75));

        JTextField commentTextField = new JTextField();
        commentTextField.setPreferredSize(new Dimension(400, 25));
        commentTextField.setMinimumSize(new Dimension(400, 25));
        commentTextField.setMaximumSize(new Dimension(400, 25));
        commentInputPanel.add(commentTextField, BorderLayout.CENTER);

        JButton postCommentButton = new JButton("Post Comment");
        commentInputPanel.add(postCommentButton, BorderLayout.EAST);

        add(commentInputPanel, BorderLayout.SOUTH); // Fixed position at the bottom

        // Fetch comments from server
        User currentUser = CurrentUser.getInstance().getUser();
        List<User> listUsers = new ArrayList<>();
        listUsers.add(currentUser);
        JSONObject jsonRequest = JsonConverter.usersToJson(listUsers, Integer.toString(post.getPostId()), RequestType.GETCOMMENTSPOST);
        RequestHandler requestHandler = new RequestHandler();
        requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
            @Override
            public void onSuccess(String response) {
                JSONObject responseServer = new JSONObject(response);
                List<Comment> comments = JsonConverter.jsonToComments(responseServer);

                SwingUtilities.invokeLater(() -> {
                    commentsPanel.removeAll(); // Clear existing comments
                    for (Comment comment : comments) {
                        CommentPanel commentPanel = new CommentPanel(comment);
                        commentsPanel.add(commentPanel);
                    }
                    commentsPanel.revalidate(); // Revalidate to reflect changes
                    commentsPanel.repaint();
                });
            }

            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to retrieve comments", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        postCommentButton.addActionListener(e -> {
            String commentText = commentTextField.getText();
            if (commentText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a comment", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Post> posts = new ArrayList<>();
            posts.add(post);
            JSONObject jsonRequest1 = JsonConverter.PostsToJson(posts, CurrentUser.getInstance().getUser(), commentText, RequestType.CREATECOMMENT);
            RequestHandler requestHandler1 = new RequestHandler();
            requestHandler1.sendRequestAsync(jsonRequest1.toString(), new Callback() {
                @Override
                public void onSuccess(String response) {
                    commentTextField.setText(""); 
                }

                @Override
                public void onFailure(IOException e) {
                    JOptionPane.showMessageDialog(null, "Failed to post comment", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
    }
}
