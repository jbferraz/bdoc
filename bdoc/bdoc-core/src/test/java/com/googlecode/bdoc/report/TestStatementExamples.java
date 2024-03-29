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

package com.googlecode.bdoc.report;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.report.testdata.BDocReportTestStory;

public class TestStatementExamples {

	@Test
	public void shouldAddASpecificationExampleFrameForEachStatementWithExamples() {

		TestMethod method = new TestMethod(TestClassWithTestTable.class, "exampleStatement");
		method.setScenarios(new ArrayList<Scenario>());
		method.getScenarios().add(new Scenario("givenADynamicScenario"));

		UserStory userStory = new UserStory(BDocReportTestStory.STORY_NR_ONE);
		userStory.addBehaviour(method);

		SpecificationExamples specificationExamples = new SpecificationExamples(new BDocConfig());
		specificationExamples.addFrom(userStory);

		StatementExampleFrame expectedExample = new StatementExampleFrame("ClassWithTestTable", new Statement("exampleStatement"),
				new BDocConfig());

		assertTrue(specificationExamples.list().contains(expectedExample));
	}

	@Test
	public void shouldAddASpecificationExampleFrameForEachSpecificationWithExamples() {

		TestMethod method = new TestMethod(TestClassWithTestTable.class, "shouldHaveExamples");
		method.setScenarios(new ArrayList<Scenario>());
		method.getScenarios().add(new Scenario("givenADynamicScenario"));

		UserStory userStory = new UserStory(BDocReportTestStory.STORY_NR_ONE);
		userStory.addBehaviour(method);

		SpecificationExamples specificationExamples = new SpecificationExamples(new BDocConfig());
		specificationExamples.addFrom(userStory);

		StatementExampleFrame expectedExample = new StatementExampleFrame("ClassWithTestTable", new Statement("shouldHaveExamples"),
				new BDocConfig());

		assertTrue(specificationExamples.list().contains(expectedExample));
	}

	public class TestClassWithTestTable {

		@Test
		public void exampleStatement() {
		}

		@Test
		public void shouldHaveExamples() {
		}
	}
}
