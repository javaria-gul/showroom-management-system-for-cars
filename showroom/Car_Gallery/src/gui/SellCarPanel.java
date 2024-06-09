
package gui;



import database.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SellCarPanel extends JPanel {
    public SellCarPanel(JFrame frame) {
        setLayout(new GridLayout(8, 2));

        JTextField makeField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField mileageField = new JTextField();

        add(new JLabel("Make:"));
        add(makeField);
        add(new JLabel("Model:"));
        add(modelField);
        add(new JLabel("Year:"));
        add(yearField);
        add(new JLabel("Price:"));
        add(priceField);
        add(new JLabel("Mileage:"));
        add(mileageField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String make = makeField.getText();
                    String model = modelField.getText();
                    int year = Integer.parseInt(yearField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    int mileage = Integer.parseInt(mileageField.getText());

                    String query = "INSERT INTO cars (make, model, year, price, mileage, type, status) VALUES (?, ?, ?, ?, ?, 'sell', 'available')";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, make);
                    statement.setString(2, model);
                    statement.setInt(3, year);
                    statement.setDouble(4, price);
                    statement.setInt(5, mileage);
                    statement.executeUpdate();

                    // Handle sell car transaction
                    int carId = statement.getGeneratedKeys().getInt(1);
                    processTransaction(carId, frame);

                    JOptionPane.showMessageDialog(frame, "Car submitted for sale!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(submitButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.setContentPane(new UserMenuPanel(frame));
            frame.revalidate();
        });
        add(backButton);
    }

    private void processTransaction(int carId, JFrame frame) {
        String userId = "1"; // Assuming a logged-in user ID is available

        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertTransactionQuery = "INSERT INTO transactions (user_id, car_id, transaction_type) VALUES (?, ?, 'sell')";

