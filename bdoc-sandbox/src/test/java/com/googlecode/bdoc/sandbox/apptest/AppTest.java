package com.googlecode.bdoc.sandbox.apptest;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testApp() {
		givenOperand1(new Integer(2));
		givenOperand2(new Integer(5));
		whenTheAddOperationIsExecuted();
		thenEnsureTheResultIsTheSumOfTheTwoValues(new Integer(7));
	}

	void thenEnsureTheResultIsTheSumOfTheTwoValues(Integer integer) {
	}

	void whenTheAddOperationIsExecuted() {
	}

	void givenOperand2(Integer integer) {
	}

	void givenOperand1(Integer integer) {
	}
}
