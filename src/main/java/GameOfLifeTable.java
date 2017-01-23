import java.util.LinkedList;
import java.util.Queue;

public class GameOfLifeTable {

    private final static int TABLE_SIZE_X_AXIS_DEFAULT = 96;
    private final static int TABLE_SIZE_Y_AXIS_DEFAULT = 50;
    private final int tableSizeXAxis;
    private final int tableSizeYAxis;
    private boolean cell[][];

    private int originalActiveCellCount = 0;
    private int activeCellCount = 0;
    private Queue<Integer> previousActiveCellCounts = new LinkedList<Integer>();
    private static final int HISTORY_SIZE = 12;

    private static final char ON_CHARACTER = Character.valueOf((char) 9608);
    private static final char OFF_CHARACTER = Character.valueOf((char) 9590);
    private static final String NEW_LINE = "\n";

    public GameOfLifeTable() {
        this.tableSizeXAxis = TABLE_SIZE_X_AXIS_DEFAULT;
        this.tableSizeYAxis = TABLE_SIZE_Y_AXIS_DEFAULT;
        this.cell = new boolean[tableSizeXAxis][tableSizeYAxis];
    }

    public GameOfLifeTable(int x, int y) {
        this.tableSizeXAxis = x;
        this.tableSizeYAxis = y;
        this.cell = new boolean[tableSizeXAxis][tableSizeYAxis];
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

    public void setPreviousActiveCellCounts(Queue<Integer> previousActiveCellCounts) {
        this.previousActiveCellCounts = previousActiveCellCounts;
    }

    public void activateCell(int x, int y) {
        if (!isCell(x, y)) {
            cell[x][y] = true;
            activeCellCount++;
        }
    }

    public void deactivateCell(int x, int y) {
        if (isCell(x, y)) {
            cell[x][y] = false;
            activeCellCount--;
        }
    }

    public boolean isCell(int x, int y) {
        try {
            return cell[x][y];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    public boolean tickTableToReviseCells() {
        boolean changesOccurred = false;

        if (originalActiveCellCount == 0) {
            originalActiveCellCount = activeCellCount;
        }

        boolean[][] revisedCell = new boolean[tableSizeXAxis][tableSizeYAxis];
        int revisedNumberOfActiveCells = 0;

        for (int i = 0; i < tableSizeXAxis; i++) {
            for (int j = 0; j < tableSizeYAxis; j++) {
                if (this.tickCellToDetermineAliveOrDead(i, j)) {
                    revisedCell[i][j] = true;
                    revisedNumberOfActiveCells++;
                    if (revisedCell[i][j] != cell[i][j]) {
                        changesOccurred = true;
                    }
                }
            }
        }
        cell = revisedCell;
        activeCellCount = revisedNumberOfActiveCells;

        previousActiveCellCounts.add(activeCellCount);
        if (previousActiveCellCounts.size() > HISTORY_SIZE) {
            previousActiveCellCounts.poll();
        }

        return changesOccurred;
    }

    public boolean tickCellToDetermineAliveOrDead(int x, int y) {
        if (isCell(x, y)) {
            switch (countNeighbouringCells(x, y)) {
                case 2:
                    return true;
                case 3:
                    return true;
                default:
                    return false;
            }
        } else {
            switch (countNeighbouringCells(x, y)) {
                case 3:
                    return true;
                default:
                    return false;
            }
        }
    }

    public int countNeighbouringCells(int x, int y) {
        int neighbouringCellCount = 0;

        if (isCell(x - 1, y + 1)) {
            neighbouringCellCount++;
        }
        if (isCell(x, y + 1)) {
            neighbouringCellCount++;
        }
        if (isCell(x + 1, y + 1)) {
            neighbouringCellCount++;
        }
        if (isCell(x - 1, y)) {
            neighbouringCellCount++;
        }
        if (isCell(x + 1, y)) {
            neighbouringCellCount++;
        }
        if (isCell(x - 1, y - 1)) {
            neighbouringCellCount++;
        }
        if (isCell(x, y - 1)) {
            neighbouringCellCount++;
        }
        if (isCell(x + 1, y - 1)) {
            neighbouringCellCount++;
        }
        return neighbouringCellCount;
    }

    public StringBuffer displayCellsInTable() {
        StringBuffer consoleOutput = new StringBuffer();

        for (int y = (tableSizeYAxis - 1); y >= 0; y--) {
            consoleOutput.append(" ");
            for (int x = 0; x < tableSizeXAxis; x++) {
                if (isCell(x, y)) {
                    consoleOutput.append(ON_CHARACTER);
                } else {
                    consoleOutput.append(OFF_CHARACTER);
                }
                consoleOutput.append(" ");
            }
            if (y > 0) {
                consoleOutput.append(NEW_LINE);
            }
        }
        return consoleOutput;
    }

    public void activatePercentageOfCellsRandomly(int percentageToActivate) {
        int numberOfCellsToActivate = Math.round((tableSizeXAxis * tableSizeYAxis * percentageToActivate) / 100);
        while (getActiveCellCount() < numberOfCellsToActivate) {
            int x = (int) Math.round(Math.random() * (tableSizeXAxis - 1));
            int y = (int) Math.round(Math.random() * (tableSizeYAxis - 1));
            activateCell(x, y);
        }
    }

    public StringBuffer displayPreviousActiveCellCounts() {
        StringBuffer consoleOutput = new StringBuffer();
        int entryCount = 0;
        boolean commaRequired = false;
        for (int i : previousActiveCellCounts) {
            entryCount++;
            if (entryCount > (HISTORY_SIZE - 8)) {
                if (commaRequired) {
                    consoleOutput.append(',');
                }
                consoleOutput.append(String.format("%d", i));
                commaRequired = true;
            }
        }
        return consoleOutput;
    }

    public boolean isCellMovementStabilised() {
        if (previousActiveCellCounts.size() < HISTORY_SIZE) {
            return false;
        } else {

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
}
