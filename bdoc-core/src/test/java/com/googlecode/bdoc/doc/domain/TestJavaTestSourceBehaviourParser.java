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
import static junit.framework.Assert.assertNull;
import integrationtestclasses.bankaccount.BankAccountBehavior;
import integrationtestclasses.calculator.TestCalculatorBehaviour;
import integrationtestclasses.stack.StackBehavior;
import integrationtestclasses.stack.TestStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;

/**
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
@Ref(Story.ADVANCED_SCENARIO_SPECIFICATION)
public class TestJavaTestSourceBehaviourParser {

	JavaTestSourceBehaviourParser javaTestSourceBehaviourParser = new JavaTestSourceBehaviourParser(BDocTestHelper.SRC_TEST_JAVA);

	@Test
	public void shouldComposeScenarioFromTestConstructedWithGivenWhenThenAsPartOfTheMethodBlock() throws IOException {

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnEmptyStack"));
		expectedScenarioParts.add(new Scenario.Part("givenOnePushedItemVALUE_1"));
		expectedScenarioParts.add(new Scenario.Part("givenOnePushedItemVALUE_2"));
		expectedScenarioParts.add(new Scenario.Part("whenPopIsCalled"));
		expectedScenarioParts.add(new Scenario.Part("thenLastItemPushedAreReturnedFromPop"));
		expectedScenarioParts.add(new Scenario.Part("thenTheValueNotRemainsInTheStack"));
		expectedScenarioParts.add(new Scenario.Part("thenTheStackAreNotEmpty"));

		assertEquals(new Scenario(expectedScenarioParts), scenarioFromFactory(StackBehavior.class, "shouldPopLastPushedValueFirst"));
	}

	@Test
	public void shouldReturnNullIfTheMethodBlockDoesNotContainAScenario() throws IOException {
		assertNull(scenarioFromFactory(TestStack.class, "shouldBeEmptyWhenStackIsNew"));
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

		assertEquals(new Scenario(expectedScenarioParts), scenarioFromFactory(BankAccountBehavior.class, "shouldAddDepositToBalance"));
	}

	@Test
	public void shouldComposeScenarioFromTestConstructedWithCatchBlockAsPartOfTheMethodBlock() throws IOException {

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance20"));
		expectedScenarioParts.add(new Scenario.Part("whenWithdrawAreCalledWithAmount21"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldAnExceptionBeThrown"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo20"));

		assertEquals(new Scenario(expectedScenarioParts), scenarioFromFactory(BankAccountBehavior.class,
				"shouldNotAffectBalanceIfAttemptToWithdrawOverBalance"));
	}

	@Test
	public void shouldComposeScenarioIncludingParameter() throws IOException {

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance100"));
		expectedScenarioParts.add(new Scenario.Part("whenWithdrawAreCalledWithAmount20"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo80"));

		assertEquals(new Scenario(expectedScenarioParts), scenarioFromFactory(BankAccountBehavior.class, "shouldWithdrawAmountFromBalance"));
	}

	@Test
	@Ignore
	public void shouldHandleArgumentsThatAreVariables() throws IOException {
		Scenario scenario = scenarioFromFactory(TestCalculatorBehaviour.class,"shouldAddADoubleWithAnInteger");

		assertEquals("givenOperandOneIs4.5", scenario.getParts().get(0).camelCaseDescription());
		assertEquals("givenOperandTwoIs10", scenario.getParts().get(1).camelCaseDescription());

		assertEquals("whenTheAddOperationIsExecuted", scenario.getParts().get(2).camelCaseDescription());
		assertEquals("thenTheResultShouldEqual14.5", scenario.getParts().get(3).camelCaseDescription());
	}

	/**
	 * Helper method to avoid duplicate testcode when testing the scenario factory
	 */
	private Scenario scenarioFromFactory(Class<?> clazz, String methodName) {
		TestClass testClass = new TestClass(clazz);
		TestMethod testMethod = testClass.getTestMethod(methodName);
		Scenario scenario = javaTestSourceBehaviourParser.createScenario(testClass, testMethod);
		return scenario;
	}

}
