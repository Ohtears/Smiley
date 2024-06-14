package Client.GUI.MainApp;

import javax.swing.*;

import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Client.GUI.MainApp.Style.CustomScrollPane;
import Client.Models.CurrentUser;
import Client.Models.Post;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Network.RequestType;

public class PostsListPanel extends JPanel {
    private List<Post> posts;
    private JPanel postsContainer;
    private Post lastpost;
    private ScheduledExecutorService scheduler;


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

        fetchAllPosts();

        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> refresh(), 0L, 1L, TimeUnit.SECONDS);    
    
    }
    private void fetchAllPosts() {
        User currentUser = CurrentUser.getInstance().getUser();
        
        List<User> userList = new ArrayList<>();
        userList.add(currentUser);
        RequestHandler requestHandler = new RequestHandler();
        JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.GETALLPOSTS);

        requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
            @Override
            public void onSuccess(String response) {
                JSONObject responseServer = new JSONObject(response);
                List<Post> fetchedPosts = JsonConverter.jsonToPosts(responseServer);

                SwingUtilities.invokeLater(() -> {
                    for (Post post : fetchedPosts) {
                        addNewPost(post);
                    }
                });
            }
    
            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to fetch posts", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
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

                    User currentUser = CurrentUser.getInstance().getUser();
                    
                    List<User> userList = new ArrayList<>();
                    userList.add(currentUser);
                    JSONObject jsonRequest = JsonConverter.usersToJson(userList, content, RequestType.CREATEPOST);
                    RequestHandler requestHandler = new RequestHandler();
                    requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
                        @Override
                        public void onSuccess(String response) {
                            

                        }
        
                        @Override
                        public void onFailure(IOException e) {
                            JOptionPane.showMessageDialog(null, "Failed to send post", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    refresh();
                    addPostFrame.dispose();

                } else {
                    JOptionPane.showMessageDialog(addPostFrame, "Post content cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(submitButton, BorderLayout.SOUTH);

        addPostFrame.setVisible(true);
    }

    public void refresh() {
        User currentUser = CurrentUser.getInstance().getUser();
        
        List<User> userList = new ArrayList<>();
        userList.add(currentUser);
        RequestHandler requestHandler = new RequestHandler();
        JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.GETALLPOSTS);

        requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
            @Override
            public void onSuccess(String response) {
                JSONObject responseServer = new JSONObject(response);

                List<Post> posts = JsonConverter.jsonToPosts(responseServer);

                Post latestPost = posts.get(posts.size() - 1);

                for (Post post : posts) {
                    System.out.println(post.content);}

                if (lastpost != null) {
                    if (latestPost.getPostId() == lastpost.getPostId()) {
                        return;
                    }
                }

                lastpost = latestPost;
                addNewPost(latestPost);

            }
    
            @Override
            public void onFailure(IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to fetch posts", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });



    }
    
    private void addNewPost(Post post) {
        posts.add(post);
        PostPanel postPanel = new PostPanel(post);
        postsContainer.add(postPanel);
        postsContainer.revalidate();
        postsContainer.repaint();
    }
}