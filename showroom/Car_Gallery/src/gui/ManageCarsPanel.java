
package gui;



import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageCarsPanel extends JPanel {
    private DefaultListModel<String> carListModel;

    public ManageCarsPanel(JFrame frame) {
        setLayout(new BorderLayout());

        carListModel = new DefaultListModel<>();
        JList<String> carList = new JList<>(carListModel);
        add(new JScrollPane(carList), BorderLayout.CENTER);

        loadCarData();

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            String selectedCar = carList.getSelectedValue();
            if (selectedCar != null) {
                // Handle update functionality
                JOptionPane.showMessageDialog(frame, "Update functionality not implemented yet", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a car first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            String selectedCar = carList.getSelectedValue();
            if (selectedCar != null) {
                // Handle delete functionality
                deleteCar(selectedCar);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a car first!", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void loadCarData() {
        carListModel.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM cars";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String carDetails = resultSet.getInt("id") + ": " + resultSet.getString("make") + " " + resultSet.getString("model") + " (" + resultSet.getInt("year") + ") - $" + resultSet.getDouble("price");
                carListModel.addElement(carDetails);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteCar(String carDetails) {
        int carId = Integer.parseInt(carDetails.split(":")[0]);
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM cars WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, carId);
            statement.executeUpdate();
            loadCarData();
            JOptionPane.showMessageDialog(null, "Car deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
