/**
 * The MIT License
 * 
 * Copyright (c) 2008, 2009 @Author(s)
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

package com.googlecode.bdoc.doc.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestModuleBehaviour {

	private static final String GIVEN_SOMETHING_WHEN_AN_ACTION_THEN_VERIFY_RESULT = "givenSomethingWhenAnActionThenVerifyResult";
	private static final String SHOULD_VERIFY_THE_IMPORTANT_STUFF = "shouldVerifyTheImportantStuff";
	private static final String MY_TEST_DATA_IS_IMPORTENT = "myTestDataIsImportent";
	private ModuleBehaviour generalBehaviour;

	@Before
	public void setup() {
		generalBehaviour = new ModuleBehaviour();
		generalBehaviour.addBehaviour(new TestMethod(TestExample.class, SHOULD_VERIFY_THE_IMPORTANT_STUFF));
		generalBehaviour.addBehaviour(new TestMethod(TestExample.class, GIVEN_SOMETHING_WHEN_AN_ACTION_THEN_VERIFY_RESULT));
		generalBehaviour.addBehaviour(new TestMethod(TestExample.class, MY_TEST_DATA_IS_IMPORTENT));
	}

	@Test
	public void shouldNotSpecifyAnyStatementsForCodeWithoutBehaviourAdded() {
		assertFalse(new ModuleBehaviour().hasClassStatements());
	}

	@Test
	public void shouldBeAbleToRetreiveAndAddedStatement() {
		assertEquals(Arrays.asList(new Statement(MY_TEST_DATA_IS_IMPORTENT)), generalBehaviour.getPackages().get(0)
				.getClassStatements().get(0).getStatements());
	}

	@Test
	public void shouldBeAbleToRetreiveAndAddedSpecification() {
		assertTrue(generalBehaviour.getPackages().get(0).getClassSpecifications().get(0).getSpecifications().contains(
				new Specification(SHOULD_VERIFY_THE_IMPORTANT_STUFF)));
	}

	@Test
	public void shouldBeAbleToRetreiveAnAddedScenario() {
		assertTrue(generalBehaviour.getPackages().get(0).getScenarios().contains(
				new Scenario(GIVEN_SOMETHING_WHEN_AN_ACTION_THEN_VERIFY_RESULT)));
	}

	@Test
	public void shouldNotSpecifyAnySpecificationsForCodeWithoutBehaviourAdded() {
		assertFalse(new ModuleBehaviour().hasClassSpecifications());
	}

	@Test
	public void shouldSpecifySpecificationsWhenSpecificationsAreAdded() {
		ModuleBehaviour packages = new ModuleBehaviour();
		packages.addBehaviour(new TestMethod(TestExample.class, SHOULD_VERIFY_THE_IMPORTANT_STUFF));
		assertTrue(packages.hasClassSpecifications());
	}

	@Test
	public void supportsListingOfAllSpecifications() {
		ModuleBehaviour generalBehaviour = new ModuleBehaviour();
		generalBehaviour.addBehaviour(new TestMethod(TestExample.class, "shouldRepresentASpecification"));

		assertFalse(generalBehaviour.specifications().isEmpty());
	}

	/**
	 * Testdata
	 */
	public class TestExample {

		@Test
		public void shouldVerifyTheImportantStuff() {
		}

		@Test
		public void myTestDataIsImportent() {
		}

		@Test
		public void givenSomethingWhenAnActionThenVerifyResult() {
		}

		@Test
		public void shouldRepresentASpecification() {
		}

		@Test
		public void itsAllAboutBehaviour() {
		}

		@Test
		public void givenWhenThen() {
		}

	}

}
