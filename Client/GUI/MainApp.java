package Client.GUI;

import java.awt.*;
import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Twitter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);

            Login loginPanel = new Login(frame);
            Register regiserPanel = new Register(frame);

            JButton registerButton = new JButton("Register");
            JButton loginButton = new JButton("Login");
            
            JPanel panel = new JPanel(new GridLayout(2, 1));
            
            panel.add(registerButton);
            panel.add(loginButton);

            frame.add(panel, BorderLayout.CENTER);

            registerButton.addActionListener(e -> {
                
                frame.getContentPane().removeAll();
                frame.add(regiserPanel);
                frame.revalidate();
                frame.repaint();
                
            });
            
            loginButton.addActionListener(e -> {
                
                frame.getContentPane().removeAll();
                frame.add(loginPanel);
                frame.revalidate();
                frame.repaint();
                
            });

            frame.setVisible(true);

        });
    }
}

