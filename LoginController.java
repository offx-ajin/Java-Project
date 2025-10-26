package controller;

import main.Main; 
import controller.AdminDashboardController; 
import model.IssueModel;
import view.AdminDashboardView;
import view.LoginView;
import view.ResidentIssueView;

import javax.swing.*;

public class LoginController {

    private LoginView view;
    private String role;
    private ResidentIssueView issueView; 

    
    private static AdminDashboardView dashboardInstance = null;

    public LoginController(LoginView view, String role) {
        this.view = view;
        this.role = role;
        
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

        
        openIssuePage(name, room);
    }

    private void openIssuePage(String name, String room) {
        
        String issue = issueView.selectIssue();
        if (issue == null) return; 

        
        if (issue.equals("Others")) {
            issue = issueView.getOtherIssueDetails();
            if (issue == null || issue.trim().isEmpty()) {
                issueView.showError("Issue cannot be empty!");
                return;
            }
        }

        
        int confirm = issueView.showConfirmation();
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        
        IssueModel.addIssue(name, room, issue);

        issueView.showSuccess("Confirmation",
                "Issue submitted!\nResident: " + name + "\nRoom: " + room + "\nIssue: " + issue);

        
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

        
        if (pass.equals("1234!@#")) {
            issueView.showSuccess("Success",
                    "Admin Login Successful!\nRegister: " + reg);
            
            view.dispose(); 

            
            if (dashboardInstance == null) {
                dashboardInstance = new AdminDashboardView();
                
                new AdminDashboardController(dashboardInstance);
            }
            dashboardInstance.setVisible(true);
        } else {
            issueView.showError("Incorrect password!");
        }
    }

}
