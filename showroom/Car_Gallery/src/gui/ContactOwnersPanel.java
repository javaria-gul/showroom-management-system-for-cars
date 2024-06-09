
package gui;



import javax.swing.*;
import java.awt.*;

public class ContactOwnersPanel extends JPanel {
    public ContactOwnersPanel(JFrame frame) {
        setLayout(new GridLayout(5, 1));

        add(new JLabel("Owner: Javaria Gul"));
        add(new JLabel("Email: javaria.gul@example.com"));
        add(new JLabel("Owner: Fatima Ilyas"));
        add(new JLabel("Email: fatima.ilyas@example.com"));
        add(new JLabel("Owner: Maria Khan"));
        add(new JLabel("Email: maria.khan@example.com"));

        JTextArea messageArea = new JTextArea(5, 20);
        add(new JScrollPane(messageArea));

        JButton sendMessageButton = new JButton("Send Message");
        sendMessageButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Message sent!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        add(sendMessageButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.setContentPane(new UserMenuPanel(frame));
            frame.revalidate();
        });
        add(backButton);
    }
}
