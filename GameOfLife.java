import javax.swing.JFrame;

/**
 * A fully-featured Game of Life simulator.
 */
public class GameOfLife {
    public static void main(String[] args) {
        JFrame frame = new GoLFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
