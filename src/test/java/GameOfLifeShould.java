import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by tonyj on 21/01/2017.
 */
public class GameOfLifeShould {

    @Test
    public void
    initialise_the_grid() {

        Grid grid = new Grid();
        assertThat(grid.getNumberOfCellsAlive(), is(0));
    }

    @Test
    public void
    make_a_cell_alive() {
        Grid grid = new Grid();
        grid.setCellAlive(1, 1);
        assertThat(grid.getNumberOfCellsAlive(), is(1));
    }

    @Test
    public void
    make_a_cell_not_alive() {
        Grid grid = new Grid();
        grid.setCellAlive(1, 1);
        grid.resetCellAlive(1,1);
        assertThat(grid.getNumberOfCellsAlive(), is(0));
    }

    @Test
    public  void
    count_the_neighbours() {
        // given
        Grid grid = new Grid();

        // when
        grid.setCellAlive(1, 1);
        grid.setCellAlive(2, 2);

        // then
        assertThat(grid.getNumberOfCellAliveNeighbours(0,0),is(1));



    }

}
