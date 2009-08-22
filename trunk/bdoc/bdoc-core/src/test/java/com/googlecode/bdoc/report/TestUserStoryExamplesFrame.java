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

import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TableColumn;
import com.googlecode.bdoc.doc.domain.TableRow;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.report.testdata.BDocReportTestStory;

public class TestUserStoryExamplesFrame {

	private String html;
	private UserStory userStory = new UserStory(BDocReportTestStory.STORY_NR_ONE);

	public TestUserStoryExamplesFrame() {
		TestMethod method = new TestMethod(TestClassWithTestTable.class, "shouldAddTwoNumbers");

		TestTable testTable = new TestTable("exampleOnSumOfTwoValues");
		testTable.addHeaderColumn(new TableColumn("value1"));
		testTable.addHeaderColumn(new TableColumn("value2"));
		testTable.addHeaderColumn(new TableColumn("sum"));
		testTable.addRow(new TableRow(new TableColumn("9912"), new TableColumn("88"), new TableColumn("10000")));

		List<TestTable> testTables = new ArrayList<TestTable>();
		testTables.add(testTable);

		method.setTestTables(testTables);

		method.setScenarios(new ArrayList<Scenario>());
		method.getScenarios().add(new Scenario("givenADynamicScenario"));

		userStory.addBehaviour(method);

		html = new UserStoryExamplesFrame(userStory, new BDocConfig()).html();
	}

	@Test
	public void shouldPresentTestTablesForSpecifications() {
		assertXPathContains("Example on sum of two values", "//ul[@class='testTable']", html);
	}

	@Test
	public void shouldPresentDynamicScenarios() {
		assertXPathContains("Given a dynamic scenario", "//ul[@class='scenario']", html);
	}

	@Test
	public void shouldPresentFormatedHeaderColumnsOfTestTables() {
		assertXPathContains("Value 1", "//ul[@class='testTable']", html);
		assertXPathContains("Value 2", "//ul[@class='testTable']", html);
		assertXPathContains("Sum", "//ul[@class='testTable']", html);
	}
	
	@Test
	public void shouldHaveFileNameBuildtUpFromUserStoryTitleConcatenatedWithStandardPostfix() {
		UserStoryExamplesFrame userStoryExamples = new UserStoryExamplesFrame(userStory, new BDocConfig());
		assertEquals( "story_nr_one_examples_frame.html", userStoryExamples.getFileName() );
	}
	

	public class TestClassWithTestTable {

		@Test
		public void shouldAddTwoNumbers() {
		}
	}
}
