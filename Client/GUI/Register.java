package Client.GUI;

import Client.Models.TimeDate;
import Client.Models.User;
import java.awt.*;
import javax.swing.*;

public class Register extends JPanel {
    private final JDialog parentDialog;

    public Register(JDialog parentDialog) {
        this.parentDialog = parentDialog;
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); 

        JLabel titleLabel = new JLabel("Create your account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nameLabel, gbc);

        JTextField nameText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nameText, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(emailLabel, gbc);

        JTextField emailText = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(emailText, gbc);

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(dobLabel, gbc);

        JPanel dobPanel = new JPanel();
        dobPanel.setBackground(Color.BLACK);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthCombo = new JComboBox<>(months);
        dobPanel.add(monthCombo);

        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        JComboBox<Integer> dayCombo = new JComboBox<>(days);
        dobPanel.add(dayCombo);

        Integer[] years = new Integer[100];
        for (int i = 0; i < 100; i++) {
            years[i] = 1924 + i;
        }
        JComboBox<Integer> yearCombo = new JComboBox<>(years);
        dobPanel.add(yearCombo);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(dobPanel, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(passwordLabel, gbc);

        JPasswordField passwordText = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(passwordText, gbc);

        JButton registerButton = new JButton("Create Account");
        registerButton.setForeground(Color.BLACK);
        registerButton.setBackground(Color.CYAN);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            String username = nameText.getText();
            String email = emailText.getText();
            int day = Integer.parseInt(dayCombo.getSelectedItem().toString());
            String month = monthCombo.getSelectedItem().toString();
            int year = Integer.parseInt(yearCombo.getSelectedItem().toString());
            String password = new String(passwordText.getPassword());

            if (username == null || username.isEmpty() ||
            email == null || email.isEmpty() ||
            month == null || month.isEmpty() ||
            password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            else {
                    
                String hashedPassword = HashPassword.hashwithsha256(password);
                TimeDate birthday = new TimeDate(day, month, year);
                User user = new User(10, username , email, hashedPassword, birthday);
                parentDialog.dispose();

            }
            

        });
    }
}
