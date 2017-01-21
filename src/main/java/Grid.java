/**
 * Created by tonyj on 21/01/2017.
 */
public class Grid {

    private boolean cell[][] = new boolean[10][10];
    private int numberOfCellsAlive;

    public Grid() {
    }

    public void setCellAlive(int x, int y) {
        if (!isCellAlive(x, y)) {
            cell[x][y] = true;
            numberOfCellsAlive++;
        }
    }

    public void resetCellAlive(int x, int y) {
        if (isCellAlive(x, y)) {
            cell[x][y] = false;
            numberOfCellsAlive--;
        }
    }

    public int getNumberOfCellsAlive() {
        return numberOfCellsAlive;
    }

    public boolean isCellAlive(int x, int y) {
        try {
            return cell[x][y];
        } catch (Exception ex) {
            return false;
        }
    }

    public int getNumberOfCellAliveNeighbours(int x, int y) {
        int numberOfCellAliveNeighbours = 0;

        if (isCellAlive(x - 1, y + 1)) {
            numberOfCellAliveNeighbours++;
        }
        if (isCellAlive(x, y + 1)) {
            numberOfCellAliveNeighbours++;
        }
        if (isCellAlive(x + 1, y + 1)) {
            numberOfCellAliveNeighbours++;
        }
        if (isCellAlive(x - 1, y)) {
            numberOfCellAliveNeighbours++;
        }
        if (isCellAlive(x + 1, y)) {
            numberOfCellAliveNeighbours++;
        }
        if (isCellAlive(x - 1, y - 1)) {
            numberOfCellAliveNeighbours++;
        }
        if (isCellAlive(x, y - 1)) {
            numberOfCellAliveNeighbours++;
        }
        if (isCellAlive(x + 1, y - 1)) {
            numberOfCellAliveNeighbours++;
        }

        return numberOfCellAliveNeighbours;
    }

    public boolean cellTickActivity(int x, int y) {

        if (isCellAlive(x, y)) {
            switch (getNumberOfCellAliveNeighbours(x, y)) {
                case 2:
                    return true;
                case 3:
                    return true;
                default:
                    return false;
            }
        } else {
            switch (getNumberOfCellAliveNeighbours(x, y)) {
                case 3:
                    return true;
                default:
                    return false;
            }
        }

    }

}
