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
        grid.activateSomeCellsRandomly(50);

        // Declare and open the frame.
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boolean gameComplete = false;
        String gameCompleteMessage = " - running";

        int i = 1;
        while (true) {
            i++;
            frame.setVisible(false);
            frame.getContentPane().removeAll();

            // if there were no changes, or no active cells, then terminate.
            boolean changesOccurred = grid.tickTableToReviseCells();

            // Declare a text area field.
            JTextArea textField = new JTextArea(40, 40);
            textField.append(grid.displayCellsInTable().toString());
            textField.setEditable(false);
            textField.setFont(new Font(FONT_OPTION[7], Font.BOLD, SIZE_OPTION[2]));

            // Declare a label field.
            String textMessage = grid.getActiveCellCount() +
                    " cells, originally " +
                    grid.getOriginalActiveCellCount() + " (..." +
                    grid.displayPreviousActiveCellCounts() +
                    ")";

            String topTextMessage = "Ticks: " + String.format("%1$d", i);
            if (gameComplete) {
                topTextMessage = topTextMessage.concat(gameCompleteMessage);
            }
            JLabel topLabelField = new JLabel(topTextMessage);
            topLabelField.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel bottomLabelField = new JLabel(textMessage);
            bottomLabelField.setHorizontalAlignment(SwingConstants.CENTER);

            frame.getContentPane().add(topLabelField, BorderLayout.NORTH);
            frame.getContentPane().add(textField, BorderLayout.CENTER);
            frame.getContentPane().add(bottomLabelField, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);

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
