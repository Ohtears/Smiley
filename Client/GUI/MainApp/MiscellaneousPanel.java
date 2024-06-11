package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

public class MiscellaneousPanel extends JPanel {

    public MiscellaneousPanel() {
        setBorder(BorderFactory.createTitledBorder("Miscellaneous"));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(5, 1));

        JLabel label1 = new JLabel("Item 1");
        JLabel label2 = new JLabel("Item 2");
        add(label1);
        add(label2);
    }
}
