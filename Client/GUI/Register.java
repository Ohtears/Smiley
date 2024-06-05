package Client.GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Register extends JPanel {
    private final JDialog parentDialog;

    public Register(JDialog parentDialog) {
        this.parentDialog = parentDialog;
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

        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        registerButton.addActionListener(e -> {

            parentDialog.dispose();
        });
    }
}
