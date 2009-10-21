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

package com.googlecode.bdoc.difflog;

import static com.googlecode.bdoc.doc.report.ReportTestHelper.sentence;
import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.TestClass;

public class TestDiffLogReport {
	BDoc emptyBdoc = new BDoc(new ProjectInfo("test", "test"));
	BDoc bdocWithTwoSpecifications = new BDoc(new ProjectInfo("test", "test"));
	{
		bdocWithTwoSpecifications.addBehaviourFrom(new TestClass(TestClassWithTwoSpecifications.class), BConst.SRC_TEST_JAVA);
	}
	BDoc bdocWithTwoSpecificationsAndAScenario = new BDoc(new ProjectInfo("test", "test"));
	{
		bdocWithTwoSpecificationsAndAScenario.addBehaviourFrom(new TestClass(TestClassWithTwoSpecifications.class), BConst.SRC_TEST_JAVA);
		bdocWithTwoSpecificationsAndAScenario.addBehaviourFrom(new TestClass(TestClassWithOneScenario.class), BConst.SRC_TEST_JAVA);
	}

	private DiffLog diffLog = new DiffLog();
	{
		diffLog.addBDocDiff(new BDocDiff(emptyBdoc, bdocWithTwoSpecifications));
		diffLog.addBDocDiff(new BDocDiff(bdocWithTwoSpecifications,bdocWithTwoSpecificationsAndAScenario)); 
	}

	private String html;

	public TestDiffLogReport() throws IOException {
		DiffLogReport diffLogReport = new DiffLogReport();
		diffLogReport.run(diffLog, new BDocConfig());
		html = diffLogReport.result();
		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);
	}

	@Test
	public void shouldContainNewSpecificationsInABdocDiff() {
		assertXPathContains(sentence(bdocWithTwoSpecifications.specifications().get(0)), "//div[@class='diffLogs']", html);
		assertXPathContains(sentence(bdocWithTwoSpecifications.specifications().get(1)), "//div[@class='diffLogs']", html);
	}

	public class TestClassWithTwoSpecifications {

		@Test
		public void shouldDoThat1() {
		}

		@Test
		public void shouldDoThat2() {
		}
	}

	public class TestClassWithOneScenario {
		@Test
		public void givenWhenThenThisScenarioShouldBeVisible() {
		}
	}
}
