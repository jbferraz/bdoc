package com.googlecode.bdoc.sandbox.apptest;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest2 extends ExperimentalScenarioSupport<AppTest2> {

	/**
	 * Rigourous Test :-)
	 */
	@Test
	public void testApp() {
		given.operand1(new Integer(2));
		and.operand2(new Integer(5));
		when.theAddOperationIsExecuted();
		then.ensureTheResultIsTheSumOfTheTwoValues(new Integer(7));
	}

	void ensureTheResultIsTheSumOfTheTwoValues(Integer integer) {
	}

	void theAddOperationIsExecuted() {
	}

	void operand2(Integer integer) {
	}

	void operand1(Integer integer) {
	}
}
