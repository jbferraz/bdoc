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
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.Scenario.Part;
import com.googlecode.bdoc.doc.tinybdd.testdata.TeztTinyTestdataScenarios;

public class TestTinyBddAnalyzer {

	private BddAnalyzer bddAnalyzer = new BddAnalyzer(BConst.SRC_TEST_JAVA);

	@Test
	public void shouldReturnSourceTestJava() {
		assertEquals(BConst.SRC_TEST_JAVA, bddAnalyzer.sourceTestDirectory());
	}

	@Test
	public void shouldCreateAScenarioForSimpleTinyBddSyntax() {
		bddAnalyzer.analyze(new TestClass(TeztTinyTestdataScenarios.class).getTestMethod("simpleGivenWhenThen"));
		assertFalse(bddAnalyzer.getCreatedScenarios().isEmpty());

		List<Part> expectedParts = asList(part("Given criteriaA"), part("When actionA"), part("Then ensureA"));
		assertEquals(new Scenario(expectedParts), bddAnalyzer.getCreatedScenarios().get(0));
	}

	@Test
	public void shouldNotAffectInternalStateOfTheTestUnderAnalyze() {
		bddAnalyzer.analyze(new TestClass(TeztTinyTestdataScenarios.class).getTestMethod("willFailIfStateIsNotHandledByBddAnalyzer"));
	}
	
	//shouldRegisterTwoScenariosInTheSameTest
	
	//should add table for a scenario, if used in input arg
	
	//shouldRegisterTesttable

}
