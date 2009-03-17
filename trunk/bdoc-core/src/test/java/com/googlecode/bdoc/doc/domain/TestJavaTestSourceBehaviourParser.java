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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.*;
import integrationtestclasses.bankaccount.BankAccountBehavior;
import integrationtestclasses.stack.StackBehavior;
import integrationtestclasses.stack.TestStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;

/**
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
@Ref(Story.ADVANCED_SCENARIO_SPECIFICATION)
public class TestJavaTestSourceBehaviourParser {

	JavaTestSourceBehaviourParser javaTestSourceBehaviourParser = new JavaTestSourceBehaviourParser(BConst.SRC_TEST_JAVA);

	@Test
	public void shouldComposeScenarioFromTestConstructedWithGivenWhenThenAsPartOfTheMethodBlock() throws IOException {

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnEmptyStack"));
		expectedScenarioParts.add(new Scenario.Part("givenOnePushedItemFoo"));
		expectedScenarioParts.add(new Scenario.Part("givenOnePushedItemBar"));
		expectedScenarioParts.add(new Scenario.Part("whenPopIsCalled"));
		expectedScenarioParts.add(new Scenario.Part("thenPopedValueShouldBeBar"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldTheValueNotRemainsInTheStack"));
		expectedScenarioParts.add(new Scenario.Part("thenTheStackAreNotEmpty"));

		assertEquals(new Scenario(expectedScenarioParts), scenarioFromFactory(StackBehavior.class, "shouldPopLastPushedValueFirst").get(0));
	}

	
	@Test
	public void shouldReturnNullIfTheMethodBlockDoesNotContainAScenario() throws IOException {
		assertTrue(scenarioFromFactory(TestStack.class, "shouldBeEmptyWhenStackIsNew").isEmpty());
	}

	@Test
	public void shouldComposeScenarioFromTestConstructedWithMultipleGivenWhenThenAsPartOfTheMethodBlock() throws IOException {

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance0"));
		expectedScenarioParts.add(new Scenario.Part("whenDepositAreCalledWithAmount100"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo100"));
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance100"));
		expectedScenarioParts.add(new Scenario.Part("whenDepositAreCalledWithAmount100"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo200"));

		assertEquals(new Scenario(expectedScenarioParts), scenarioFromFactory(BankAccountBehavior.class, "shouldAddDepositToBalance").get(0));
	}

	@Test
	public void shouldComposeScenarioFromTestConstructedWithCatchBlockAsPartOfTheMethodBlock() throws IOException {

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance20"));
		expectedScenarioParts.add(new Scenario.Part("whenWithdrawAreCalledWithAmount21"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldAnExceptionBeThrown"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo20"));

		assertEquals(new Scenario(expectedScenarioParts), scenarioFromFactory(BankAccountBehavior.class,
				"shouldNotAffectBalanceIfAttemptToWithdrawOverBalance").get(0));
	}

	@Test
	public void shouldComposeScenarioIncludingParameter() throws IOException {

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance100"));
		expectedScenarioParts.add(new Scenario.Part("whenWithdrawAreCalledWithAmount20"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo80"));

		assertEquals(new Scenario(expectedScenarioParts), scenarioFromFactory(BankAccountBehavior.class, "shouldWithdrawAmountFromBalance").get(0));
	}

	@Test
	public void shouldResetCreatedScenarioForEachAnalyze() {
		TestClass testClass = new TestClass(BankAccountBehavior.class);
		javaTestSourceBehaviourParser.analyze(testClass.getTestMethod("shouldAddDepositToBalance"));
		assertNotNull( javaTestSourceBehaviourParser.getCreatedScenario() );
		javaTestSourceBehaviourParser.analyze(testClass.getTestMethod("shouldBeEmptySpecification"));
		assertTrue( javaTestSourceBehaviourParser.getCreatedScenario().isEmpty() );
	}
	
	/**
	 * Helper method to avoid duplicate testcode when testing the scenario factory
	 */
	private List<Scenario> scenarioFromFactory(Class<?> clazz, String methodName) {
		TestClass testClass = new TestClass(clazz);
		TestMethod testMethod = testClass.getTestMethod(methodName);
		javaTestSourceBehaviourParser.analyze(testMethod);
		return javaTestSourceBehaviourParser.getCreatedScenario();
	}

}
