package view; 

import model.IssueModel;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardView extends JFrame {

    private JTable pendingTable;
    private JTable resolvedTable;

    public AdminDashboardView() {
        setTitle("Admin Dashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 

        JPanel panel = new JPanel(new BorderLayout());
        add(panel);

        JLabel title = new JLabel("Apartment Issue Dashboard", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        IssueModel.loadIssuesFromDatabase(); 
        
        pendingTable = new JTable(IssueModel.getPendingModel());
        JScrollPane pendingScroll = new JScrollPane(pendingTable);
        pendingScroll.setBorder(BorderFactory.createTitledBorder("Pending Issues"));

        resolvedTable = new JTable(IssueModel.getResolvedModel());
        JScrollPane resolvedScroll = new JScrollPane(resolvedTable);
        resolvedScroll.setBorder(BorderFactory.createTitledBorder("Resolved Issues"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pendingScroll, resolvedScroll);
        splitPane.setDividerLocation(250);
        panel.add(splitPane, BorderLayout.CENTER);
    }


    public JTable getPendingTable() {
        return pendingTable;
    }

}
