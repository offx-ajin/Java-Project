package main; 

import controller.LoginController;
import model.IssueModel; // Import the Model
import view.LoginView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Force the IssueModel to load the JDBC driver and initial data
        IssueModel.loadIssuesFromDatabase(); 
        
        SwingUtilities.invokeLater(Main::showRoleSelector);
    }

    public static void showRoleSelector() {
        String[] roles = {"User", "Admin"};
        String role = (String) JOptionPane.showInputDialog(
                null, "Select your role:", "Role Selection",
                JOptionPane.PLAIN_MESSAGE, null, roles, roles[0]);

        if (role != null) {
            LoginView loginView = new LoginView(role);
            
            // Pass the loginView to the controller
            new LoginController(loginView, role);
            
            loginView.setVisible(true);
        } else {
            System.exit(0);
        }
    }
}
