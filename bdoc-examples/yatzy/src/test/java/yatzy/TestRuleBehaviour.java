package yatzy;

import static java.util.Arrays.asList;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import testsupport.Ref;
import testsupport.RefClass;
import testsupport.ScenarioSupport;
import testsupport.Story;

@Ref(Story.YATZY_GAME)
@RefClass(Rule.class)
public class TestRuleBehaviour extends ScenarioSupport<TestRuleBehaviour> {

	@Test
	public void onesAreTheSumOfAllOnes() {
		example.singleRollComputation(Rule.ONES, asList(1, 1, 1, 1, 1), 5);
		example.singleRollComputation(Rule.ONES, asList(1, 2, 3, 4, 5), 1);
	}

	@Test
	public void twosAreTheScoreOfAllTwos() {
		example.singleRollComputation(Rule.TWOS, asList(2, 2, 2, 2, 2), 10);
		example.singleRollComputation(Rule.TWOS, asList(1, 2, 3, 4, 5), 2);
	}

	@Test
	public void threesAreTheScoreOfAllThrees() {
		example.singleRollComputation(Rule.THREES, asList(3, 3, 3, 3, 3), 15);
		example.singleRollComputation(Rule.THREES, asList(1, 2, 3, 4, 5), 3);
	}

	@Test
	public void foursAreTheScoreOfAllFours() {
		example.singleRollComputation(Rule.FOURS, asList(4, 4, 4, 4, 4), 20);
		example.singleRollComputation(Rule.FOURS, asList(1, 2, 3, 4, 5), 4);
	}

	@Test
	public void fivesAreTheScoreOfAllFives() {
		example.singleRollComputation(Rule.FIVES, asList(5, 5, 5, 5, 5), 25);
		example.singleRollComputation(Rule.FIVES, asList(1, 2, 3, 4, 5), 5);
	}

	@Test
	public void sixesAreTheScoreOfAllSixes() {
		example.singleRollComputation(Rule.SIXES, asList(6, 6, 6, 6, 6), 30);
		example.singleRollComputation(Rule.SIXES, asList(1, 2, 6, 4, 5), 6);
	}

	@Test
	public void threeOfAKindIsTheSumOfTheThreeEqualDice() {
		example.singleRollComputation(Rule.THREE_OF_A_KIND, asList(6, 6, 6, 1, 1), 18);
		example.singleRollComputation(Rule.THREE_OF_A_KIND, asList(2, 3, 3, 3, 1), 9);
		example.singleRollComputation(Rule.THREE_OF_A_KIND, asList(2, 4, 5, 5, 5), 15);
		example.singleRollComputation(Rule.THREE_OF_A_KIND, asList(3, 6, 6, 1, 1), 0);
	}

	@Test
	public void fourOfAKindIsTheSumOfTheFourEqualDice() {
		example.singleRollComputation(Rule.FOUR_OF_A_KIND, asList(6, 6, 6, 6, 1), 24);
		example.singleRollComputation(Rule.FOUR_OF_A_KIND, asList(2, 3, 3, 3, 3), 12);
		example.singleRollComputation(Rule.FOUR_OF_A_KIND, asList(5, 4, 5, 5, 5), 20);
	}

	@Test
	public void fullHouseIsTheSumOfThreeEqualDicePlussTwoEqualDice() {
		example.singleRollComputation(Rule.FULL_HOUSE, asList(2, 2, 3, 3, 3), 13);
		example.singleRollComputation(Rule.FULL_HOUSE, asList(4, 4, 3, 3, 3), 17);
		example.singleRollComputation(Rule.FULL_HOUSE, asList(3, 4, 3, 3, 3), 0);
		example.singleRollComputation(Rule.FULL_HOUSE, asList(4, 3, 4, 4, 4), 0);
		example.singleRollComputation(Rule.FULL_HOUSE, asList(5, 5, 4, 4, 1), 0);
		// one example is missing, that will fail the full house
		// rule-implementation
	}

	void singleRollComputation(Rule rule, List<Integer> roll, int score) {
		assertEquals("Computation with rule " + rule, score, rule.compute(roll));
	}

}
