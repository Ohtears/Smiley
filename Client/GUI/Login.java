package Client.GUI;

import Client.GUI.MainApp.App;
import Client.Models.CurrentUser;
import Client.Models.User;
import Client.Network.JsonConverter;
import Client.Network.RequestHandler;
import Client.Network.RequestHandler.Callback;
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

                List<User> userList = new ArrayList<>();
                User user4check = new User(0, null, null, email, hashedPassword, null, null);
                userList.add(user4check);
                JSONObject jsonRequest = JsonConverter.usersToJson(userList, RequestType.CHECKPASSWORD);
                
                RequestHandler requestHandler = new RequestHandler();
                requestHandler.sendRequestAsync(jsonRequest.toString(), new Callback() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject jsonResponse = new JSONObject(response);
                        // Handle success (e.g., update UI, save user info, etc.)
                        JOptionPane.showMessageDialog(null, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                        boolean passwordIsValid = JsonConverter.jsonToBoolean(jsonResponse);

                        if (passwordIsValid){
                    
                            // JSONObject jsonRequest_USER = JsonConverter.usersToJson(userList, RequestType.GETCURRENTUSER);
                            // JSONObject responseServer_USER = RequestHandler.call(jsonRequest_USER);
        
                            // User currentuser = (JsonConverter.jsonToUsers(responseServer_USER)).get(0);
                            // User currentuser = MYSQLHandler.getCurrentUser(email);
                            System.out.println('s');
                            // CurrentUser.getInstance().setUser(currentuser);
        
                            parentDialog.dispose();
                            mainFrame.dispose();
                            openApp();
        
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Password Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
        
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {
                        // Handle failure (e.g., show error message)
                        JOptionPane.showMessageDialog(null, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });

                // MYSQLHandler.checkPassword(email, hashedPassword)


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

    private void openApp(){

        SwingUtilities.invokeLater(() -> {
            App app = new App();

            app.setVisible(true);


        });

    }


}
