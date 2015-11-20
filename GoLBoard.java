import java.awt.Point;

/**
 * Represents the board in the Game of Life.
 */
public class GoLBoard {

    // TODO: These should be changeable
    private int rows;
    private int columns;

    // The current and next generations of the board
    private int[][] currentGen;
    private int[][] nextGen;

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
        currentGen = new int[rows][columns];
        nextGen = new int[rows][columns];
        reset();
        toroidal = false;
    }

    /**
     * Resets the board to be all "dead" cells.
     */
    public void reset() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) 
                currentGen[i][j] = nextGen[i][j] = 0;
        }
        generation = 0;
        population = 0;
    }

    /**
     * Calculates the next generation of the game board.
     */
    public void nextGeneration() {
        nextGen = new int[rows][columns];   // Reset the next generation

        int sum;

        // Finite boards
        if (! toroidal) {
            // Check each cell
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    sum = 0;
                    //Check the surrounding cells
                    for (int p = i - 1; p <= i + 1; p++) {
                        if (p < 0 || p >= rows) continue;
                        for (int q = j - 1; q <= j + 1; q++) {
                            // Ignore the cell itself
                            if (p == i && q == j) continue;
                            if (q < 0 || q >= columns) continue;
                            sum += currentGen[p][q];
                        }
                    }
                    if (currentGen[i][j] == 0) {
                        if (sum == 3) {
                            nextGen[i][j] = 1;
                            population += 1;
                        }
                    }
                    else {
                        if (sum < 2 || sum > 3) {
                            nextGen[i][j] = 0;
                            population -= 1;
                        }
                        else nextGen[i][j] = 1;
                    }
                }
            }
        }
        currentGen = nextGen;
        generation++;
    }

    /**
     * Sets a cell to alive.
     * @param cell the cell to be brought to life
     */
    public void liveCell(Point cell) {
        int row = (int)cell.getX();
        int column = (int)cell.getY();
        if (currentGen[row][column] == 0) {
            population += 1;
            currentGen[row][column] = 1;
        }
    }

    /**
     * Sets a cell to dead.
     * @param cell the cell to be killed
     */
    public void killCell(Point cell) {
        int row = (int)cell.getX();
        int column = (int)cell.getY();
        if (currentGen[row][column] == 1) {
            population -= 1;
            currentGen[row][column] = 0;
        }
    }

    /**
     * Checks whether a cell is alive.
     * @param cell the cell to check
     * @return true if it is alive, false if it is dead
     */
    public boolean cellAlive(Point cell) {
        int row = (int)cell.getX();
        int column = (int)cell.getY();
        return (currentGen[row][column] == 1);
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
    public int[][] getCurrentGeneration() {
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
