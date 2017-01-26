import org.junit.Test;

/**
 * Created by tonyj on 21/01/2017.
 */
public class GameOfLifeShould {
    @Test
    public void
    display_the_screen() {
        GameOfLifeTable g = new GameOfLifeTable();
        System.out.println(g.displayCellsInTable());

    }

    @Test
    public void
    run_the_game() {
        try {
            GameOfLife.run(new GameOfLifeTable(20, 20, 10, GameOfLifeRule.STANDARD));
        } catch (Exception ex) {
        }

    }
}
