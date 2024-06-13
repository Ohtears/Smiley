package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

public class ForumsListPanel extends JPanel {

    public ForumsListPanel() {
        setBorder(BorderFactory.createTitledBorder("Forums List"));
        setBackground(new Color(32, 34, 37));
        setLayout(new GridLayout(2, 1));

        JLabel label1 = new JLabel("Forum 1");
        JLabel label2 = new JLabel("Forum 2");
        add(label1);
        add(label2);
    }
}
