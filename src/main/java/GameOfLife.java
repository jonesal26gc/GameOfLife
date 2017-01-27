import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class GameOfLife {

    private static final String[] FONT_OPTION = {"Serif", "Agency FB", "Arial", "Calibri", "Cambrian"
            , "Century Gothic", "Comic Sans MS", "Courier New"
            , "Forte", "Garamond", "Monospaced", "Segoe UI"
            , "Times New Roman", "Trebuchet MS", "Serif"};
    private static final int[] SIZE_OPTION = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28};
    private static final int WINDOW_DISPLAY_TIME_IN_MILLISECONDS_FAST = 250;
    private static final int WINDOW_DISPLAY_TIME_IN_MILLISECONDS_MEDIUM = 500;
    private static final int WINDOW_DISPLAY_TIME_IN_MILLISECONDS_SLOW = 1200;
    private static final String NEW_LINE = "\n";

    public static void main(String[] args) {
        try {
            run(constructGameFromArguments(args));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static GameOfLifeTable constructGameFromArguments(String[] args) {
        if (args.length == 3) {
            return randomGameWithDimensionsFromArguments(args);
        }
        if (args.length == 4) {
            return randomGameWithDimensionsAndPatternFromArguments(args);
        }
        return defaultGameWithPresetArguments();
    }

    private static GameOfLifeTable randomGameWithDimensionsAndPatternFromArguments(String[] args) {
        return new GameOfLifeTable(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]),
                GameOfLifeRule.lookUp(Integer.parseInt(args[3])));
    }

    private static GameOfLifeTable randomGameWithDimensionsFromArguments(String[] args) {
        return new GameOfLifeTable(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]));
    }

    private static GameOfLifeTable defaultGameWithPresetArguments() {
        //return new GameOfLifeTable(135, 67, 50, GameOfLifeRule.STANDARD);
        return new GameOfLifeTable(10, 10, 50, GameOfLifeRule.STANDARD);
    }

    public static void run(GameOfLifeTable grid) throws Exception {
        grid.activatePercentageOfCellsRandomly();

        // Declare and open the frame.
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        boolean gameComplete = false;
        String gameCompleteMessage = "";

        for (int tickCount = 1; true; tickCount++) {
            // clear the frame content.
            frame.getContentPane().removeAll();

            // Declare a text area field for the table.
            JTextArea textField = new JTextArea(grid.getTableSizeYAxis(), grid.getTableSizeXAxis());
            textField.append(grid.displayCellsInTable().toString());
            textField.setFont(new Font(FONT_OPTION[7], Font.BOLD, SIZE_OPTION[2]));
            textField.setEditable(false);

            String firstLineText = String.format("Rule:%s - Ticks: %04d", grid.getGameOfLifeRule().getName(), tickCount);
            if (gameComplete) {
                firstLineText = firstLineText.concat(gameCompleteMessage);
            }

            JLabel firstLabelField = new JLabel(firstLineText);
            firstLabelField.setHorizontalAlignment(SwingConstants.CENTER);

            // Declare a label field.
            String lastLineText = grid.getActiveCellCount() +
                    " cells, was " +
                    grid.getOriginalActiveCellCount() + " (....." +
                    grid.displayPreviousEightActiveCellCounts() +
                    ")";

            // declare the lower text label.
            JLabel lastLabelField = new JLabel(lastLineText);
            lastLabelField.setHorizontalAlignment(SwingConstants.CENTER);

            // add content to the frame and display the screen.
            frame.getContentPane().add(firstLabelField, BorderLayout.NORTH);
            frame.getContentPane().add(textField, BorderLayout.CENTER);
            frame.getContentPane().add(lastLabelField, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);

            if (tickCount == 1) {
                Thread.sleep(WINDOW_DISPLAY_TIME_IN_MILLISECONDS_SLOW);
            }

            // update the table, and be conscious of any changes occurring.
            boolean changesOccurred = grid.tickTableToReviseCells();

            // If we have finished or stabilised, then change characteristics of the screen.
            if ((!changesOccurred)
                    || grid.getActiveCellCount() == 0
                    || grid.isCellMovementStable()) {
                if (!gameComplete) {
                    gameCompleteMessage = " - Stabilised after " + tickCount + " ticks.";
                    gameComplete = true;
                }
            }
            Thread.sleep(determineSleepPeriodBetweenDisplays(grid, gameComplete));
        }
        //frame.dispose();
    }

    private static int determineSleepPeriodBetweenDisplays(GameOfLifeTable grid, boolean gameComplete) {
        if (gameComplete) {
            return WINDOW_DISPLAY_TIME_IN_MILLISECONDS_SLOW;
        }
        if (grid.getActiveCellCount() < 150) {
            return WINDOW_DISPLAY_TIME_IN_MILLISECONDS_MEDIUM;
        }
        return WINDOW_DISPLAY_TIME_IN_MILLISECONDS_FAST;
    }
}
