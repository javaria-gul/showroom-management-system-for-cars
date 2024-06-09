
package gui;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel(JFrame frame) {
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());

                    String query = "SELECT role FROM users WHERE username = ? AND password = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, password);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String role = resultSet.getString("role");
                        switch (role) {
                            case "user":
                                frame.setContentPane(new UserMenuPanel(frame));
                                break;
                            case "manager":
                                frame.setContentPane(new ManagerMenuPanel(frame));
                                break;
                            case "employee":
                                frame.setContentPane(new EmployeeMenuPanel(frame));
                                break;
                        }
                        frame.revalidate();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(loginButton);

        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new SignupPanel(frame));
                frame.revalidate();
            }
        });
        add(signupButton);
    }
}
