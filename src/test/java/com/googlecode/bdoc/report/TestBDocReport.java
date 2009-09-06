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

import static com.googlecode.bdoc.doc.util.TestMethodReferenceFinder.findTestMethodReferenceFor;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviourSorter;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.dynamic.RuntimeBehaviourFactory;
import com.googlecode.bdoc.report.testdata.BDocReportTestRef;
import com.googlecode.bdoc.report.testdata.TestClassWithRefToStoryNrOne;
import com.googlecode.bdoc.report.testdata.TestClassWithRefToStoryNrTwoBehaviour;

public class TestBDocReport {

	RuntimeBehaviourFactory behaviourFactory = new RuntimeBehaviourFactory(BConst.SRC_TEST_JAVA);
	File baseDir = new File("./target");
	File reportDirectory = new File(baseDir, "bdoc");

	public TestBDocReport() throws IOException {
		if (reportDirectory.exists()) {
			FileUtils.forceDelete(reportDirectory);
		}
		BDoc bdoc = new BDoc(BDocReportTestRef.class, new ClassBehaviourSorter());
		bdoc.setProject(new ProjectInfo("TestBDocReport", "1.0"));

		bdoc.addBehaviourFrom(new TestClass(TestClassWithRefToStoryNrOne.class), behaviourFactory);

		TestClass testClass = new TestClass(TestClassWithRefToStoryNrTwoBehaviour.class);
		testClass.registerTestMethodReferences(findTestMethodReferenceFor(testClass, BConst.SRC_TEST_JAVA));
		bdoc.addBehaviourFrom(testClass, behaviourFactory);

		new BDocReport(bdoc, new BDocConfig()).writeTo(baseDir);
	}

	@Test
	public void shouldCreateBDocDirectoryInTargetDirectory() {
		assertTrue(reportDirectory.exists());
	}

	@Test
	public void shouldCreateIndexHtmlInTheBDocReportDirectory() {
		assertReportIsCreated("index.html");
	}

	@Test
	public void shouldCreateACascadingStyleSheetInTheBDocReportDirectory() {
		assertReportIsCreated("stylesheet.css");
	}

	@Test
	public void shouldCreateProjectInfoHtmlInTheBDocReportDirectory() {
		assertReportIsCreated("project_info.html");
	}

	@Test
	public void shouldCreateABlankHtmlToBeUsedTogheterWithProjectInfo() {
		assertReportIsCreated("blank.html");
	}

	@Test
	public void shouldCreateUserStoriesTocFrameInBDocReportDirectory() {
		assertReportIsCreated("user_story_toc_frame.html");
	}

	@Test
	public void shouldCreateAUserStorySpecificationFrameForEachUserStory() {
		assertReportIsCreated("story_nr_one_specifications_frame.html");
		assertReportIsCreated("story_nr_two_specifications_frame.html");
	}


	@Test
	public void shouldCreateAnExampleFrameForEachStatementContaingExamples() {
		assertReportIsCreated("classwithreftostorynrtwobehaviour-specforstory2-examples_frame.html");
		assertReportIsCreated("classwithreftostorynrtwobehaviour-specwithtable-examples_frame.html");
	}

	void assertReportIsCreated(String reportFileName) {
		assertTrue(new File(reportDirectory, reportFileName).exists());
	}

}
