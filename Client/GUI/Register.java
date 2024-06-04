package Client.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Register extends BasePanel {
 
    private final JFrame parentFrame;

    public Register(JFrame parentFrame){

        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel userLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userLabel, gbc);

        JTextField userText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(userText, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        JTextField passwordText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordText, gbc);

        JLabel bioLabel = new JLabel("BIO:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(bioLabel, gbc);

        JTextField bioText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(bioText, gbc);

        JButton RegisterButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(RegisterButton, gbc);

        RegisterButton.addActionListener(e -> {

            parentFrame.getContentPane().removeAll();
            Dashboard dashboardPanel = new Dashboard(parentFrame);
            parentFrame.add(dashboardPanel);
            parentFrame.revalidate();
            parentFrame.repaint();

        });
    }
    
}
