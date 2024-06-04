package Client.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Login extends BasePanel {
    private final JFrame parentFrame;

    public Login(JFrame parentFrame) {
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

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        loginButton.addActionListener(e -> {

            parentFrame.getContentPane().removeAll();
            Dashboard dashboardPanel = new Dashboard(parentFrame);
            parentFrame.add(dashboardPanel);
            parentFrame.revalidate();
            parentFrame.repaint();

        //     //
        // loginButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {

        //         parentFrame.getContentPane().removeAll();
        //         Dashboard dashboardPanel = new Dashboard(parentFrame);
        //         parentFrame.add(dashboardPanel);
        //         parentFrame.revalidate();
        //         parentFrame.repaint();
        //     }
        // }); 
        });
    }
}
