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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

	@Test
	public void shouldComposeScenarioFromTestConstructedWithGivenWhenThenAsPartOfTheMethodBlock() throws IOException {

		String stackBehaviorJava = FileUtils.readFileToString(new File(BDocTestHelper.SRC_TEST_JAVA
				+ "/integrationtestclasses/stack/StackBehavior.java"));

		JavaTestSourceBehaviourParser sourceClassBehaviourParser = new JavaTestSourceBehaviourParser(stackBehaviorJava);

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnEmptyStack"));
		expectedScenarioParts.add(new Scenario.Part("givenOnePushedItemVALUE_1"));
		expectedScenarioParts.add(new Scenario.Part("givenOnePushedItemVALUE_2"));
		expectedScenarioParts.add(new Scenario.Part("whenPopIsCalled"));
		expectedScenarioParts.add(new Scenario.Part("thenLastItemPushedAreReturnedFromPop"));
		expectedScenarioParts.add(new Scenario.Part("thenTheValueNotRemainsInTheStack"));
		expectedScenarioParts.add(new Scenario.Part("thenTheStackAreNotEmpty"));

		assertEquals(new Scenario(expectedScenarioParts), sourceClassBehaviourParser.getScenario("shouldPopLastPushedValueFirst"));
	}

	@Test
	public void shouldReturnNullIfTheMethodBlockDoesNotContainAScenario() throws IOException {
		String testStackJava = FileUtils.readFileToString(new File(BDocTestHelper.SRC_TEST_JAVA
				+ "/integrationtestclasses/stack/TestStack.java"));

		JavaTestSourceBehaviourParser sourceClassBehaviourParser = new JavaTestSourceBehaviourParser(testStackJava);

		assertNull(sourceClassBehaviourParser.getScenario("shouldBeEmptyWhenStackIsNew"));
	}

	@Test
	public void shouldComposeScenarioFromTestConstructedWithMultipleGivenWhenThenAsPartOfTheMethodBlock() throws IOException {

		String behaviorJava = FileUtils.readFileToString(new File(BDocTestHelper.SRC_TEST_JAVA
				+ "/integrationtestclasses/bankaccount/BankAccountBehavior.java"));

		JavaTestSourceBehaviourParser sourceClassBehaviourParser = new JavaTestSourceBehaviourParser(behaviorJava);

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance0"));
		expectedScenarioParts.add(new Scenario.Part("whenDepositAreCalledWithAmount100"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo100"));
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance100"));
		expectedScenarioParts.add(new Scenario.Part("whenDepositAreCalledWithAmount100"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo200"));

		Scenario scenario = sourceClassBehaviourParser.getScenario("shouldAddDepositToBalance");

		assertEquals(new Scenario(expectedScenarioParts), scenario);
	}

	@Test
	public void shouldComposeScenarioFromTestConstructedWithCatchBlockAsPartOfTheMethodBlock() throws IOException {

		String behaviorJava = FileUtils.readFileToString(new File(BDocTestHelper.SRC_TEST_JAVA
				+ "/integrationtestclasses/bankaccount/BankAccountBehavior.java"));

		JavaTestSourceBehaviourParser sourceClassBehaviourParser = new JavaTestSourceBehaviourParser(behaviorJava);

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance20"));
		expectedScenarioParts.add(new Scenario.Part("whenWithdrawAreCalledWithAmount21"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldAnExceptionBeThrown"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo20"));

		Scenario scenario = sourceClassBehaviourParser.getScenario("shouldNotAffectBalanceIfAttemptToWithdrawOverBalance");

		assertEquals(new Scenario(expectedScenarioParts), scenario);
	}

	@Test
	public void shouldComposeScenarioIncludingParameter() throws IOException {

		String behaviorJava = FileUtils.readFileToString(new File(BDocTestHelper.SRC_TEST_JAVA
				+ "/integrationtestclasses/bankaccount/BankAccountBehavior.java"));

		JavaTestSourceBehaviourParser sourceClassBehaviourParser = new JavaTestSourceBehaviourParser(behaviorJava);

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnAccountWithInitialBalance100"));
		expectedScenarioParts.add(new Scenario.Part("whenWithdrawAreCalledWithAmount20"));
		expectedScenarioParts.add(new Scenario.Part("thenShouldBalanceEqualsTo80"));

		Scenario scenario = sourceClassBehaviourParser.getScenario("shouldWithdrawAmountFromBalance");

		assertEquals(new Scenario(expectedScenarioParts), scenario);
	}

	@Test
	@Ignore
	public void shouldHandleArgumentsThatAreVariables() throws IOException {
		String behaviorJava = FileUtils.readFileToString(new File(BDocTestHelper.SRC_TEST_JAVA
				+ "/integrationtestclasses/calculator/TestCalculatorBehaviour.java"));

		JavaTestSourceBehaviourParser sourceClassBehaviourParser = new JavaTestSourceBehaviourParser(behaviorJava);
		Scenario scenario = sourceClassBehaviourParser.getScenario("shouldAddADoubleWithAnInteger");

		assertEquals("givenOperandOneIs4.5", scenario.getParts().get(0).camelCaseDescription() );
		assertEquals("givenOperandTwoIs10", scenario.getParts().get(1).camelCaseDescription() );
		
		assertEquals("whenTheAddOperationIsExecuted", scenario.getParts().get(2).camelCaseDescription() );
		assertEquals("thenTheResultShouldEqual14.5", scenario.getParts().get(3).camelCaseDescription() );
	}
}
