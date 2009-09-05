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

package com.googlecode.bdoc.doc.tinybdd;

import static com.googlecode.bdoc.doc.domain.Scenario.part;
import static com.googlecode.bdoc.doc.domain.TableColumn.columns;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.Scenario.Part;
import com.googlecode.bdoc.doc.tinybdd.testdata.TeztTinyTestdataScenarios;

public class TestTinyBddAnalyzer {

	private TinyBddAnalyzer bddAnalyzer = new TinyBddAnalyzer(BConst.SRC_TEST_JAVA);

	@Test
	public void shouldReturnSourceTestJava() {
		assertEquals(BConst.SRC_TEST_JAVA, bddAnalyzer.sourceTestDirectory());
	}

	@Test
	public void shouldCreateAScenarioForSimpleTinyBddSyntax() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "simpleGivenWhenThen");

		assertFalse(bddAnalyzer.getCreatedScenarios().isEmpty());

		List<Part> expectedParts = asList(part("given criteriaA"), part("when actionA"), part("then ensureA"));
		assertEquals(new Scenario(expectedParts), bddAnalyzer.getCreatedScenarios().get(0));
	}

	private TinyBddAnalyzer analyze(Class<TeztTinyTestdataScenarios> clazz, String methodName) {
		TinyBddAnalyzer bddAnalyzer = new TinyBddAnalyzer(BConst.SRC_TEST_JAVA);
		bddAnalyzer.analyze(new TestClass(clazz).getTestMethod(methodName));
		return bddAnalyzer;
	}

	@Test
	public void shouldNotAffectInternalStateOfTheTestUnderAnalyze() {
		TinyBddAnalyzer tinyBddAnalyzer = new TinyBddAnalyzer(BConst.SRC_TEST_JAVA);
		tinyBddAnalyzer.analyze(new TestClass(TeztTinyTestdataScenarios.class)
				.getTestMethod("willFailIfStateIsNotHandledByBddAnalyzer"));
	}

	@Test
	public void primitivtInputArgumentToScenarioPartShouldBeAddedToTheDescription() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "scenarioWithPrimitivArgument");

		Scenario scenario = bddAnalyzer.getCreatedScenarios().get(0);
		Part part = scenario.getParts().get(0);
		assertEquals("given numberWithValue 10", part.camelCaseDescription());
	}

	@Test
	public void nameOfMethodUsedAsInputArgumentToScenarioPartShouldBeAddedToTheDescription() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "containsScenarioWithTable");
		Scenario scenario = bddAnalyzer.getCreatedScenarios().get(0);
		Part part = scenario.getParts().get(0);
		assertEquals("given listWith randomNumbers", part.camelCaseDescription());
	}

	@Test
	public void collectionArgumentToScenarioPartShouldBeAddedAsATestTable() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "containsScenarioWithTable");
		Scenario scenario = bddAnalyzer.getCreatedScenarios().get(0);
		Part part = scenario.getParts().get(0);
		assertFalse(part.getArgumentTables().isEmpty());
	}

	@Test
	public void testTablesCreatedWithAFunctionCallAsArgumentToAScenarioPartShouldBeNamedTheSameAsTheFunction() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "containsScenarioWithTable");

		Scenario scenario = bddAnalyzer.getCreatedScenarios().get(0);
		Part part = scenario.getParts().get(0);
		assertEquals("randomNumbers", part.getArgumentTables().get(0).getCamelCaseSentence());
	}

	@Test
	public void testTableCreatedForAScenarioPartShouldContainOneRowForEachItemInTheCollection() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "containsScenarioWithTable");

		Scenario scenario = bddAnalyzer.getCreatedScenarios().get(0);
		Part part = scenario.getParts().get(0);
		TestTable testTable = part.getArgumentTables().get(0);
		assertEquals(3, testTable.getRows().size());
	}

	@Test
	public void shouldSupportDifferentLanguagesOnTheScenarioKeywordByUseingArgumentGivenToFactoryMethod() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "containsScenarioWithNorwegianLanguage");
		Scenario scenario = bddAnalyzer.getCreatedScenarios().get(0);
		Part part = scenario.getParts().get(0);

		assertEquals("gitt tilstandX", part.camelCaseDescription());
	}

	@Test
	public void shouldAddScenarioKeywordMarkedWithIndentToItsParentScenarioPart() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "scenarioWithAnd");
		Scenario scenario = bddAnalyzer.getCreatedScenarios().get(0);
		Part firstPart = scenario.getParts().get(0);
		assertFalse(firstPart.getIndentedParts().isEmpty());
	}

	@Test
	public void shouldResetScenariosBetweenEachRun() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "simpleGivenWhenThen");
		bddAnalyzer.analyze(new TestMethod(TeztTinyTestdataScenarios.class, "noExamples"));
		assertTrue(bddAnalyzer.getCreatedScenarios().isEmpty());
	}

	@Test
	public void shouldCreateTestTableForTinyBddSyntax() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "spexWithExample");
		assertFalse(bddAnalyzer.getCreatedTestTables().isEmpty());
		TestTable testTable = bddAnalyzer.getCreatedTestTables().get(0);
		assertEquals("addOperation", testTable.getSentence());
		assertEquals(columns("operator1", "operator2", "sum"), testTable.getHeaderColumns());
		assertEquals(columns("2", "2", "4"), testTable.getRows().get(0).getColumns());
		assertEquals(columns("3", "2", "5"), testTable.getRows().get(1).getColumns());
	}

	@Test
	public void shouldResetTestTablesBetweenEachRun() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "spexWithExample");
		bddAnalyzer.analyze(new TestMethod(TeztTinyTestdataScenarios.class, "noExamples"));
		assertTrue(bddAnalyzer.getCreatedTestTables().isEmpty());
	}

	@Test
	public void shouldRecognizeTwoScenariosSplittedByThenFollowingGiven() {
		TinyBddAnalyzer bddAnalyzer = analyze(TeztTinyTestdataScenarios.class, "specWithTwoScenarios");
		assertEquals(2, bddAnalyzer.getCreatedScenarios().size());
	}

	// should add table for a scenario, if used in input arg

	// shouldRegisterTesttable

	// shouldRegisterTwoScenariosInTheSameTest

	// test to testtables i samme spec
	// test to scenarioer etterhverandre i samme spec

}
