package yatzy;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bdoc.ScenarioSupport;

public class TestGame extends ScenarioSupport<TestGame> {

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

	
}
