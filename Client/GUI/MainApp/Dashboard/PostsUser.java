package Client.GUI.MainApp.Dashboard;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import Client.Models.Post;

public class PostsUser implements ContentPanel {
    private JPanel panel;
    @SuppressWarnings("unused")
    private List<Post> posts;

    public PostsUser(List<Post> posts) {
        this.posts = posts;
        panel = new JPanel();
        panel.setBackground(new Color(53, 57, 63));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        for (Post post : posts) {
            PostPnlUser postPanel = new PostPnlUser(post);
            panel.add(postPanel);
        }
    
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}



