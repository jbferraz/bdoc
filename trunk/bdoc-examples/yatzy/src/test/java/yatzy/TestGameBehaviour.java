package yatzy;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import testsupport.Ref;
import testsupport.RefClass;
import testsupport.ScenarioSupport;
import testsupport.Story;


/**
 * Yatzy stuff:
 * 
 * http://ellnestam.wordpress.com/2007/07/13/first-crack-at-bdding-a-yatzy-game/ http://www.wartoft.nu/yahtzee/rules/
 * http://www.tactic.net/site/rules/NO/02029.pdf
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
@Ref(Story.YATZY_GAME)
@RefClass(Game.class)
public class TestGameBehaviour extends ScenarioSupport<TestGameBehaviour> {

    private Game game;

    @Before
    public void resetGame() {
        game = new Game();
    }
    
	@Test
	public void shouldComputeAllRollsForAGivenPlayer() {
		game.addRoll("perotto", asList(1, 1, 1, 1, 1), Rule.ONES);
		game.addRoll("perotto", asList(2, 2, 2, 2, 2), Rule.TWOS);
		assertEquals("Chance", 15, game.score("perotto"));
	}    

    @Test
    public void shouldKeepTrackOfTotalScoreForAPlayer() {
        int _try = 0;
        example.game(++_try, Rule.ONES, asList(1, 1, 1, 1, 1), 5);
        example.game(++_try, Rule.TWOS, asList(2, 2, 2, 2, 2), 15);
        example.game(++_try, Rule.THREES, asList(3, 3, 3, 3, 3), 30);
        example.game(++_try, Rule.FOURS, asList(4, 4, 4, 4, 4), 50);
        example.game(++_try, Rule.FIVES, asList(5, 5, 5, 5, 5), 75);
        example.game(++_try, Rule.SIXES, asList(6, 6, 6, 6, 6), 105);
        example.game(++_try, Rule.THREE_OF_A_KIND, asList(1, 6, 6, 6, 1), 123);
        example.game(++_try, Rule.FOUR_OF_A_KIND, asList(6, 6, 6, 6, 1), 147);
        example.game(++_try, Rule.FULL_HOUSE, asList(6, 6, 6, 2, 2), 169);

    }

    void game(int _try, Rule rule, List<Integer> roll, int totalScore) {
        game.addRoll("unknown", roll, rule);
        assertEquals("Computation with rule " + rule, totalScore, game.score("unknown"));
    }

    @Test
    public void shouldComputeScorePerPlayer() {
        given.player(action("perotto", "has added", asList(1, 1, 1, 1, 1), Rule.ONES));
        and.player(action("bob", "has added", asList(2, 2, 2, 2, 2), Rule.TWOS));
        when.player(action("perotto", "adds", asList(6, 6, 6, 1, 1), Rule.THREE_OF_A_KIND));
        then.ensureResult(new Result("perotto", 23));
        and.ensureResult(new Result("bob", 10));
    }

    void player(Action score) {
        game.addRoll(score.player, score.roll, score.rule);
    }

    void scoreForPlayerShouldEqual(String player, int score) {
        assertEquals(score, game.score(player));
    }

    void ensureResult(Result score) {
        assertEquals(score.score, game.score(score.player));
    }

    static Action action(String player, String msg, List<Integer> roll, Rule rule) {
        return new Action(player, msg, roll, rule);
    }

    static class Action {
        String player;
        String msg;
        Rule rule;
        List<Integer> roll;

        public Action(String player, String msg, List<Integer> roll, Rule rule) {
            this.player = player;
            this.msg = msg;
            this.rule = rule;
            this.roll = roll;
        }

        public String toString() {
            return player + " " + msg + " " + rule.name().toLowerCase() + " " + roll + ", ";
        }
    }

    static class Result {
        String player;
        int score;

        public Result(String player, int score) {
            this.player = player;
            this.score = score;
        }

        public String toString() {
            return ": " + player + " = " + score;
        }
    }
}
