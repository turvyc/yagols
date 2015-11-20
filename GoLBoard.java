/**
 * Represents the board in the Game of Life.
 */
public class GoLBoard {

    // TODO: These should be changeable
    private static final int ROWS = 100;
    private static final int COLUMNS = 100;
    private static final int CELL_SIZE = 10; 

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
    public GoLBoard() {
        currentGen = new boolean[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) 
                currentGen[i][j] = false;
        }
        toroidal = false;
        generation = 0;
        population = 0;
    }

    /**
     * Calculates the next generation of the game board.
     * @return the updated board
     */
    public boolean[][] nextGeneration() {
        currentGen = nextGen;                   // Set the next gen to the current gen
        nextGen = new boolean[ROWS][COLUMNS];   // Reset the next generation
        generation++;
        return currentGen;
    }

    /**
     * Toggles the value of a cell.
     * @param row The row of the cell
     * @param column The column of the cell
     */
    public void toggleCell(int row, int column) {
        assert row >= 0 && row < ROWS;
        assert column >= 0 && column < COLUMNS;
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
