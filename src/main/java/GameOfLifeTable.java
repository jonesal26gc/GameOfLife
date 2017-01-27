import java.util.LinkedList;
import java.util.Queue;

public class GameOfLifeTable {
    private final int tableSizeXAxis;
    private final int tableSizeYAxis;
    private final int percentageToActivate;
    private final GameOfLifeRule gameOfLifeRule;
    private boolean cell[][];

    private int originalActiveCellCount = 0;
    private int activeCellCount = 0;
    private Queue<Integer> previousActiveCellCounts = new LinkedList<Integer>();
    private static final int HISTORY_SIZE = 12;

    private static final char ON_CHARACTER = 9608;
    private static final char OFF_CHARACTER = 9590;
    private static final String NEW_LINE = "\n";


    public GameOfLifeTable(int x, int y, int percentageToActivate, GameOfLifeRule gameOfLifeRule) {
        this.tableSizeXAxis = x;
        this.tableSizeYAxis = y;
        this.percentageToActivate = percentageToActivate;
        this.gameOfLifeRule = gameOfLifeRule;
        this.cell = new boolean[tableSizeXAxis][tableSizeYAxis];
    }

    public GameOfLifeTable(int x, int y, int percentageToActivate) {
        this(x, y, percentageToActivate, GameOfLifeRule.lookUp((int) Math.round(Math.random() * 2)));
    }

    public int getOriginalActiveCellCount() {
        return originalActiveCellCount;
    }

    public int getActiveCellCount() {
        return activeCellCount;
    }

    public int getTableSizeXAxis() {
        return this.tableSizeXAxis;
    }

    public int getTableSizeYAxis() {
        return this.tableSizeYAxis;
    }

    public static int getHistorySize() {
        return HISTORY_SIZE;
    }

    public GameOfLifeRule getGameOfLifeRule() {
        return gameOfLifeRule;
    }

    public void setPreviousActiveCellCounts(Queue<Integer> previousActiveCellCounts) {
        this.previousActiveCellCounts = previousActiveCellCounts;
    }

    public void activateCell(int x, int y) {
        if (!isAlive(x, y)) {
            cell[x][y] = true;
            activeCellCount++;
        }
    }

    public void deactivateCell(int x, int y) {
        if (isAlive(x, y)) {
            cell[x][y] = false;
            activeCellCount--;
        }
    }

    public boolean isAlive(int x, int y) {
        try {
            return cell[x][y];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    public boolean tickTableToReviseCells() {
        boolean changesOccurred = false;

        // set the the original count prior to the first tick.
        if (originalActiveCellCount == 0) {
            originalActiveCellCount = activeCellCount;
        }

        // Create new array for the updated details.
        boolean[][] revisedCell = new boolean[tableSizeXAxis][tableSizeYAxis];
        int revisedNumberOfActiveCells = 0;

        // Refresh the existing cells, posting the update to the new array.
        for (int i = 0; i < tableSizeXAxis; i++) {
            for (int j = 0; j < tableSizeYAxis; j++) {
                if (this.reviseCellToDetermineAliveOrDead(i, j)) {
                    revisedCell[i][j] = true;
                    revisedNumberOfActiveCells++;
                    if (revisedCell[i][j] != cell[i][j]) {
                        changesOccurred = true;
                    }
                }
            }
        }

        // Revise the previous array with the new values.
        cell = revisedCell;
        activeCellCount = revisedNumberOfActiveCells;

        // Record the number of active cells in the history queue.
        previousActiveCellCounts.add(activeCellCount);
        if (previousActiveCellCounts.size() > HISTORY_SIZE) {
            previousActiveCellCounts.poll();
        }

        // Indicate whether any changes were encountered.
        return changesOccurred;
    }

    public boolean reviseCellToDetermineAliveOrDead(int x, int y) {
        return gameOfLifeRule.isCellAliveAfterTick(countNeighbouringCells(x, y), isAlive(x, y));
    }

    public int countNeighbouringCells(int x, int y) {
        int neighbouringCellCount = 0;

        if (isAlive(x - 1, y + 1)) {
            neighbouringCellCount++;
        }
        if (isAlive(x, y + 1)) {
            neighbouringCellCount++;
        }
        if (isAlive(x + 1, y + 1)) {
            neighbouringCellCount++;
        }
        if (isAlive(x - 1, y)) {
            neighbouringCellCount++;
        }
        if (isAlive(x + 1, y)) {
            neighbouringCellCount++;
        }
        if (isAlive(x - 1, y - 1)) {
            neighbouringCellCount++;
        }
        if (isAlive(x, y - 1)) {
            neighbouringCellCount++;
        }
        if (isAlive(x + 1, y - 1)) {
            neighbouringCellCount++;
        }
        return neighbouringCellCount;
    }

    public StringBuffer displayCellsInTable() {
        StringBuffer consoleOutput = new StringBuffer();

        for (int y = (tableSizeYAxis - 1); y >= 0; y--) {
            consoleOutput.append(" ");
            for (int x = 0; x < tableSizeXAxis; x++) {
                consoleOutput.append(chooseCharacterForCellDisplay(y, x)).append(" ");
            }
            if (y > 0) {
                consoleOutput.append(NEW_LINE);
            }
        }
        return consoleOutput;
    }

    private char chooseCharacterForCellDisplay(int y, int x) {
        if (isAlive(x, y)) {
            return ON_CHARACTER;
        }
        return OFF_CHARACTER;
    }

    public void activatePercentageOfCellsRandomly() {
        int numberOfCellsToActivate = Math.round((tableSizeXAxis * tableSizeYAxis * percentageToActivate) / 100);
        while (getActiveCellCount() < numberOfCellsToActivate) {
            activateCell((int) Math.round(Math.random() * (tableSizeXAxis - 1)),
                    (int) Math.round(Math.random() * (tableSizeYAxis - 1)));
        }
    }

    public StringBuffer displayPreviousEightActiveCellCounts() {
        StringBuffer consoleOutput = new StringBuffer();
        int arrayIndex = 0;
        boolean commaRequired = false;
        for (int activeCellCount : previousActiveCellCounts) {
            arrayIndex++;
            if (arrayIndex > (HISTORY_SIZE - 8)) {
                if (commaRequired) {
                    consoleOutput.append(',');
                }
                consoleOutput.append(String.format("%d", activeCellCount));
                commaRequired = true;
            }
        }
        return consoleOutput;
    }

    public boolean isCellMovementStable() {
        if (previousActiveCellCounts.size() < HISTORY_SIZE) {
            return false;
        }
        // transfer the queue contents into a contiguous string of active cell counts.
        StringBuilder previousActiveCellCountsInFixedWidthStringFormat = new StringBuilder();
        for (Integer activeCellCount : previousActiveCellCounts) {
            previousActiveCellCountsInFixedWidthStringFormat.append(String.format("%04d", activeCellCount));
        }
        // check for repeating patterns of varying lengths.
        for (int activeCellCountsInPattern = 1; activeCellCountsInPattern < 5; activeCellCountsInPattern++) {
            int lengthOfPattern = (activeCellCountsInPattern * 4);
            String pattern = previousActiveCellCountsInFixedWidthStringFormat.substring(0, lengthOfPattern);

            int startColumn = 0;
            int numberOfPatternRepetitions = 0;
            while (true) {
                int indexOfPattern = previousActiveCellCountsInFixedWidthStringFormat.indexOf(pattern, startColumn);
                if (indexOfPattern != startColumn) {
                    break;
                }
                numberOfPatternRepetitions++;
                startColumn = indexOfPattern + lengthOfPattern;
            }

            if (numberOfPatternRepetitions == previousActiveCellCountsInFixedWidthStringFormat.length() / pattern.length()) {
                //System.out.println("There are " + numberOfPatternRepetitions + " repetitions of '" + pattern + "'");
                return true;
            }
        }
        return false;
    }
}
