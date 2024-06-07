package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

public class DMListPanel extends JPanel {

    public DMListPanel() {
        setBorder(BorderFactory.createTitledBorder("DM List"));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(2, 1));

        // Add subcomponents or customize as needed
        JLabel label1 = new JLabel("DM 1");
        JLabel label2 = new JLabel("DM 2");
        add(label1);
        add(label2);
    }
}
