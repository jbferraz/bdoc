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
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassSpecifications;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TableColumn;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.UserStoryDescription.Narrative;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.HTML_REPORT)
public class TestHtmlReport {

	private String html;
	private BDoc bdoc;

	public TestHtmlReport() throws IOException {
		bdoc = BDocTestHelper.bdocWithTwoStoriesThreeScenariosFourSpecificationsGeneralBehaviourAndTestTables();
		html = new HtmlReport(bdoc).html();
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
		assertXPathContains(bdoc.getGeneralBehaviour().getPackages().get(0).getName(), "//div[@id='generalBehaviour']", html);
	}

	@Test
	public void shouldPresentScenariosNotAssociatedWithAnyStories() {
		List<Scenario> scenarios = bdoc.getGeneralBehaviour().getPackages().get(0).getClassBehaviour().get(0).getScenarios();
		assertXPathContains(scenarioPart(0, scenarios.get(0)), "//div[@id='generalBehaviour']/div[@class='package']", html);
	}

	@Test
	public void shouldPresentSpecificationsNotAssociatedWithAnyStories() {
		List<Specification> specifications = bdoc.getGeneralBehaviour().getPackages().get(0).getClassBehaviour().get(0).getSpecifications();
		assertXPathContains(sentence(specifications.get(0)), "//div[@id='generalBehaviour']/div[@class='package']", html);
	}

	@Test
	public void shouldPresentStatementsNotAssociatedWithAnyStories() {
		List<Statement> statements = bdoc.getGeneralBehaviour().getPackages().get(0).getClassBehaviour().get(0).getStatements();
		assertXPathContains(sentence(statements.get(0)), "//div[@id='generalBehaviour']/div[@class='package']", html);
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
	public void shouldPresentHeaderColumnsOfTestTables() {
		List<TestTable> testTables = bdoc.testTables();
		assertXPathContains(testTables.get(0).getHeaderColumns().get(0).getValue().toString(), "//ul[@class='testTable']", html);
	}

	@Test
	public void shouldPresentColumnValuesOfTestTables() {
		List<TableColumn> columns = bdoc.testTables().get(0).getRows().get(0).getColumns();
		assertXPathContains(columns.get(0).getValue().toString(), "//ul[@class='testTable']", html);
		assertXPathContains(columns.get(1).getValue().toString(), "//ul[@class='testTable']", html);
		assertXPathContains(columns.get(2).getValue().toString(), "//ul[@class='testTable']", html);
		
	}

}