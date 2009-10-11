package yatzy;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bdoc.Ref;
import bdoc.RefClass;
import bdoc.ScenarioSupport;
import bdoc.Story;

/**
 * http://ellnestam.wordpress.com/2007/07/13/first-crack-at-bdding-a-yatzy-game/
 * http://www.wartoft.nu/yahtzee/rules/
 * http://www.tactic.net/site/rules/NO/02029.pdf
 * 
 * @author Per Otto
 * 
 * 1. Before/after må kjøres 
 */
@RefClass( Game.class )
public class TestYatzyGameBehaviour extends ScenarioSupport<TestYatzyGameBehaviour>{

	private Game game;

	@Before
	public void resetGame() {
		game = new Game();
	}

	@Test
	public void shouldComputeScorePerPlayer() {
		resetGame(); //need to run before
	
		game.addRoll("perotto", asList(1, 2, 3, 4, 5));
		game.addRoll("another", asList(1, 1, 1, 1, 1));
		assertEquals(15, game.score("perotto"));
		assertEquals(5, game.score("another"));
	}

	@Test
	public void shouldComputeAllRollsForAGivenPlayer() {
		resetGame(); //need to run before
		
		game.addRoll("perotto", asList(1, 2, 3, 4, 5));
		game.addRoll("perotto", asList(1, 1, 1, 1, 1));
		assertEquals("Chance", 20, game.score("perotto"));
	}

	@Test
	public void shouldComputeTheScoreAccordingToRuleSpecified() {
		example.rollComputation( Rule.ONES, asList(1, 1, 1, 1, 1), 5 );		
	}

	void rollComputation(Rule rule, List<Integer> roll, int expectedRollScore) {
		
	}

}
