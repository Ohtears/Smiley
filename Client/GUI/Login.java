package Client.GUI;

import Server.Database.MYSQLHandler;
import java.awt.*;
import javax.swing.*;

public class Login extends JPanel {
    private final JDialog parentDialog;

    public Login(JDialog parentDialog) {
        this.parentDialog = parentDialog;
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Sign in to Twitter");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel userLabel = new JLabel("Email:");
        userLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(userLabel, gbc);

        JTextField userText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(userText, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        JPasswordField passwordText = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordText, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.BLACK);
        loginButton.setBackground(Color.CYAN);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        loginButton.addActionListener(e -> {

            String email = userText.getText();

            String password = new String(passwordText.getPassword());

            if (email == null || email.isEmpty() || password.isEmpty()

            ) {
                JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {

                String hashedPassword = HashPassword.hashwithsha256(password);

                if (MYSQLHandler.Checkpassword(email, hashedPassword)){
                    
                    parentDialog.dispose();

                }
                else {
                    JOptionPane.showMessageDialog(null, "Password Incorrect", "Error", JOptionPane.ERROR_MESSAGE);

                }

            }
        });

        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(noAccountLabel, gbc);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setBackground(Color.CYAN);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(signUpButton, gbc);

        signUpButton.addActionListener(e -> {
            parentDialog.dispose();
            openRegisterDialog();
        });
    }

    private void openRegisterDialog() {
        JDialog registerDialog = new JDialog(parentDialog, "Register", Dialog.ModalityType.APPLICATION_MODAL);
        registerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        registerDialog.setSize(400, 300);
        registerDialog.setLocationRelativeTo(parentDialog);
        registerDialog.setLayout(new BorderLayout());

        Register registerPanel = new Register(registerDialog);
        registerDialog.add(registerPanel, BorderLayout.CENTER);

        registerDialog.setVisible(true);
    }
}
