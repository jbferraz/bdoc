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

package com.googlecode.bdoc.doc.dynamic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.dynamic.testdata.AccountBehaviour;
import com.googlecode.bdoc.doc.dynamic.testdata.SimpleBehaviour;
import com.googlecode.bdoc.doc.dynamic.testdata.TestMyObjectBehaviour;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.ADVANCED_SCENARIO_SPECIFICATION)
public class TestRuntimeBehaviourFactory {

	private TestClass simpleTestClass = new TestClass(SimpleBehaviour.class);
	private TestClass accountBehaviourTestClass = new TestClass(AccountBehaviour.class);

	RuntimeBehaviourFactory runtimeBehaviourFactory = new RuntimeBehaviourFactory(BConst.SRC_TEST_JAVA);

	@Test
	public void shouldCreateAScenarioFromAListOfMethodCalls() {
		runtimeBehaviourFactory.analyze(simpleTestClass.getTestMethod("shouldBeSimple"));
		assertFalse(runtimeBehaviourFactory.getCreatedScenarios().isEmpty());
		assertEquals(new Scenario("givenWhenThen"), runtimeBehaviourFactory.getCreatedScenarios().get(0));
	}

	@Test
	public void shouldResetCreatedScenarioWhenAnalyzeIsRunAgain() {
		runtimeBehaviourFactory.analyze(simpleTestClass.getTestMethod("shouldBeSimple"));
		runtimeBehaviourFactory.analyze(simpleTestClass.getTestMethod("shouldNotContainScenario"));
		assertTrue(runtimeBehaviourFactory.getCreatedScenarios().isEmpty());
	}

	@Test
	public void shouldCreateAScenarioFromTestMethodWithGivenWhenThenMethodCalls() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("plainScenario"));
		assertEquals(new Scenario("givenWhenThen"), runtimeBehaviourFactory.getCreatedScenarios().get(0));
	}

	@Test
	public void shouldAddMethodCallArgumentValuesToTheEndOfEachScenarioPart() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("scenarioWithArguments"));
		assertEquals(new Scenario("given_1_When_2_And_3_Then_4_And_5_And_6_"), runtimeBehaviourFactory.getCreatedScenarios().get(0));
	}

	@Test
	public void shouldPutASpaceCharBeforeAndAfterEachArgument() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("scenarioWithArguments"));
		assertEquals(new Scenario("given_1_When_2_And_3_Then_4_And_5_And_6_"), runtimeBehaviourFactory.getCreatedScenarios().get(0));
	}

	@Test
	public void shouldUseTheWordOgBetweenArgumentsForScenariosWrittenInNorwegian() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("norwegianScenario"));
		assertEquals(new Scenario("gitt_1_Naar_2_Og_3_Saa_4_Og_5_Og_6_"), runtimeBehaviourFactory.getCreatedScenarios().get(0));
	}

	@Test
	public void shouldNotCreateAScenarioForTestMethodsThatThrowsException() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("shouldJustBeASimpleSpecification"));
		assertTrue(runtimeBehaviourFactory.getCreatedScenarios().isEmpty());
	}

	@Test
	public void shouldNotCreateAScenarioForTestMethodsThatDoNotContainAScenario() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("emptyTest"));
		assertTrue(runtimeBehaviourFactory.getCreatedScenarios().isEmpty());
	}

	@Test
	public void shouldCreateTestTablesFromTestMethodWhereCallsAreMadeToAccessibleCustomAssert() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("shouldContainATestTable"));
		assertFalse(runtimeBehaviourFactory.getCreatedTestTables().isEmpty());
		assertEquals("assertSum", runtimeBehaviourFactory.getCreatedTestTables().get(0).getSentence());
	}

	@Test
	public void shouldNotCreateATestTablesFromTestMethodWhenNonCallsAreMadeToAccessibleCustomAssert() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("emptyTest"));
		assertTrue(runtimeBehaviourFactory.getCreatedTestTables().isEmpty());
	}

	@Test
	public void shouldNotCreateATestTablesFromTestMethodWhenCallsAreMadeToAccessibleCustomAssertOnlyOnce() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("withdraw"));
		assertTrue(runtimeBehaviourFactory.getCreatedTestTables().isEmpty());
	}

	@Test
	public void shouldNotCreateAScenarioIfTheFirstMethodDoesntStartWithTheScenarioKeywordGiven() {
		runtimeBehaviourFactory.analyze(accountBehaviourTestClass.getTestMethod("shouldContainATestTable"));
		assertTrue(runtimeBehaviourFactory.getCreatedScenarios().isEmpty());
	}

	@Test
	public void shouldCreateBothScenariosAndTestTablesDeclaredInTheSameTestMethod() {
		runtimeBehaviourFactory.analyze(new TestClass(TestMyObjectBehaviour.class).getTestMethod("shouldAddTwoNumbers"));

		List<Scenario> createdScenarios = runtimeBehaviourFactory.getCreatedScenarios();
		List<TestTable> createdTestTables = runtimeBehaviourFactory.getCreatedTestTables();

		assertTrue(createdScenarios.contains(new Scenario("givenWhenThen")));
		assertTrue(createdTestTables.contains(new TestTable("example")));
	}

	@Test
	public void shouldNotCreateTestTablesWhenMethodCallsDoNotIncludeArguments() {
		runtimeBehaviourFactory.analyze(new TestClass(TestMyObjectBehaviour.class).getTestMethod("shouldMakeACallWithoutArguments"));
		assertTrue(runtimeBehaviourFactory.getCreatedTestTables().isEmpty());
	}
}