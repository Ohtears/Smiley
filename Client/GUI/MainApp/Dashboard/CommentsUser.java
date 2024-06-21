package Client.GUI.MainApp.Dashboard;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Client.Models.Comment;

public class CommentsUser implements ContentPanel {

    private JPanel panel;
    @SuppressWarnings("unused")
    private List<Comment> Comments;

    public CommentsUser(List<Comment> Comments) {
        this.Comments = Comments;
        panel = new JPanel();
        panel.setBackground(new Color(53, 57, 63));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        for (Comment Comment : Comments) {
            // CommentsPnlUser CommentsPanel = new CommentsPnlUser(Comment);
            // panel.add(CommentsPanel);
        }
        //needs work
    }
    @Override
    public JPanel getPanel() {
        return panel;
    }
    
}
