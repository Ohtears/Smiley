package Client.GUI;

import Client.GUI.MainApp.Style.CustomButton;
import Client.GUI.MainApp.App;
import Client.Models.CurrentUser;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
import Client.Tools.HashPassword;
import Client.Network.RequestType;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import org.json.JSONObject;

public class Login extends JPanel {
    private final JDialog parentDialog;
    private final JFrame mainFrame;

    public Login(JDialog parentDialog, JFrame mainFrame) {
        this.parentDialog = parentDialog;
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Sign in to Smiley");
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

        CustomButton loginButton = new CustomButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        if (parentDialog.getRootPane() != null) {
            parentDialog.getRootPane().setDefaultButton(loginButton);
        }


        loginButton.addActionListener(e -> {

            String email = userText.getText();

            String password = new String(passwordText.getPassword());

            if (email == null || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                String hashedPassword = HashPassword.hashwithsha256(password);

                List<User> userList = new ArrayList<>();
                User user4check = new User(0, "", "", email, hashedPassword, null, "");
                userList.add(user4check);
                JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.CHECKPASSWORD);
                
                RequestHandler requestHandler = new RequestHandler();
                requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean passwordIsValid = JsonConverter.jsonToBoolean(jsonResponse);

                        if (passwordIsValid) {
                            JSONObject jsonRequest_USER = JsonConverter.usersToJson(userList, RequestType.GETCURRENTUSER);
                            requestHandler.sendRequestAsync(jsonRequest_USER.toString(), new Callback() {
                                @Override
                                public void onSuccess(String response_USER) {
                                    JSONObject jsonResponse_USER = new JSONObject(response_USER);

                                    User currentuser = (JsonConverter.jsonToUsers(jsonResponse_USER)).get(0);
                                    CurrentUser.getInstance().setUser(currentuser);
                                    parentDialog.dispose();
                                    mainFrame.dispose();
                                    openApp();
                                }

                                @Override
                                public void onFailure(IOException e) {
                                    JOptionPane.showMessageDialog(null, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            });
                        } else {
                            JOptionPane.showMessageDialog(null, "Password Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {
                        JOptionPane.showMessageDialog(null, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        });

        JLabel noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(noAccountLabel, gbc);

        CustomButton signUpButton = new CustomButton("Sign Up");
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

    private void openApp() {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}
