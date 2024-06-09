
package gui;



import javax.swing.*;
import java.awt.*;

public class ManagerMenuPanel extends JPanel {
    public ManagerMenuPanel(JFrame frame) {
        setLayout(new GridLayout(5, 1));

        JLabel welcomeLabel = new JLabel("Manager Dashboard");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        JButton manageCarsButton = new JButton("Manage Cars");
        manageCarsButton.addActionListener(e -> {
            frame.setContentPane(new ManageCarsPanel(frame));
            frame.revalidate();
        });
        add(manageCarsButton);

        JButton manageEmployeesButton = new JButton("Manage Employees");
        manageEmployeesButton.addActionListener(e -> {
            frame.setContentPane(new ManageEmployeesPanel(frame));
            frame.revalidate();
        });
        add(manageEmployeesButton);

        JButton approveListingsButton = new JButton("Approve Listings");
        approveListingsButton.addActionListener(e -> {
            frame.setContentPane(new ApproveListingsPanel(frame));
            frame.revalidate();
        });
        add(approveListingsButton);

        JButton updatePricingButton = new JButton("Update Pricing");
        updatePricingButton.addActionListener(e -> {
            frame.setContentPane(new UpdatePricingPanel(frame));
            frame.revalidate();
        });
        add(updatePricingButton);
    }
}
