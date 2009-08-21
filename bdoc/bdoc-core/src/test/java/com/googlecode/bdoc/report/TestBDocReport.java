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
import com.googlecode.bdoc.report.testdata.BDocReportTestRef;
import com.googlecode.bdoc.report.testdata.TestClassWithRefToStoryNrOne;
import com.googlecode.bdoc.report.testdata.TestClassWithRefToStoryNrTwoBehaviour;

public class TestBDocReport {

	File baseDir = new File("./target");
	File reportDirectory = new File(baseDir, "bdoc");

	public TestBDocReport() throws IOException {
		if (reportDirectory.exists()) {
			FileUtils.forceDelete(reportDirectory);
		}
		BDoc bdoc = new BDoc(BDocReportTestRef.class, new ClassBehaviourSorter());
		bdoc.setProject(new ProjectInfo("TestBDocReport", "1.0"));
		bdoc.addBehaviourFrom(new TestClass(TestClassWithRefToStoryNrOne.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestClassWithRefToStoryNrTwoBehaviour.class), BConst.SRC_TEST_JAVA);

		BDocReport bdocReport = new BDocReport(bdoc, new BDocConfig());
		bdocReport.writeTo(baseDir);
	}

	@Test
	public void shouldCreateBDocDirectoryInTargetDirectory() {
		assertTrue(reportDirectory.exists());
	}

	@Test
	public void shouldCreateIndexHtmlInBDocReportDirectory() {
		assertTrue(new File(reportDirectory, "index.html").exists());
	}

	@Test
	public void shouldCreateProjectInfoHtmlInBDocReportDirectory() {
		assertTrue(new File(reportDirectory, "project_info.html").exists());
	}

	@Test
	public void shouldCreateABlankHtmlToBeUsedTogheterWithProjectInfo() {
		assertTrue(new File(reportDirectory, "blank.html").exists());
	}

	@Test
	public void shouldCreateUserStoriesTocFrameInBDocReportDirectory() {
		assertTrue(new File(reportDirectory, "user_story_toc_frame.html").exists());
	}

	@Test
	public void shouldCreateAUserStorySpecificationFrameForEachUserStory() {
		assertTrue(new File(reportDirectory, "story_nr_one_specifications_frame.html").exists());
		assertTrue(new File(reportDirectory, "story_nr_two_specifications_frame.html").exists());
	}
	
	@Test
	public void shouldCreateUserStoriesExamplesFrameInBDocReportDirectory() {
		assertTrue(new File(reportDirectory, "user_story_examples_frame.html").exists());
	}
	

}
