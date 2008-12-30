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

package com.googlecode.bdoc.doc.domain;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.Scenario.Part;
import com.googlecode.bdoc.doc.domain.testdata.TestDomainBehavior;
import com.googlecode.bdoc.doc.domain.testdata.TestDomainBehaviour;
import com.googlecode.bdoc.doc.testdata.BddDocTestHelper;


public class TestTestClass {

	@Test
	public void shouldSpecifyCheckForScenariosInTestMethodBlockWhenTestClassHasPostfixBehaviour() {
		TestClass testClass = new TestClass(TestDomainBehaviour.class);
		assertTrue(testClass.isMarkedAsContainerOfScenariosSpecifiedInTestMethodBlocks());
	}

	@Test
	public void shouldSpecifyCheckForScenariosInTestMethodBlockWhenTestClassHasPostfixBehavior() {
		TestClass testClass = new TestClass(TestDomainBehavior.class);
		assertTrue(testClass.isMarkedAsContainerOfScenariosSpecifiedInTestMethodBlocks());
	}

	@Test
	public void shouldLoadTheJavaSourceForTheTestClass() {
		String source = new TestClass(TestDomainBehaviour.class).getSource(BddDocTestHelper.SRC_TEST_JAVA);
		assertTrue(source.contains("DomainBehaviour"));
	}

	@Test
	public void shouldGetScenarioFromTestMethodBlock() {
		TestClass testClass = new TestClass(TestDomainBehaviour.class);
		Scenario scenarioFromTestMethodBlock = testClass.getScenarioFromTestMethodBlock("simpleScenario", BddDocTestHelper.SRC_TEST_JAVA);

		List<Part> parts = new ArrayList<Part>();
		parts.add(new Part("given"));
		parts.add(new Part("when"));
		parts.add(new Part("then"));
		
		assertEquals(new Scenario( parts ), scenarioFromTestMethodBlock );
	}

}
