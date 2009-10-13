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
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.ClassBehaviourSorter;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.tinybdd.TinyBddAnalyzer;
import com.googlecode.bdoc.report.testdata.StatementExampleFrameTestExampleBehaviour;

public class TestStatementExampleFrameIntegration {

	private String html;
	private Statement statement;

	public TestStatementExampleFrameIntegration() throws IOException {

		BDoc bdoc = new BDoc(null, new ClassBehaviourSorter());
		bdoc.addBehaviourFrom(new TestClass(StatementExampleFrameTestExampleBehaviour.class),
				new TinyBddAnalyzer(BConst.SRC_TEST_JAVA));

		ClassBehaviour classBehaviour = bdoc.classBehaviourInModuleBehaviour(StatementExampleFrameTestExampleBehaviour.class);
		statement = classBehaviour.getStatements().get(0);

		html = new StatementExampleFrame("ClassWithTestTable", statement, new BDocConfig()).html();
		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);

		// need css to see what's going on
		writeStringToFile(new File("target/stylesheet.css"), BDocReportUtils.createContentFrom("css.ftl",
				new HashMap<String, Object>()));
	}

	@Test
	@Ignore("Todo: Add support for list with primitives")
	public void aListWithPrimitivesAsArgumentToAScenarioPartShouldBePresented() {
		assertXPathContains("ItemA", "//body", html);
		assertXPathContains("ItemB", "//body", html);
		assertXPathContains("ItemC", "//body", html);
	}

	@Test
	public void aListWithObjectsAsArgumentToAScenarioPartShouldBePresented() {

	}

}
