package Client.GUI;

import java.awt.*;
import javax.swing.*;

public class MenuMain {
    public static void main(String[] args){
        
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Smiley");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);
            frame.setSize(1200, 720);

            JPanel leftsidep = new JPanel();
            JPanel imagetwitter = new ImageHandler("twittericon.png");
            leftsidep.setPreferredSize(new Dimension(700, 600));
            imagetwitter.setPreferredSize(new Dimension(700, 600));
            leftsidep.add(imagetwitter);
            leftsidep.setBackground(Color.BLACK);

            JPanel footerp = new JPanel();
            footerp.setLayout(new BoxLayout(footerp, BoxLayout.Y_AXIS));
            footerp.setPreferredSize(new Dimension(1200, 120));
            footerp.setBackground(Color.BLACK);

            JLabel labelfooterp1 = new JLabel("Contact Us");
            JLabel labelfooterp2 = new JLabel("+98 912 779 8614");
            JLabel labelfooterp3 = new JLabel("This project was made by Ashkan Marali aka Tears");
            JLabel labelfooterp4 = new JLabel("Student of University of Guilan");

            Font footerFont = new Font("Arial", Font.PLAIN, 12);
            Color footerColor = Color.WHITE;

            labelfooterp1.setFont(footerFont);
            labelfooterp1.setForeground(footerColor);
            labelfooterp2.setFont(footerFont);
            labelfooterp2.setForeground(footerColor);
            labelfooterp3.setFont(footerFont);
            labelfooterp3.setForeground(footerColor);
            labelfooterp4.setFont(footerFont);
            labelfooterp4.setForeground(footerColor);

            labelfooterp1.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelfooterp2.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelfooterp3.setAlignmentX(Component.CENTER_ALIGNMENT);
            labelfooterp4.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            footerp.add(Box.createVerticalStrut(55));
            footerp.add(labelfooterp1);
            footerp.add(labelfooterp2);
            footerp.add(labelfooterp3);
            footerp.add(labelfooterp4);
            
            
            footerp.setBackground(Color.GRAY);
            
            JPanel mainp = new JPanel();
            mainp.setLayout(new BoxLayout(mainp, BoxLayout.Y_AXIS));
            mainp.setBackground(Color.BLACK);

            JLabel mainlabel1 = new JLabel("Happening now");
            mainlabel1.setFont(new Font("Arial", Font.BOLD, 36));
            mainlabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainlabel1.setForeground(Color.WHITE);

            JLabel mainlabel2 = new JLabel("Join today.");
            mainlabel2.setFont(new Font("Arial", Font.PLAIN, 24));
            mainlabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainlabel2.setForeground(Color.WHITE);

            JButton registerButton = new JButton("Create Account");
            registerButton.setBackground(Color.CYAN);
            registerButton.setForeground(Color.WHITE);
            registerButton.setFont(new Font("Arial", Font.BOLD, 22));
            registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);


            JPanel termsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            termsPanel.setBackground(Color.BLACK);

            JLabel agreementLabel = new JLabel("By signing up, you agree to the");
            agreementLabel.setForeground(Color.WHITE);
            agreementLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            agreementLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton termsButton = new JButton("Terms of Service");
            termsButton.setForeground(Color.CYAN);
            termsButton.setBackground(Color.BLACK);
            termsButton.setFont(new Font("Arial", Font.PLAIN, 16));
            termsButton.setBorderPainted(false);
            termsButton.setFocusPainted(false);
            termsButton.setMargin(new Insets(0, 0, 0, 0));

            JButton policyButton = new JButton("Privacy Policy");
            policyButton.setForeground(Color.CYAN);
            policyButton.setBackground(Color.BLACK);
            policyButton.setFont(new Font("Arial", Font.PLAIN, 16));
            policyButton.setBorderPainted(false);
            policyButton.setFocusPainted(false);

            policyButton.addActionListener(e -> showPrivacyPolicy());
            termsButton.addActionListener(e -> showTermsOfService());

            termsPanel.add(agreementLabel);
            termsPanel.add(termsButton);
            termsPanel.add(Box.createVerticalStrut(10));
            termsPanel.add(policyButton);

            mainp.add(Box.createVerticalStrut(55));
            mainp.add(mainlabel1);
            mainp.add(Box.createVerticalStrut(55));
            mainp.add(mainlabel2);
            mainp.add(Box.createVerticalStrut(33));
            mainp.add(registerButton);
            mainp.add(Box.createVerticalStrut(33));
            mainp.add(termsPanel);
            mainp.add(Box.createVerticalStrut(33));

            JLabel mainlabel3 = new JLabel("Already have an account?");
            mainlabel3.setFont(new Font("Arial", Font.PLAIN, 18));
            mainlabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainlabel3.setForeground(Color.WHITE);

            JButton loginButton = new JButton("Sign in");
            loginButton.setBackground(Color.BLACK);
            loginButton.setForeground(Color.CYAN);
            loginButton.setFont(new Font("Arial", Font.BOLD, 18));
            loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            mainp.add(mainlabel3);
            mainp.add(Box.createVerticalStrut(22));
            mainp.add(loginButton);
            mainp.add(Box.createVerticalGlue());


            frame.add(leftsidep, BorderLayout.WEST);
            frame.add(mainp, BorderLayout.CENTER);
            frame.add(footerp, BorderLayout.SOUTH);

            loginButton.addActionListener(e -> {
                JDialog loginDialog = new JDialog(frame, "Login", Dialog.ModalityType.APPLICATION_MODAL);
                loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                loginDialog.setSize(400, 300);
                loginDialog.setLocationRelativeTo(frame);
                loginDialog.setLayout(new BorderLayout());

                Login loginPanel = new Login(loginDialog, frame);
                loginDialog.add(loginPanel, BorderLayout.CENTER);

                loginDialog.setVisible(true);
            });

            registerButton.addActionListener(e -> {
                JDialog registerDialog = new JDialog(frame, "Register", Dialog.ModalityType.APPLICATION_MODAL);
                registerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                registerDialog.setSize(400, 300);
                registerDialog.setLocationRelativeTo(frame);
                registerDialog.setLayout(new BorderLayout());

                Register registerPanel = new Register(registerDialog);
                registerDialog.add(registerPanel, BorderLayout.CENTER);

                registerDialog.setVisible(true);
            });

            frame.setVisible(true);
        });

    }
    private static void showPrivacyPolicy() {
        String privacyPolicyContent = ConstsMessages.PRIVACY_POLICY.getMessage();
        JOptionPane.showMessageDialog(null, privacyPolicyContent, "Privacy Policy", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showTermsOfService() {
        String termsOfServiceContent = ConstsMessages.TERMS_OF_SERVICE.getMessage();
        JOptionPane.showMessageDialog(null, termsOfServiceContent, "Terms of Service", JOptionPane.INFORMATION_MESSAGE);
    }
}
