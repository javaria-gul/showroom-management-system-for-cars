
package gui;

import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageEmployeesPanel extends JPanel {
    private DefaultListModel<String> employeeListModel;

    public ManageEmployeesPanel(JFrame frame) {
        setLayout(new BorderLayout());

        employeeListModel = new DefaultListModel<>();
        JList<String> employeeList = new JList<>(employeeListModel);
        add(new JScrollPane(employeeList), BorderLayout.CENTER);

        loadEmployeeData();

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            String selectedEmployee = employeeList.getSelectedValue();
            if (selectedEmployee != null) {
                // Handle update functionality
                JOptionPane.showMessageDialog(frame, "Update functionality not implemented yet", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an employee first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            String selectedEmployee = employeeList.getSelectedValue();
            if (selectedEmployee != null) {
                // Handle delete functionality
                deleteEmployee(selectedEmployee);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select an employee first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.setContentPane(new ManagerMenuPanel(frame));
            frame.revalidate();
        });
        add(backButton, BorderLayout.NORTH);
    }

    private void loadEmployeeData() {
        employeeListModel.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE role = 'employee'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String employeeDetails = resultSet.getInt("id") + ": " + resultSet.getString("first_name") + " " + resultSet.getString("last_name") + " - " + resultSet.getString("username");
                employeeListModel.addElement(employeeDetails);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteEmployee(String employeeDetails) {
        int employeeId = Integer.parseInt(employeeDetails.split(":")[0]);
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, employeeId);
            statement.executeUpdate();
            loadEmployeeData();
            JOptionPane.showMessageDialog(null, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
