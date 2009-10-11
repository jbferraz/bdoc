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

package com.googlecode.bdoc.doc.report;

import static com.googlecode.bdoc.doc.report.ReportTestHelper.scenarioPart;
import static com.googlecode.bdoc.doc.report.ReportTestHelper.sentence;
import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassSpecifications;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.doc.domain.UserStoryDescription.Narrative;
import com.googlecode.bdoc.doc.dynamic.RuntimeBehaviourFactory;
import com.googlecode.bdoc.doc.report.BddDocMacroHelper.TableCellFormatter;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;
import com.googlecode.bdoc.doc.testdata.TestClassWithFourSpecifications;
import com.googlecode.bdoc.doc.testdata.TestClassWithGeneralBehaviour;
import com.googlecode.bdoc.doc.testdata.TestClassWithTestTablesBehaviour;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper.TestClassWithThreeScenariosThreeSpecificationsAndThreeStatements;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.HTML_REPORT)
public class TestHtmlReport {

	private String html;
	private BDoc bdoc;

	public TestHtmlReport() throws IOException {
		bdoc = BDocTestHelper.bdocWithProject();
		bdoc.addBehaviourFrom(new TestClass(TestClassWithThreeScenariosThreeSpecificationsAndThreeStatements.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestClassWithFourSpecifications.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestClassWithGeneralBehaviour.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestClassWithTestTablesBehaviour.class), new RuntimeBehaviourFactory(BConst.SRC_TEST_JAVA));

		HtmlReport htmlReport = new HtmlReport(bdoc);

		TableCellFormatter customClassFormatter = new TableCellFormatter() {
			public String format(Object object) {
				return "custom";
			}
		};

		HashMap<Class<?>, TableCellFormatter> tableCellFormatters = new HashMap<Class<?>, TableCellFormatter>();
		tableCellFormatters.put(Class.class, customClassFormatter);
		htmlReport.setCustomObjectFormatters(tableCellFormatters);

		html = htmlReport.html();

		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);
		writeStringToFile(new File("target/" + getClass().getName() + ".xml"), new XmlReport(bdoc).xml());
	}

	@Test
	public void shouldContainNameOfTheProjectNameInTheTitleAndHeader() {
		assertXPathContains(bdoc.getProject().getName(), "//title", html);
		assertXPathContains(bdoc.getProject().getName(), "//h1", html);
	}

	@Test
	public void shouldPresentTheNarrativeOfTheStory() {
		Narrative narrative = bdoc.getUserstories().get(0).getNarrative();
		assertXPathContains(narrative.getRole(), "//div[@class='userstory']", html);
		assertXPathContains(narrative.getAction(), "//div[@class='userstory']", html);
		assertXPathContains(narrative.getBenefit(), "//div[@class='userstory']", html);
	}

	@Test
	public void shouldPresentTheScenariosOfTheStory() {
		Scenario scenario = bdoc.getUserstories().get(0).getScenarios().get(0);
		assertXPathContains(scenarioPart(0, scenario), "//ul[@class='scenario']", html);
		assertXPathContains(scenarioPart(1, scenario), "//ul[@class='scenario']", html);
		assertXPathContains(scenarioPart(2, scenario), "//ul[@class='scenario']", html);
	}

	@Test
	public void shouldPresentTheSpecificationsAssociatedWithTheStory() {
		List<Specification> specifications = bdoc.getUserstories().get(0).getClassSpecifications().get(0).getSpecifications();
		assertXPathContains(sentence(specifications.get(0)), "//ul[@class='specifications']", html);
	}

	@Test
	public void shouldPresentTheStatementsAssociatedWithTheStory() {
		List<Statement> statements = bdoc.getUserstories().get(0).getClassStatements().get(0).getStatements();
		assertXPathContains(sentence(statements.get(0)), "//ul[@class='statements']", html);
	}

	@Test
	public void shouldPresentTheNameOfTheClassWithTheSpecifications() {
		ClassSpecifications classBehaviour = bdoc.getUserstories().get(0).getClassSpecifications().get(0);
		assertXPathContains(classBehaviour.getClassName(), "//ul[@class='specifications']", html);
	}

	@Test
	public void shouldPresentPackagesWithBehaviourNotAssociatedWityAnyStories() {
		assertXPathContains(bdoc.getGeneralBehaviour().getPackages().get(0).getName(), "//div[@class='generalBehaviour']", html);
	}

	@Test
	public void shouldPresentScenariosNotAssociatedWithAnyStories() {
		List<Scenario> scenarios = bdoc.getGeneralBehaviour().getPackages().get(0).getClassBehaviour().get(0).getScenarios();
		assertXPathContains(scenarioPart(0, scenarios.get(0)), "//div[@class='generalBehaviour']/div[@class='package']", html);
	}

	@Test
	public void shouldPresentSpecificationsNotAssociatedWithAnyStories() {
		List<Specification> specifications = bdoc.getGeneralBehaviour().getPackages().get(0).getClassBehaviour().get(0).getSpecifications();
		assertXPathContains(sentence(specifications.get(0)), "//div[@class='generalBehaviour']/div[@class='package']", html);
	}

	@Test
	public void shouldPresentStatementsNotAssociatedWithAnyStories() {
		List<Statement> statements = bdoc.getGeneralBehaviour().getPackages().get(0).getClassBehaviour().get(0).getStatements();
		assertXPathContains(sentence(statements.get(0)), "//div[@class='generalBehaviour']/div[@class='package']", html);
	}

	@Test
	public void shouldBePossibleToChangeFormattingForAdvancedScenarioSpecification() throws IOException {
		bdoc = BDocTestHelper.bdocWithAdvancedScenarioSpecification();
		String htmlAndInBetween = new HtmlReport(bdoc, new AndInBetweenScenarioLinesFormatter()).html();
		String htmlEachOnNewLine = new HtmlReport(bdoc, new EachOnNewLineScenarioLinesFormatter()).html();
		Assert.assertFalse(htmlAndInBetween.equals(htmlEachOnNewLine));
	}

	@Test
	public void shouldPresentTestTablesForGeneralBehaviour() {
		List<TestTable> testTables = bdoc.testTables();
		assertXPathContains(sentence(testTables.get(0)), "//ul[@class='testTable']", html);
	}

	@Test
	public void shouldPresentFormatedHeaderColumnsOfTestTables() {
		List<TestTable> testTables = bdoc.testTables();
		assertXPathContains(sentence(testTables.get(0).getHeaderColumns().get(0).getValue().toString()), "//ul[@class='testTable']", html);
	}

	@Test
	public void shouldPresentColumnValuesOfTestTablesWithATableCellFormatter() {
		assertXPathContains("custom", "//div[@class='exampleOnTypeConversionSuppert']", html);
		assertXPathContains("custom", "//div[@class='exampleOnTypeConversionSuppert']", html);
	}
	@Test
	public void shouldPresentATableOfContentsWithAllUserStoryTitles() {
		List<UserStory> stories = bdoc.getUserstories();
		for (UserStory story : stories) {
			assertXPathContains(story.getTitle(), "//ul[@class='toc']", html);
		}
	}
	@Test
	public void shouldAddBackToTopLinkWithAllUserStories() {
		List<UserStory> stories = bdoc.getUserstories();
		for (int i=0; i < stories.size(); i++) {
			assertXPathContains("< Back to top", "//a[@href=\"#top\"]", html);
		}
	}
}