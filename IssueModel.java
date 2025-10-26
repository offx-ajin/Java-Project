package model;

import javax.swing.JOptionPane; // <-- FIX: Import JOptionPane
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class IssueModel {

    // --- JDBC Connection Details (for XAMPP/MySQL) ---
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/apartment_issues?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 
    
    // In-memory models are now populated by the database
    private static DefaultTableModel pendingModel = new DefaultTableModel(
            new Object[]{"Issue ID", "Resident", "Room", "Issue"}, 0);
    private static DefaultTableModel resolvedModel = new DefaultTableModel(
            new Object[]{"Issue ID", "Resident", "Room", "Issue"}, 0);

    // Initial counter, only used if the table is empty
    private static int issueCounter = 1;

    // Static initializer block to load the driver when the class is loaded
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            loadIssuesFromDatabase(); // Load data immediately
        } catch (ClassNotFoundException e) {
            // This line uses JOptionPane, which was not imported!
            JOptionPane.showMessageDialog(null, "MySQL JDBC Driver not found!", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Private Helper Method to Connect ---

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // --- Private Data Loading Method (READ) ---

    public static void loadIssuesFromDatabase() {
        pendingModel.setRowCount(0); 
        resolvedModel.setRowCount(0);
        issueCounter = 1;

        String sql = "SELECT issue_id, resident_name, room_number, issue_description, status FROM issues ORDER BY id ASC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String issueID = rs.getString("issue_id");
                String name = rs.getString("resident_name");
                String room = rs.getString("room_number");
                String issueDesc = rs.getString("issue_description");
                String status = rs.getString("status");

                Object[] row = {issueID, name, room, issueDesc};

                if (status.equalsIgnoreCase("Pending")) {
                    pendingModel.addRow(row);
                } else {
                    resolvedModel.addRow(row);
                }
                
                try {
                    int idNum = Integer.parseInt(issueID.substring(1)); 
                    if (idNum >= issueCounter) {
                        issueCounter = idNum + 1;
                    }
                } catch (NumberFormatException ignored) {}
            }

        } catch (SQLException e) {
            // This line caused the error!
            JOptionPane.showMessageDialog(null, "Error loading data from database: " + e.getMessage(), "Database Load Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // --- Data Model Getters and Business Logic (addIssue, resolveIssue) are unchanged ---

    public static DefaultTableModel getPendingModel() {
        return pendingModel;
    }

    public static DefaultTableModel getResolvedModel() {
        return resolvedModel;
    }

    public static String addIssue(String name, String room, String issue) {
        String issueID = String.format("P%03d", issueCounter++);
        String sql = "INSERT INTO issues (issue_id, resident_name, room_number, issue_description, status) VALUES (?, ?, ?, ?, 'Pending')";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, issueID);
            pstmt.setString(2, name);
            pstmt.setString(3, room);
            pstmt.setString(4, issue);
            pstmt.executeUpdate();

            pendingModel.addRow(new Object[]{issueID, name, room, issue});
            return issueID;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error submitting issue to database: " + e.getMessage(), "Database Submit Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public static void resolveIssue(int pendingRowIndex) {
        if (pendingRowIndex >= 0 && pendingRowIndex < pendingModel.getRowCount()) {
            String issueID = pendingModel.getValueAt(pendingRowIndex, 0).toString();
            String sql = "UPDATE issues SET status = 'Resolved' WHERE issue_id = ?";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, issueID);
                pstmt.executeUpdate();

                Object[] rowData = new Object[4];
                for (int i = 0; i < 4; i++) {
                    rowData[i] = pendingModel.getValueAt(pendingRowIndex, i);
                }
                resolvedModel.addRow(rowData);
                pendingModel.removeRow(pendingRowIndex);

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error resolving issue in database: " + e.getMessage(), "Database Update Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}