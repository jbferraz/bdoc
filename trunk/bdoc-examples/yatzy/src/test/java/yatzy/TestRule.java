package yatzy;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
