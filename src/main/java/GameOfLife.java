import javax.swing.*;
import java.awt.*;

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
            run();
        } catch (Exception ex) {
        }
    }

    public static void run() throws Exception {
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activatePercentageOfCellsRandomly(50);

        // Declare and open the frame.
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boolean gameComplete = false;
        String gameCompleteMessage = "";

        int i = 0;
        while (true) {
            i++;

            // update the table, and be conscious of any changes occurring.
            boolean changesOccurred = grid.tickTableToReviseCells();

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

            String topTextMessage = "Ticks: " + String.format("%04d", i);
            if (gameComplete) {
                topTextMessage = topTextMessage.concat(gameCompleteMessage);
            }
            JLabel topLabelField = new JLabel(topTextMessage);
            topLabelField.setHorizontalAlignment(SwingConstants.CENTER);

            // declate the lower text label.
            JLabel bottomLabelField = new JLabel(textMessage);
            bottomLabelField.setHorizontalAlignment(SwingConstants.CENTER);

            // add content to the frame and display the screen.
            frame.getContentPane().add(topLabelField, BorderLayout.NORTH);
            frame.getContentPane().add(textField, BorderLayout.CENTER);
            frame.getContentPane().add(bottomLabelField, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);

            // If we have finished or stabilised, then change characteristics of the screen.
            if ((!changesOccurred)
                    || grid.getActiveCellCount() == 0
                    || grid.isCellMovementStabilised()) {
                if (!gameComplete) {
                    gameCompleteMessage = " - Stabilised after " + i + " ticks.";
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
