import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class testing {

    @Test
    public void
    test_for_repeating_pattern() {

        // create a queue with 24 numbers.
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < 24; i++) {
            queue.add(1);
        }

        // transfer the queue contents into a contiguous string of active cell counts.
        StringBuilder previousActiveCellCountsInFixedWidthStringFormat = new StringBuilder();
        for (Integer activeCellCount : queue) {
            previousActiveCellCountsInFixedWidthStringFormat.append(String.format("%04d", activeCellCount));
        }

        // check for repeating patterns of varying lengths.
        for (int activeCellCountsInPattern = 1; activeCellCountsInPattern < 5; activeCellCountsInPattern++) {
            int lengthOfPattern = (activeCellCountsInPattern * 4);
            String pattern = previousActiveCellCountsInFixedWidthStringFormat.substring(0, lengthOfPattern);

            int startColumn = 0;
            int numberOfPatternRepetitions = 0;
            while (true) {
                int indexOfPattern = previousActiveCellCountsInFixedWidthStringFormat.indexOf(pattern,startColumn);
                if (indexOfPattern < 0) {
                    break;
                } else {
                    numberOfPatternRepetitions++;
                    startColumn = indexOfPattern + lengthOfPattern;
                }
            }
            System.out.println("There are " + numberOfPatternRepetitions + " repetitions of '" + pattern + "'");
            if (numberOfPatternRepetitions == previousActiveCellCountsInFixedWidthStringFormat.length() / pattern.length()) {
                System.out.println("success!");
                //return true;
            }
        }
        //return false;
    }
}
