import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by xm39 on 27/01/2017.
 */
public class GameOfLifeRuleShould {

    @Test
    public void
    retrieve_the_rule_by_number() {
        // then
        assertThat(GameOfLifeRule.lookUp(0),is(GameOfLifeRule.STANDARD));
        assertThat(GameOfLifeRule.lookUp(1),is(GameOfLifeRule.SIERPINSKI));
        assertThat(GameOfLifeRule.lookUp(2),is(GameOfLifeRule.HIGH_LIFE));
    }


    @Test(expected=RuntimeException.class)
    public void
    fail_retrieve_the_rule_by_number() {
        // then
        assertThat(GameOfLifeRule.lookUp(3),is(GameOfLifeRule.STANDARD));

    }

}
