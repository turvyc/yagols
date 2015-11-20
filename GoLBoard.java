import java.awt.Point;

/**
 * Represents the board in the Game of Life.
 */
public class GoLBoard {

    // TODO: These should be changeable
    private int rows;
    private int columns;

    // The current and next generations of the board
    private boolean[][] currentGen;
    private boolean[][] nextGen;

    // Are the top/bottom and left/right sides stitched together?
    private boolean toroidal;

    // The current generation
    private int generation;

    // The current number of living cells
    private int population;

    /**
     * Creates a new board populated with "dead" cells.
     */
    public GoLBoard(int r, int c) {
        rows = r;
        columns = c;
        currentGen = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) 
                currentGen[i][j] = false;
        }
        toroidal = false;
        generation = 0;
        population = 0;
    }

    /**
     * Calculates the next generation of the game board.
     */
    public void nextGeneration() {
        currentGen = nextGen;                   // Set the next gen to the current gen
        nextGen = new boolean[rows][columns];   // Reset the next generation
        generation++;
    }

    /**
     * Toggles the value of a cell.
     * @param row The row of the cell
     * @param column The column of the cell
     */
    public void toggleCell(Point cell) {
        int row = (int)cell.getX();
        int column = (int)cell.getY();
        assert row >= 0 && row < rows;
        assert column >= 0 && column < columns;
        if (currentGen[row][column]) {
            population -= 1;
            currentGen[row][column] = false;
        }
        else {
            population += 1;
            currentGen[row][column] = true;
        }
    }

    /**
     * Toggles whether the board has toroidal borders.
     */
    public void toggleToroidal() {
        toroidal = (toroidal) ? false : true;
    }

    /**
     * Returns the current state of the game.
     * @return the current generation of the game board
     */
    public boolean[][] getCurrentGeneration() {
        return currentGen;
    }

    /**
     * Returns the current population of the board.
     * @return the number of living cells
     */
    public int getPopulation() {
        assert population >= 0;
        return population;
    }

    /**
     * Returns the current generation of the board.
     * @return the current generation
     */
    public int getGeneration() {
        assert generation >= 0;
        return generation;
    }
}
