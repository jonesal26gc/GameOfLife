import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameOfLifeTableShould {

    @Test
    public void
    initialise_the_grid() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();

        // then
        assertThat(grid.getActiveCellCount(), is(0));
    }

    @Test
    public void
    make_a_cell_alive() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // then
        assertThat(grid.getActiveCellCount(), is(1));
    }

    @Test
    public void
    make_a_cell_not_alive() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // when
        assertThat(grid.getActiveCellCount(), is(1));
        grid.deactivateCell(1, 1);

        // then
        assertThat(grid.getActiveCellCount(), is(0));
    }

    @Test
    public void
    count_the_neighbours() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(0, 2);
        grid.activateCell(2, 0);

        // then
        assertThat(grid.countNumberOfCellNeighbours(1, 1), is(2));
    }

    @Test
    public void
    activate_cell() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(2, 0);
        grid.activateCell(2, 1);
        grid.activateCell(2, 2);

        // then
        assertThat(grid.tickCellToDetermineAliveOrDead(1, 1), is(true));
    }

    @Test
    public void
    deactivate_cell_with_0_neighbours() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // then
        assertThat(grid.tickCellToDetermineAliveOrDead(1, 1), is(false));
    }


    @Test
    public void
    deactivate_cell_with_1_neighbour() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // when
        grid.activateCell(2, 1);

        // then
        assertThat(grid.tickCellToDetermineAliveOrDead(1, 1), is(false));
    }

    @Test
    public void
    keep_cell_with_2_neighbours() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // when
        grid.activateCell(2, 1);
        grid.activateCell(2, 2);

        // then
        assertThat(grid.tickCellToDetermineAliveOrDead(1, 1), is(true));
    }

    @Test
    public void
    keep_cell_with_3_neighbours() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // when
        grid.activateCell(0, 0);
        grid.activateCell(0, 1);
        grid.activateCell(0, 2);

        // then
        assertThat(grid.tickCellToDetermineAliveOrDead(1, 1), is(true));
    }

    @Test
    public void
    deactivate_cell_with_4_neighbours() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // when
        grid.activateCell(2, 0);
        grid.activateCell(2, 1);
        grid.activateCell(2, 2);
        grid.activateCell(1, 0);

        // then
        assertThat(grid.tickCellToDetermineAliveOrDead(1, 1), is(false));
    }

    @Test
    public void
    deactivate_cell_with_8_neighbours() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // when
        grid.activateCell(2, 0);
        grid.activateCell(2, 1);
        grid.activateCell(2, 2);
        grid.activateCell(0, 0);
        grid.activateCell(0, 1);
        grid.activateCell(0, 2);
        grid.activateCell(1, 0);
        grid.activateCell(1, 2);

        // then
        assertThat(grid.tickCellToDetermineAliveOrDead(1, 1), is(false));
    }

    @Test
    public void
    perform_tick_activities_for_all_cells_that_die() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(1, 1);

        // when
        GameOfLifeTable newGrid = new GameOfLifeTable();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (grid.tickCellToDetermineAliveOrDead(i, j)) {
                    newGrid.activateCell(i, j);
                }
            }
        }

        // then
        assertThat(newGrid.getActiveCellCount(), is(0));
    }


    @Test
    public void
    perform_tick_activities_for_all_cells_that_spawn() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        grid.activateCell(2, 0);
        grid.activateCell(2, 1);
        grid.activateCell(2, 2);

        // when
        System.out.println("Before:");
        System.out.println(grid.displayCellsInTable());
        grid.tickTableToReviseCells();
        System.out.println("First tick:");
        System.out.println(grid.displayCellsInTable());
        grid.tickTableToReviseCells();
        System.out.println("Second tick:");
        System.out.println(grid.displayCellsInTable());
        grid.tickTableToReviseCells();
        System.out.println("Third tick:");
        System.out.println(grid.displayCellsInTable());
        grid.tickTableToReviseCells();

        // then
        assertThat(grid.isCell(2, 1), is(true));
        assertThat(grid.getActiveCellCount(), is(3));
    }

    @Test
    public void
    display() {
        for (int i = 9500; i < 9800; i++) {
            System.out.println(i + "=" + Character.valueOf((char) i));
        }
    }

}
