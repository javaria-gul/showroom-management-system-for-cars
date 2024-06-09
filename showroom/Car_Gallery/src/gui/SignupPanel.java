package gui;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupPanel extends JPanel {
    public SignupPanel(JFrame frame) {
        setLayout(new GridLayout(8, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Address:"));
        add(addressField);

        JCheckBox termsCheckBox = new JCheckBox("Accept Terms and Conditions");
        add(termsCheckBox);

        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!termsCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "You must accept the terms and conditions!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String email = emailField.getText();
                    String address = addressField.getText();

                    String query = "INSERT INTO users (username, password, role, first_name, last_name, email, address) VALUES (?, ?, 'user', ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    statement.setString(3, firstName);
                    statement.setString(4, lastName);
                    statement.setString(5, email);
                    statement.setString(6, address);
                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.setContentPane(new LoginPanel(frame));
                    frame.revalidate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(signupButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new LoginPanel(frame));
                frame.revalidate();
            }
        });
        add(backButton);
    }
}

