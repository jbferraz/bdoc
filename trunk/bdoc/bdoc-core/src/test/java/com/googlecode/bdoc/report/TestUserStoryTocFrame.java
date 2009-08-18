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

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviourSorter;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.report.testdata.BDocReportTestRef;
import com.googlecode.bdoc.report.testdata.TestClassWithRefToStoryNrOne;
import com.googlecode.bdoc.report.testdata.TestClassWithRefToStoryNrTwoBehaviour;

public class TestUserStoryTocFrame {

	private BDoc bdoc;
	private String html;

	public TestUserStoryTocFrame() {
		bdoc = new BDoc(BDocReportTestRef.class, new ClassBehaviourSorter());
		bdoc.setProject(new ProjectInfo("TestBDocReport", "1.0"));
		bdoc.addBehaviourFrom(new TestClass(TestClassWithRefToStoryNrOne.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestClassWithRefToStoryNrTwoBehaviour.class), BConst.SRC_TEST_JAVA);

		html = new UserStoryTocFrame(bdoc, new BDocConfig() ).html();
	}

	@Test
	public void shouldPresentATableOfContentsWithAllUserStoryTitles() {
		for (UserStory story : bdoc.getUserstories()) {
			assertXPathContains(story.getTitle(), "//ul[@class='toc']", html);
		}
	}

}
