/**
 * The MIT License
 * 
 * Copyright (c) 2008 Per Otto Bergum Christensen
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package bdddoc4j.core.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestGeneralBehaviour {

	private static final String GIVEN_SOMETHING_WHEN_AN_ACTION_THEN_VERIFY_RESULT = "givenSomethingWhenAnActionThenVerifyResult";
	private static final String SHOULD_VERIFY_THE_IMPORTANT_STUFF = "shouldVerifyTheImportantStuff";
	private static final String TEST_DATA_IS_IMPORTENT = "testDataIsImportent";
	private GeneralBehaviour generalBehaviour;

	@Before
	public void setup() {
		generalBehaviour = new GeneralBehaviour();
		generalBehaviour.addBehaviour(TestExample.class, SHOULD_VERIFY_THE_IMPORTANT_STUFF);
		generalBehaviour.addBehaviour(TestExample.class, GIVEN_SOMETHING_WHEN_AN_ACTION_THEN_VERIFY_RESULT);
		generalBehaviour.addBehaviour(TestExample.class, TEST_DATA_IS_IMPORTENT);
	}

	@Test
	public void shouldNotSpecifyAnyStatementsForCodeWithoutBehaviourAdded() {
		assertFalse(new GeneralBehaviour().hasClassStatements());
	}

	@Test
	public void shouldBeAbleToRetreiveAndAddedStatement() {
		assertTrue(generalBehaviour.getPackages().get(0).getClassStatements().get(0).getStatements().contains(
				new Statement(TEST_DATA_IS_IMPORTENT)));
	}

	@Test
	public void shouldBeAbleToRetreiveAndAddedSpecification() {
		assertTrue(generalBehaviour.getPackages().get(0).getClassSpecifications().get(0).getSpecifications().contains(
				new Specification(SHOULD_VERIFY_THE_IMPORTANT_STUFF)));
	}

	@Test
	public void shouldBeAbleToRetreiveAndAddedScenario() {
		assertTrue(generalBehaviour.getPackages().get(0).getScenarios().contains(
				new Scenario(GIVEN_SOMETHING_WHEN_AN_ACTION_THEN_VERIFY_RESULT)));
	}

	@Test
	public void shouldNotSpecifyAnySpecificationsForCodeWithoutBehaviourAdded() {
		assertFalse(new GeneralBehaviour().hasClassSpecifications());
	}

	@Test
	public void shouldSpecifySpecificationsWhenSpecificationsAreAdded() {
		GeneralBehaviour packages = new GeneralBehaviour();
		packages.addBehaviour(TestExample.class, SHOULD_VERIFY_THE_IMPORTANT_STUFF);
		assertTrue(packages.hasClassSpecifications());
	}

	/**
	 * Testdata
	 */
	public class TestExample {
	}

}
