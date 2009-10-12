package yatzy;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import org.junit.Test;

import bdoc.RefClass;

public class TestRule {

	@Test
	public void onesAreTheSumOfAllOnes() {
		assertEquals(5, Rule.ONES.compute(1, 1, 1, 1, 1));
		assertEquals(1, Rule.ONES.compute(1, 2, 3, 4, 5));
	}

	@Test
	public void twosAreTheScoreOfAllTwos() {
		assertEquals(10, Rule.TWOS.compute(2, 2, 2, 2, 2));
		assertEquals(2, Rule.TWOS.compute(1, 2, 3, 4, 5));
	}

	@Test
	public void threesAreTheScoreOfAllThrees() {
		assertEquals(15, Rule.THREES.compute(3, 3, 3, 3, 3));
		assertEquals(3, Rule.THREES.compute(1, 2, 3, 4, 5));
	}
	
	@Test
	public void foursAreTheScoreOfAllFours() {
		assertEquals(20, Rule.FOURS.compute(4, 4, 4, 4, 4));
		assertEquals(4, Rule.FOURS.compute(1, 2, 3, 4, 5));
	}
	
	@Test
	public void fivesAreTheScoreOfAllFives() {
		assertEquals(25, Rule.FIVES.compute(5, 5, 5, 5, 5));
		assertEquals(5, Rule.FIVES.compute(1, 2, 3, 4, 5));
	}
	
	@Test
	public void sixesAreTheScoreOfAllSixes() {
		assertEquals(30, Rule.SIXES.compute(6, 6, 6, 6, 6));
		assertEquals(6, Rule.SIXES.compute(1, 2, 6, 4, 5));
	}
	
	@Test
	public void threeOfAKindIsTheSumOfTheThreeEqualDice() {
		assertEquals(18, Rule.THREE_OF_A_KIND.compute(6, 6, 6, 1, 1));
		assertEquals(9, Rule.THREE_OF_A_KIND.compute(2, 3, 3, 3, 1));
		assertEquals(15, Rule.THREE_OF_A_KIND.compute(2, 4, 5, 5, 5));
		assertEquals(0, Rule.THREE_OF_A_KIND.compute(3, 6, 6, 1, 1));
	}
	
	@Test
	public void fourOfAKindIsTheSumOfTheFourEqualDice() {
		assertEquals(24, Rule.FOUR_OF_A_KIND.compute(6, 6, 6, 6, 1));
		assertEquals(12, Rule.FOUR_OF_A_KIND.compute(2, 3, 3, 3, 3));
		assertEquals(20, Rule.FOUR_OF_A_KIND.compute(5, 4, 5, 5, 5));		
	}
	
}
