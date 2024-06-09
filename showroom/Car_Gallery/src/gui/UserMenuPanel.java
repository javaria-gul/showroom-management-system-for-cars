package gui;

import javax.swing.*;
import java.awt.*;

public class UserMenuPanel extends JPanel {
    public UserMenuPanel(JFrame frame) {
        setLayout(new GridLayout(5, 1));

        JLabel welcomeLabel = new JLabel("Welcome to the Car Gallery!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        Timer timer = new Timer(2000, e -> {
            remove(welcomeLabel);
            revalidate();
            repaint();
        });
        timer.setRepeats(false);
        timer.start();

        JButton buyCarButton = new JButton("Buy Cars");
        buyCarButton.addActionListener(e -> {
            frame.setContentPane(new BuyCarPanel(frame));
            frame.revalidate();
        });
        add(buyCarButton);

        JButton sellCarButton = new JButton("Sell Cars");
        sellCarButton.addActionListener(e -> {
            frame.setContentPane(new SellCarPanel(frame));
            frame.revalidate();
        });
        add(sellCarButton);

        JButton rentCarButton = new JButton("Rent Cars");
        rentCarButton.addActionListener(e -> {
            frame.setContentPane(new RentCarPanel(frame));
            frame.revalidate();
        });
        add(rentCarButton);

        JButton contactOwnersButton = new JButton("Contact Owners");
        contactOwnersButton.addActionListener(e -> {
            frame.setContentPane(new ContactOwnersPanel(frame));
            frame.revalidate();
        });
        add(contactOwnersButton);
    }
}
