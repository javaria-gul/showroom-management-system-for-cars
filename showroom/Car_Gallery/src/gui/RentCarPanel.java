
package gui;



import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RentCarPanel extends JPanel {
    public RentCarPanel(JFrame frame) {
        setLayout(new BorderLayout());

        DefaultListModel<String> carListModel = new DefaultListModel<>();
        JList<String> carList = new JList<>(carListModel);
        add(new JScrollPane(carList), BorderLayout.CENTER);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM cars WHERE type = 'rent' AND status = 'available'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String carDetails = resultSet.getString("make") + " " + resultSet.getString("model") + " (" + resultSet.getInt("year") + ") - $" + resultSet.getDouble("price");
                carListModel.addElement(carDetails);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JPanel buttonPanel = new JPanel();
        JButton rentNowButton = new JButton("Rent Now");
        rentNowButton.addActionListener(e -> {
            String selectedCar = carList.getSelectedValue();
            if (selectedCar != null) {
                // Handle rent now functionality
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a car first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(rentNowButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.setContentPane(new UserMenuPanel(frame));
            frame.revalidate();
        });
        add(backButton, BorderLayout.NORTH);
    }
}
