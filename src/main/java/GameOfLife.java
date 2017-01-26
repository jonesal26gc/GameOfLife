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

    public static void main(String[] args) {
        try {
            run(constructGameFrom(args));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static GameOfLifeTable constructGameFrom(String[] args) {
        if (args.length == 3) {
            return randomGameWithDimensionFrom(args);
        }
        if (args.length == 4) {
            return randomGameWithDimensionsAndPatternFrom(args);
        }
        return defaultGame();
    }

    private static GameOfLifeTable defaultGame() {
        return new GameOfLifeTable(135, 67, 50, GameOfLifeRule.STANDARD);
    }

    private static GameOfLifeTable randomGameWithDimensionsAndPatternFrom(String[] args) {
        return new GameOfLifeTable(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]),
                GameOfLifeRule.lookUp(Integer.parseInt(args[3])));
    }

    private static GameOfLifeTable randomGameWithDimensionFrom(String[] args) {
        return new GameOfLifeTable(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]),
                Integer.parseInt(args[2]));
    }

    public static void run(GameOfLifeTable grid) throws Exception {
        grid.activatePercentageOfCellsRandomly();

        // Declare and open the frame.
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        boolean gameComplete = false;
        String gameCompleteMessage = "";

        for (int generation = 1; true; generation++) {
            // clear the frame content.
            frame.getContentPane().removeAll();
            //frame.setVisible(false);

            // Declare a text area field for the table.
            JTextArea textField = new JTextArea(grid.getTableSizeYAxis(), grid.getTableSizeXAxis());
            textField.append(grid.displayCellsInTable().toString());
            textField.setFont(new Font(FONT_OPTION[7], Font.BOLD, SIZE_OPTION[2]));
            textField.setEditable(false);

            // Declare a label field.
            String textMessage = grid.getActiveCellCount() +
                    " cells, was " +
                    grid.getOriginalActiveCellCount() + " (....." +
                    grid.displayPreviousActiveCellCounts() +
                    ")";

            String topTextMessage = String.format("Rule:%s - Ticks: %04d", grid.getGameOfLifeRule().getRuleName(), generation);
            if (gameComplete) {
                topTextMessage = topTextMessage.concat(gameCompleteMessage);
            }
            JLabel topLabelField = new JLabel(topTextMessage);
            topLabelField.setHorizontalAlignment(SwingConstants.CENTER);

            // declare the lower text label.
            JLabel bottomLabelField = new JLabel(textMessage);
            bottomLabelField.setHorizontalAlignment(SwingConstants.CENTER);

            // add content to the frame and display the screen.
            frame.getContentPane().add(topLabelField, BorderLayout.NORTH);
            frame.getContentPane().add(textField, BorderLayout.CENTER);
            frame.getContentPane().add(bottomLabelField, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);

            if (generation == 1) {
                Thread.sleep(WINDOW_DISPLAY_TIME_IN_MILLISECONDS_SLOW);
            }

            // update the table, and be conscious of any changes occurring.
            boolean changesOccurred = grid.tickTableToReviseCells();

            // If we have finished or stabilised, then change characteristics of the screen.
            if ((!changesOccurred)
                    || grid.getActiveCellCount() == 0
                    || grid.isCellMovementStabilised()) {
                if (!gameComplete) {
                    gameCompleteMessage = " - Stabilised after " + generation + " ticks.";
                    gameComplete = true;
                }
            }

            if (gameComplete) {
                Thread.sleep(WINDOW_DISPLAY_TIME_IN_MILLISECONDS_SLOW);
            } else {
                if (grid.getActiveCellCount() > 150) {
                    Thread.sleep(WINDOW_DISPLAY_TIME_IN_MILLISECONDS_FAST);
                } else {
                    Thread.sleep(WINDOW_DISPLAY_TIME_IN_MILLISECONDS_MEDIUM);
                }
            }
        }
        //frame.dispose();
    }
}
