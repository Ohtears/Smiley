package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

public class PostsListPanel extends JPanel {

    public PostsListPanel() {
        setBorder(BorderFactory.createTitledBorder("Posts List"));
        setBackground(new Color(54, 57, 63));
        setLayout(new GridLayout(2, 1));

        JLabel label1 = new JLabel("Post 1");
        JLabel label2 = new JLabel("Post 2");
        add(label1);
        add(label2);
    }
}
