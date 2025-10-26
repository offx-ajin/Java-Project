package view; 

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

  
    private JTextField nameField, roomField, phoneField;
    private JButton userLoginBtn, clearBtn;


    private JTextField regField;
    private JPasswordField passField;
    private JButton adminLoginBtn;


    private JButton exitBtn;

    public LoginView(String role) {
        setTitle(role.equals("Admin") ? "Admin Login" : "Resident Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        JLabel titleLabel = new JLabel(role.equals("Admin") ? "Admin Login" : "Resident Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        if (role.equals("User")) {
            buildUserLogin(mainPanel);
        } else {
            buildAdminLogin(mainPanel);
        }
    }

    private void buildUserLogin(JPanel panel) {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Room Number:"));
        roomField = new JTextField();
        formPanel.add(roomField);

        formPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        userLoginBtn = new JButton("Login");
        clearBtn = new JButton("Clear");
        exitBtn = new JButton("Exit");

        buttonPanel.add(userLoginBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(exitBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void buildAdminLogin(JPanel panel) {
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));

        formPanel.add(new JLabel("Register Number:"));
        regField = new JTextField();
        formPanel.add(regField);

        formPanel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        formPanel.add(passField);

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        adminLoginBtn = new JButton("Login");
        exitBtn = new JButton("Exit");
        buttonPanel.add(adminLoginBtn);
        buttonPanel.add(exitBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }




    public JTextField getNameField() { return nameField; }
    public JTextField getRoomField() { return roomField; }
    public JTextField getPhoneField() { return phoneField; }
    public JButton getUserLoginBtn() { return userLoginBtn; }
    public JButton getClearBtn() { return clearBtn; }


    public JTextField getRegField() { return regField; }
    public JPasswordField getPassField() { return passField; }
    public JButton getAdminLoginBtn() { return adminLoginBtn; }

    public JButton getExitBtn() { return exitBtn; }

}
