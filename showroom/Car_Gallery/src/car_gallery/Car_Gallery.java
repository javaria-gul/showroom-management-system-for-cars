
package car_gallery;

import gui.LoginPanel;
import javax.swing.JFrame;


public class Car_Gallery {

   
    public static void main(String[] args) {
         JFrame frame = new JFrame("Car Gallery Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setContentPane(new LoginPanel(frame));
        frame.setVisible(true);
    }
    
}
