package view;

import javax.swing.*;
import java.awt.*;

public class ResidentIssueView {

    private Component parent;

    public ResidentIssueView(Component parent) {
        this.parent = parent;
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String title, String msg) {
        JOptionPane.showMessageDialog(parent, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public String selectIssue() {
        String[] issues = {"Water Scarcity", "Electricity Failure", "Elevator Broken",
                "Garbage Collection", "Pest Control", "Gas Connection Failure",
                "Health Problems", "Others"};

        return (String) JOptionPane.showInputDialog(
                parent, "Select your issue:", "Report Issue",
                JOptionPane.PLAIN_MESSAGE, null, issues, issues[0]);
    }

    public String getOtherIssueDetails() {
        return JOptionPane.showInputDialog(parent, "Describe your issue:");
    }

    public int showConfirmation() {
        return JOptionPane.showConfirmDialog(parent,
                "If this issue is important, please call admin at 000-123456.\nDo you want to submit?",
                "Important Issue Reminder",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
    }

}
