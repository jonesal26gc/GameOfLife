/**
 * Created by xm39 on 25/01/2017.
 */
public enum GameOfLifeRule {
    STANDARD(1, "B3/S23", "Original Game - Born 3, Survives 2/3"),
    SIERPINSKI (2, "B1/S12", "Sierpinski  - Born 1, Survives 1/2"),
    HIGH_LIFE(3, "B36/S23", "High-Life - Born 3/6, Survives 2/3");

    int rule;
    String ruleName;
    String description;

    GameOfLifeRule(int rule, String ruleName, String description) {
        this.rule = rule;
        this.ruleName = ruleName;
        this.description = description;
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
}
