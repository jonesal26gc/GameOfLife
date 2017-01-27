import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Created by xm39 on 25/01/2017.
 */
public enum GameOfLifeRule {
    STANDARD( "B3/S23", "Original Game - Born 3, Survives 2/3", asList(2, 3), singletonList(3)),
    SIERPINSKI("B1/S12", "Sierpinski  - Born 1, Survives 1/2", asList(1, 2), singletonList(1)),
    HIGH_LIFE("B36/S23", "High-Life - Born 3/6, Survives 2/3", asList(2, 3), asList(3, 6));

    String name;
    String description;
    private final List<Integer> numberOfNeighboursRequiredToStayAlive;
    private final List<Integer> numberOfNeighboursRequiredToBeBorn;

    GameOfLifeRule(String name, String description,
                   List<Integer> numberOfNeighboursRequiredToStayAlive, List<Integer> numberOfNeighboursRequiredToBeBorn) {
        this.name = name;
        this.description = description;
        this.numberOfNeighboursRequiredToStayAlive = numberOfNeighboursRequiredToStayAlive;
        this.numberOfNeighboursRequiredToBeBorn = numberOfNeighboursRequiredToBeBorn;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static GameOfLifeRule lookUp(int rule) {
        for (GameOfLifeRule gameOfLifeRule : GameOfLifeRule.values()) {
            if (gameOfLifeRule.ordinal() == rule) {
                return gameOfLifeRule;
            }
        }
        throw new RuntimeException("Invalid GameOfLife rule " + rule + ".");
    }

    boolean isCellAliveAfterTick(int neighbourCount, boolean isAlive) {
        if (isAlive) {
            return numberOfNeighboursRequiredToStayAlive.contains(neighbourCount);
        }
        return numberOfNeighboursRequiredToBeBorn.contains(neighbourCount);
    }
}
