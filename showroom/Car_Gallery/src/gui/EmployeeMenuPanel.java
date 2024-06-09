
package gui;



import javax.swing.*;
import java.awt.*;

public class EmployeeMenuPanel extends JPanel {
    public EmployeeMenuPanel(JFrame frame) {
        setLayout(new GridLayout(4, 1));

        JLabel welcomeLabel = new JLabel("Employee Dashboard");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        JButton updateCarsButton = new JButton("Update Cars");
        updateCarsButton.addActionListener(e -> {
            frame.setContentPane(new UpdateCarsPanel(frame));
            frame.revalidate();
        });
        add(updateCarsButton);

        JButton sortCarsButton = new JButton("Sort Cars");
        sortCarsButton.addActionListener(e -> {
            frame.setContentPane(new SortCarsPanel(frame));
            frame.revalidate();
        });
        add(sortCarsButton);

        JButton deleteCarsButton = new JButton("Delete Cars");
        deleteCarsButton.addActionListener(e -> {
            frame.setContentPane(new DeleteCarsPanel(frame));
            frame.revalidate();
        });
        add(deleteCarsButton);
    }
}

