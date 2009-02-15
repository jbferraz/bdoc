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
import static com.googlecode.bdoc.doc.testdata.BDocTestHelper.SRC_TEST_JAVA;
import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.testdataclasses.TestClassWithOneSpecification;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.DIFF_LOG)
public class TestDiffLogReportBehaviour {

	BDoc emptyBdoc = new BDoc(new ProjectInfo("test", "test"));
	BDoc bdocWithOneSpecification = new BDoc(new ProjectInfo("test", "test"));
	{
		bdocWithOneSpecification.addBehaviourFrom(new TestClass(TestClassWithOneSpecification.class), SRC_TEST_JAVA);
	}

	private DiffLog diffLog = new DiffLog();
	private DiffLogReport diffLogReport = new DiffLogReport();

	private void givenAnEmptyDiffLog() {
		diffLog = new DiffLog();
	}

	private void whenADiffWithOneNewSpecificationIsAddedToTheDiffLog() {
		diffLog.addBDocDiff(new BDocDiff(emptyBdoc, bdocWithOneSpecification));
	}

	private void whenTheReportIsRunOnTheDiffLog() {
		diffLogReport.run(diffLog);
	}

	private void thenEnsureTheReportContainsTheDiffWithTheAddedSpecification() {
		assertXPathContains(sentence(bdocWithOneSpecification.specifications().get(0)), "//diffLogs", diffLogReport.result());
	}

	@Test
	@Ignore
	public void shouldContainBDocDiff() {
		givenAnEmptyDiffLog();
		whenADiffWithOneNewSpecificationIsAddedToTheDiffLog();
		whenTheReportIsRunOnTheDiffLog();
		thenEnsureTheReportContainsTheDiffWithTheAddedSpecification();
	}
}
