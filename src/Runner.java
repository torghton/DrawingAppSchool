import javax.swing.*;
import java.awt.*;

public class Runner {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        Screen table = new Screen(new Dimension(800, 800), frame);

        // Adds the panels to the frame
        frame.add(table);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        // Sets frame settings
        frame.setTitle("Gimp 0.5");
        frame.setLocation(100, 100);
        frame.setVisible(true);
        frame.setResizable(false);

        frame.setSize(800,800);

        table.gameLoop(10);

    }
}