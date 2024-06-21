import javax.swing.*;
import java.io.IOException;
/**
 * @author Michael Tuskan & Matthew Kim
 * Group Number: 23
 * Date: 05/31/2023
 * Course: TCSS 445 A - Database System Design
 * Project: Final Project Submission
 */
/** Class: ForagerApplication -> Gui application driver */
public class ForagerApplication extends JFrame {

    /** Instance Variables for ForagerApplication Class */
    private ForagerGUI gui;

    /**
     * ForagerApplication - Constructor for Forager Application
     * @throws IOException - IOException
     */
    public ForagerApplication() throws IOException {
        gui = new ForagerGUI(null);
    }

    /**
     * runApplication - Run Forager Information System Application
     */
    public void runApplication() {
        setContentPane(gui.getMainJPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLocation(500, 200);
        setVisible(true);
        setTitle("Forager Information Systems");
    }

    /**
     * main - Main Method -> Program Driver
     * @param args - arguments
     * @throws IOException - IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Console Output: ");
        ForagerApplication foragerApp = new ForagerApplication();
        foragerApp.runApplication();
    }
}
