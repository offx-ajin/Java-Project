package controller;

import main.Main; // Assumes Main.java is in a 'main' package
import controller.AdminDashboardController; // Needs to be explicitly imported
import model.IssueModel;
import view.AdminDashboardView;
import view.LoginView;
import view.ResidentIssueView;

import javax.swing.*;

public class LoginController {

    private LoginView view;
    private String role;
    private ResidentIssueView issueView; // View helper for dialogs

    // Cache the dashboard instance
    private static AdminDashboardView dashboardInstance = null;

    public LoginController(LoginView view, String role) {
        this.view = view;
        this.role = role;
        // The issueView object provides a clean way to show dialogs (JOptionPane)
        this.issueView = new ResidentIssueView(view); 

        attachListeners();
    }

    private void attachListeners() {
        view.getExitBtn().addActionListener(e -> System.exit(0));

        if (role.equals("User")) {
            view.getUserLoginBtn().addActionListener(e -> handleUserLogin());
            view.getClearBtn().addActionListener(e -> clearUserFields());
        } else {
            view.getAdminLoginBtn().addActionListener(e -> handleAdminLogin());
        }
    }

    private void clearUserFields() {
        view.getNameField().setText("");
        view.getRoomField().setText("");
        view.getPhoneField().setText("");
    }

    private void handleUserLogin() {
        String name = view.getNameField().getText().trim();
        String room = view.getRoomField().getText().trim();
        String phone = view.getPhoneField().getText().trim();

        // 1. Validation
        if (name.isEmpty() || room.isEmpty() || phone.isEmpty()) {
            issueView.showError("Please fill all fields!");
            return;
        }
        if (!name.matches("[a-zA-Z ]+")) {
            issueView.showError("Name should contain only alphabets!");
            return;
        }
        if (!room.matches("\\d+") || !phone.matches("\\d+") || phone.length() != 10) {
            issueView.showError("Invalid Room or Phone number!");
            return;
        }

        issueView.showSuccess("Success", "Welcome, " + name + "!\nRoom: " + room);

        // 2. Proceed to issue reporting
        openIssuePage(name, room);
    }

    private void openIssuePage(String name, String room) {
        // Step 1: Select issue from a dialog (handled by ResidentIssueView)
        String issue = issueView.selectIssue();
        if (issue == null) return; // User cancelled

        // Step 2: Handle 'Others' description
        if (issue.equals("Others")) {
            issue = issueView.getOtherIssueDetails();
            if (issue == null || issue.trim().isEmpty()) {
                issueView.showError("Issue cannot be empty!");
                return;
            }
        }

        // Step 3: Confirmation dialog
        int confirm = issueView.showConfirmation();
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Step 4: Add issue to the Model
        IssueModel.addIssue(name, room, issue);

        issueView.showSuccess("Confirmation",
                "Issue submitted!\nResident: " + name + "\nRoom: " + room + "\nIssue: " + issue);

        // Step 5: Close login and return to role selector
        view.dispose();
        Main.showRoleSelector();
    }


    private void handleAdminLogin() {
        String reg = view.getRegField().getText().trim();
        String pass = new String(view.getPassField().getPassword());

        if (reg.isEmpty() || pass.isEmpty()) {
            issueView.showError("Please fill all fields!");
            return;
        }

        // Hardcoded password check
        if (pass.equals("1234!@#")) {
            issueView.showSuccess("Success",
                    "Admin Login Successful!\nRegister: " + reg);
            
            view.dispose(); // Close login window

            // Create or show the dashboard
            if (dashboardInstance == null) {
                dashboardInstance = new AdminDashboardView();
                // Attach the dashboard's controller
                new AdminDashboardController(dashboardInstance);
            }
            dashboardInstance.setVisible(true);
        } else {
            issueView.showError("Incorrect password!");
        }
    }
}