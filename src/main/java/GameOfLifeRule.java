import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Created by xm39 on 25/01/2017.
 */
public enum GameOfLifeRule {
    STANDARD(1, "B3/S23", "Original Game - Born 3, Survives 2/3", asList(2, 3), singletonList(3)),
    SIERPINSKI(2, "B1/S12", "Sierpinski  - Born 1, Survives 1/2", asList(1, 2), singletonList(1)),
    HIGH_LIFE(3, "B36/S23", "High-Life - Born 3/6, Survives 2/3", asList(2, 3), asList(3, 6));

    int rule;
    String ruleName;
    String description;
    private final List<Integer> numberOfNeighboursToStayAlive;
    private final List<Integer> numberOfNeighboursToBeBorn;

    GameOfLifeRule(int rule, String ruleName, String description,
                   List<Integer> numberOfNeighboursToStayAlive, List<Integer> numberOfNeighboursToBeBorn) {
        this.rule = rule;
        this.ruleName = ruleName;
        this.description = description;
        this.numberOfNeighboursToStayAlive = numberOfNeighboursToStayAlive;
        this.numberOfNeighboursToBeBorn = numberOfNeighboursToBeBorn;
    }

    public int getRule() {
        return rule;
    }

    public String getRuleName() {
        return ruleName;
    }

    public String getDescription() {
        return description;
    }

    public static GameOfLifeRule lookUp(int rule) {
        for (GameOfLifeRule gameOfLifeRule : GameOfLifeRule.values()) {
            if (gameOfLifeRule.rule == rule) {
                return gameOfLifeRule;
            }
        }
        throw new RuntimeException("Invalid GameOfLife rule " + rule + ".");
    }

    boolean isAfterTick(int neighbourCount, boolean isAlive) {
        if (isAlive) {
            return numberOfNeighboursToStayAlive.contains(neighbourCount);
        }
        return numberOfNeighboursToBeBorn.contains(neighbourCount);
    }
}
