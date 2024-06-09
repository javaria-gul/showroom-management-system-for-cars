
package gui;

import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyCarPanel extends JPanel {
    public BuyCarPanel(JFrame frame) {
        setLayout(new BorderLayout());

        DefaultListModel<String> carListModel = new DefaultListModel<>();
        JList<String> carList = new JList<>(carListModel);
        add(new JScrollPane(carList), BorderLayout.CENTER);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM cars WHERE type = 'buy' AND status = 'available'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String carDetails = resultSet.getInt("id") + ": " + resultSet.getString("make") + " " + resultSet.getString("model") + " (" + resultSet.getInt("year") + ") - $" + resultSet.getDouble("price");
                carListModel.addElement(carDetails);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JPanel buttonPanel = new JPanel();
        JButton buyNowButton = new JButton("Buy Now");
        buyNowButton.addActionListener(e -> {
            String selectedCar = carList.getSelectedValue();
            if (selectedCar != null) {
                // Handle buy now functionality
                processTransaction(selectedCar, frame);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a car first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(buyNowButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.setContentPane(new UserMenuPanel(frame));
            frame.revalidate();
        });
        add(backButton, BorderLayout.NORTH);
    }

    private void processTransaction(String selectedCar, JFrame frame) {
        int carId = Integer.parseInt(selectedCar.split(":")[0]);
        String userId = "1"; // Assuming a logged-in user ID is available

        try (Connection connection = DatabaseConnection.getConnection()) {
            String updateCarStatusQuery = "UPDATE cars SET status = 'sold' WHERE id = ?";
            PreparedStatement updateCarStatusStmt = connection.prepareStatement(updateCarStatusQuery);
            updateCarStatusStmt.setInt(1, carId);
            updateCarStatusStmt.executeUpdate();

            String insertTransactionQuery = "INSERT INTO transactions (user_id, car_id, transaction_type) VALUES (?, ?, 'buy')";
            PreparedStatement insertTransactionStmt = connection.prepareStatement(insertTransactionQuery);
            insertTransactionStmt.setInt(1, Integer.parseInt(userId));
            insertTransactionStmt.setInt(2, carId);
            insertTransactionStmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Transaction completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
