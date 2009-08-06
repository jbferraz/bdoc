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

import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviourSorter;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;
import com.googlecode.bdoc.doc.testdata.ExReference;
import com.googlecode.bdoc.doc.testdata.RefClass;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.HTML_USERSTORY_REPORT)
@RefClass(UserStoryHtmlReport.class)
public class TestTextRenderingInHtmlReport {

	private String html;

	public TestTextRenderingInHtmlReport() throws IOException {
		BDoc bddDoc = new BDoc(ExReference.class, new ClassBehaviourSorter());
		bddDoc.setProject(BDocTestHelper.testProject());
		bddDoc.addBehaviourFrom(new TestClass(TestClassWithASpecification.class), BConst.SRC_TEST_JAVA);
		bddDoc.addBehaviourFrom(new TestClass(TestClassWithAStatement.class), BConst.SRC_TEST_JAVA);

		html = new ModuleBehaviourReport(bddDoc,new BDocConfig()).html();
		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);
	}

	@Test
	public void shouldConvertTheCamelCaseSpecificationToASentence() {
		assertXPathContains("Should be a specification", "//div[@class='classBehaviour']", html);
	}

	@Test
	public void shouldConvertTheCamelCaseStatementToASentence() {
		assertXPathContains("This is a statement", "//div[@class='classBehaviour']", html);
	}

	// Testdata =>
	public class TestClassWithASpecification {
		@Test
		public void shouldBeASpecification() {
		}
	}

	public class TestClassWithAStatement {
		@Test
		public void thisIsAStatement() {
		}
	}

}
