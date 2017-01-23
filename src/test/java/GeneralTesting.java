import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class GeneralTesting {

    @Test
    public void
    display() {
        for (int i = 9500; i < 9800; i++) {
            System.out.println(i + "=" + Character.valueOf((char) i));
        }
    }
}
