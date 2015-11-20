import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import javax.swing.JComponent;

/**
 * This component displays a graphical representation of the Game of Life.
 */
public class BoardComponent extends JComponent {

    private int cellSize;
    private int gridSize;
    private GoLBoard game;

    public BoardComponent (int cS, int gS, GoLBoard g) {
        cellSize = cS;
        gridSize = gS;
        game = g;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        paintGrid(g2);
        paintCells(g2);
    }

    /**
     * Paints the live cells.
     * @param g2 the Graphics2D object cast in paintComponent
     */
    public void paintCells(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        int[][] board = game.getCurrentGeneration();
        Rectangle cell;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (board[i][j] == 1) {
                    cell = new Rectangle(i * cellSize + 1, j * cellSize + 1, 
                            cellSize - 1, cellSize - 1);
                    g2.fill(cell);
                }
            }
        }
    }


    /**
     * Draws the game grid.
     * @param g2 the Graphics2D object cast in paintComponent
     */
    public void paintGrid(Graphics2D g2) {
        g2.setColor(Color.BLACK);
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
