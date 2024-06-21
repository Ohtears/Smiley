package Client.GUI.MainApp;

import javax.swing.*;

import Client.GUI.MainApp.Dashboard.PanelSwitchListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeButton extends JButton {

    @SuppressWarnings("unused")
    private PanelSwitchListener listener;

    public HomeButton(PanelSwitchListener listener) {
        this.listener = listener;

        ImageIcon icon = new ImageIcon(getClass().getResource("/Client/Static/Images/smiley.png"));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        setIcon(icon);
        setPreferredSize(new Dimension(70, 70));
        setMaximumSize(new Dimension(70, 70));
        setMinimumSize(new Dimension(70, 70));
        setHorizontalAlignment(SwingConstants.CENTER); 

        setContentAreaFilled(false);
        setBorderPainted(false);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("SAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                listener.onPanelSwitch(new PostsListPanel(listener)); 


            }
        });
    }
}
