import org.junit.Test;
import org.mockito.Mock;

import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GameOfLifeTableShould {

    @Mock
    GameOfLifeTable MockTheGameOfLifeTable;

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
        assertThat(grid.countNeighbouringCells(1, 1), is(2));
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
    randomly_activate_a_proportion_of_cells() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();
        int percentageToActivate = 50;
        grid.activatePercentageOfCellsRandomly(percentageToActivate);

        // then
        assertThat(grid.getActiveCellCount()
                , is(((GameOfLifeTable.getTableSizeXAxis()
                        * GameOfLifeTable.getTableSizeYAxis()
                        * percentageToActivate)
                ) / 100));
    }

    @Test
    public void
    check_for_repeating_pattern_signifying_stability() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();

        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < GameOfLifeTable.getHistorySize(); i++) {
            queue.add(1);
        }
        grid.setPreviousActiveCellCounts(queue);

        //then
        assertTrue(grid.isCellMovementStabilised());
    }

    @Test
    public void
    display_active_cell_history() {
        // given
        GameOfLifeTable grid = new GameOfLifeTable();

        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < GameOfLifeTable.getHistorySize(); i++) {
            queue.add(1);
        }
        grid.setPreviousActiveCellCounts(queue);

        // then
        assertEquals(grid.displayPreviousActiveCellCounts().toString(),("1,1,1,1,1,1,1,1,1,1,1,1"));
    }
}
