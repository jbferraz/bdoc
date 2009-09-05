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

import static com.googlecode.bdoc.doc.domain.TableColumn.columns;
import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;
import static java.util.Arrays.asList;
import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TableRow;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.Scenario.Part;

public class TestStatementExampleFrame {

	private String html;
	private Statement statement;

	public TestStatementExampleFrame() throws IOException {
		TestMethod method = new TestMethod(TestClassWithTestTable.class, "exampleStatement");

		//# Adding testtable
		TestTable testTable = new TestTable("exampleOnSumOfTwoValues", columns("value1", "value2", "sum"));
		testTable.addRow(new TableRow( columns("9912", "88", "10000") ));		
		method.addTestTable(  testTable ) ;
		
		//# Adding simple scenario
		method.addScenario(new Scenario("givenADynamicScenarioWhenActionThenEnsure"));

		//# Adding scenario with indented parts
		Scenario scenarioWithIndentedParts = new Scenario(Scenario.parts("givenStateA", "when", "then"));
		scenarioWithIndentedParts.getParts().get(0).addIndentedPart(new Part("andStateB"));
		scenarioWithIndentedParts.getParts().get(0).addIndentedPart(new Part("andStateC"));
		method.getScenarios().add(scenarioWithIndentedParts);

		//# Adding scenario with testtables as argument in 
		Part partWithTestTableAsArgument = new Part( "given listArgument");
		
		TestTable listArgument = new TestTable("Income", columns("year", "amount" ));
		listArgument.addRow(new TableRow( columns("2000", "90000" ) ));
		listArgument.addRow(new TableRow( columns("2001", "110000" ) ));
		partWithTestTableAsArgument.addArgumentTable(listArgument);
		
		Scenario scenarioArgumentsAsTestTable = new Scenario(asList( partWithTestTableAsArgument ));
		method.getScenarios().add(scenarioArgumentsAsTestTable);
		
		//# Creating statement with a lot of examples put on the method   
		statement = new Statement(method);

		html = new StatementExampleFrame("ClassWithTestTable", statement, new BDocConfig()).html();
		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);
		
		//need css to see what's going on
		writeStringToFile(new File("target/stylesheet.css"), BDocReportUtils.createContentFrom("css.ftl", new HashMap<String, Object>()) );		
	}

	@Test
	public void shouldPresentTestTables() {
		assertXPathContains("Example on sum of two values", "//body", html);
	}

	@Test
	public void shouldPresentDynamicScenarios() {
		assertXPathContains("Given a dynamic scenario", "//ul[@class='scenario']", html);
	}

	@Test
	public void shouldPresentFormatedHeaderColumnsOfTestTables() {
		assertXPathContains("Value 1", "//body", html);
		assertXPathContains("Value 2", "//body", html);
		assertXPathContains("Sum", "//body", html);
	}
	
	@Test
	public void shouldPresentListArgumentForScenarioParts() {
		assertXPathContains("Income", "//body", html);
		assertXPathContains("110000", "//body", html);
	}

	@Test
	public void shouldHaveFileNameBuildtUpFromStatementConcatenatedWithStandardPostfix() {
		StatementExampleFrame statementExamplesFrame = new StatementExampleFrame("ClassWithTestTable", statement, new BDocConfig());
		assertEquals("classwithtesttable-examplestatement-examples_frame.html", statementExamplesFrame.getFileName());
	}

	public class TestClassWithTestTable {

		@Test
		public void exampleStatement() {
		}
	}
}
