package controller; // Simplified package

import model.IssueModel; // Corrected import
import view.AdminDashboardView; // Corrected import

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminDashboardController {

    private AdminDashboardView view;

    public AdminDashboardController(AdminDashboardView view) {
        this.view = view;
        attachListeners();
    }

    private void attachListeners() {
        view.getPendingTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable table = view.getPendingTable();
                int row = table.getSelectedRow();
                
                if (row >= 0 && e.getClickCount() == 1) { // Single-click to move
                    String issueID = table.getValueAt(row, 0).toString();
                    String issue = table.getValueAt(row, 3).toString();

                    int choice = JOptionPane.showConfirmDialog(
                            view,
                            "Move issue " + issueID + " (" + issue + ") to Resolved?",
                            "Mark as Resolved",
                            JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        // --- Model Interaction ---
                        IssueModel.resolveIssue(row);
                        // --- End Model Interaction ---
                    }
                }
            }
        });
    }
}