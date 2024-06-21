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
    @SuppressWarnings("unused")
    private Post post;
    @SuppressWarnings("unused")
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

        // Post Panel
        PostPanel postPanel = new PostPanel(post, listener);
        postPanel.setPreferredSize(new Dimension(616, 150));
        add(postPanel, BorderLayout.NORTH);

        // Comment Input Area
        JPanel commentInputPanel = new JPanel();
        commentInputPanel.setLayout(new BorderLayout());
        commentInputPanel.setBackground(new Color(64, 68, 75));

        JTextField commentTextField = new JTextField();
        commentInputPanel.add(commentTextField, BorderLayout.CENTER);

        JButton postCommentButton = new JButton("Post Comment");
        commentInputPanel.add(postCommentButton, BorderLayout.EAST);

        add(commentInputPanel, BorderLayout.CENTER);

        // Comments Section
        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentsPanel.setBackground(new Color(64, 68, 75));

        CustomScrollPane commentsScrollPane = new CustomScrollPane(commentsPanel);
        add(commentsScrollPane, BorderLayout.SOUTH);

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
            
                for (Comment comment : comments) {
                    CommentPanel commentPanel = new CommentPanel(comment);
                    commentsPanel.add(commentPanel);
                }
            }
    
            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "failed", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

    }
}
