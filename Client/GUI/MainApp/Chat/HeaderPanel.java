package Client.GUI.MainApp.Chat;

import java.awt.*;

import javax.swing.*;

public class HeaderPanel extends JPanel {
    
    public HeaderPanel(String userName) {
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nameLabel = new JLabel(userName);
        add(nameLabel);
    }
}
