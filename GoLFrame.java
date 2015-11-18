import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * Defines the layout and event handling for the Game of Life frame.
 */
public class GoLFrame extends JFrame {
    // TODO: These should be changeable
    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 500;

    private static String FRAME_TITLE = "Game of Life Simulator";

    public GoLFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
    }
}
