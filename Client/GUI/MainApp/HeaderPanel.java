package Client.GUI.MainApp;

import java.awt.*;
import javax.swing.*;

public class HeaderPanel extends JPanel {

    public HeaderPanel() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Header Panel");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.CENTER);
    
        Dimension preferredSize = new Dimension(1280, 50);
        setPreferredSize(preferredSize);

    }
}
