import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JComponent;

/**
 * This component displays a graphical representation of the Game of Life.
 */
public class BoardComponent extends JComponent {

    private int cellSize;
    private int gridSize;

    public BoardComponent (int c, int g) {
        cellSize = c;
        gridSize = g;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        // Draw the vertical lines first
        int x1 = 0;
        int y1 = 0;
        int x2 = x1;
        int y2 = cellSize * gridSize;
        Line2D.Double line;
        while (x1 <= cellSize * gridSize) {
            line = new Line2D.Double(x1, y1, x2, y2);
            g2.draw(line);
            x1 += cellSize;
            x2 += cellSize;
        }

        // Draw the horizontal lines
        x1 = y1 = y2 = 0;
        x2 = cellSize * gridSize;
        while (y1 <= cellSize * gridSize) {
            line = new Line2D.Double(x1, y1, x2, y2);
            g2.draw(line);
            y1 += cellSize;
            y2 += cellSize;
        }
    }
}
