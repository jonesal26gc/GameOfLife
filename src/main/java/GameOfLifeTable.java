import java.util.LinkedList;
import java.util.Queue;

public class GameOfLifeTable {

    private static final int TABLE_SIZE = 40;
    private boolean cell[][] = new boolean[TABLE_SIZE][TABLE_SIZE];

    private int originalActiveCellCount = 0;
    private int activeCellCount = 0;
    private Queue<Integer> previousActiveCellCounts = new LinkedList<Integer>();
    private static final int HISTORY_SIZE = 12;

    private static final char ON_CHARACTER = Character.valueOf((char) 9608);
    private static final char OFF_CHARACTER = Character.valueOf((char) 9590);
    private static final String NEW_LINE = "\n";

    public GameOfLifeTable() {
    }

    public int getOriginalActiveCellCount() {
        return originalActiveCellCount;
    }

    public int getActiveCellCount() {
        return activeCellCount;
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
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean tickTableToReviseCells() {
        boolean[][] revisedCell = new boolean[TABLE_SIZE][TABLE_SIZE];
        int revisedNumberOfActiveCells = 0;
        boolean changesOccurred = false;

        if (originalActiveCellCount == 0) {
            originalActiveCellCount = activeCellCount;
        }

        for (int i = 0; i < TABLE_SIZE; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
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
            switch (countNumberOfCellNeighbours(x, y)) {
                case 2:
                    return true;
                case 3:
                    return true;
                default:
                    return false;
            }
        } else {
            switch (countNumberOfCellNeighbours(x, y)) {
                case 3:
                    return true;
                default:
                    return false;
            }
        }
    }

    public int countNumberOfCellNeighbours(int x, int y) {
        int numberOfCellNeighbours = 0;

        if (isCell(x - 1, y + 1)) {
            numberOfCellNeighbours++;
        }
        if (isCell(x, y + 1)) {
            numberOfCellNeighbours++;
        }
        if (isCell(x + 1, y + 1)) {
            numberOfCellNeighbours++;
        }
        if (isCell(x - 1, y)) {
            numberOfCellNeighbours++;
        }
        if (isCell(x + 1, y)) {
            numberOfCellNeighbours++;
        }
        if (isCell(x - 1, y - 1)) {
            numberOfCellNeighbours++;
        }
        if (isCell(x, y - 1)) {
            numberOfCellNeighbours++;
        }
        if (isCell(x + 1, y - 1)) {
            numberOfCellNeighbours++;
        }
        return numberOfCellNeighbours;
    }

    public StringBuffer displayCellsInTable() {
        StringBuffer consoleOutput = new StringBuffer();

        for (int y = (TABLE_SIZE - 1); y >= 0; y--) {
            consoleOutput.append(" ");
            for (int x = 0; x < TABLE_SIZE; x++) {
                if (isCell(x, y)) {
                    consoleOutput.append(ON_CHARACTER + " ");
                } else {
                    consoleOutput.append(OFF_CHARACTER + " ");
                }
            }
            if (y > 0) {
                consoleOutput.append(NEW_LINE);
            }
        }
        return consoleOutput;
    }

    public void activateSomeCellsRandomly(int percentageToActivate) {
        for (int i = 0; i < ((TABLE_SIZE * TABLE_SIZE * percentageToActivate)/100); i++) {
            int x = (int) Math.round(Math.random() * (TABLE_SIZE - 1));
            int y = (int) Math.round(Math.random() * (TABLE_SIZE - 1));
            activateCell(x, y);
        }
    }

    public StringBuffer displayPreviousActiveCellCounts() {
        StringBuffer consoleOutput = new StringBuffer();
        boolean commaRequired = false;
        for (int i : previousActiveCellCounts) {
            if (commaRequired) {
                consoleOutput.append(',');
            }
            consoleOutput.append(String.format("%d", i));
            commaRequired = true;
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
                    System.out.println("There are " + numberOfPatternRepetitions + " repetitions of '" + pattern + "'");
                    return true;
                }
            }
            return false;
        }
    }
}
