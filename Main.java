package main; 

import controller.LoginController;
import model.IssueModel; 
import view.LoginView;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
       
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
            
   
            new LoginController(loginView, role);
            
            loginView.setVisible(true);
        } else {
            System.exit(0);
        }
    }
}

