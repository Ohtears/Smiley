package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

public class MiscellaneousPanel extends JPanel {

    public MiscellaneousPanel() {
        setBorder(BorderFactory.createTitledBorder("Miscellaneous"));
        setBackground(new Color(47, 49, 54));
        setLayout(new GridLayout(5, 1));

        JLabel label1 = new JLabel("Coming Soon");
        add(label1);
    }
}
