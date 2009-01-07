/**
 * The MIT License
 * 
 * Copyright (c) 2008 Per Otto Bergum Christensen
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

import static com.googlecode.bdoc.doc.XmlCustomAssert.assertXPathContains;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.report.BDocReportImpl;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;


@Ref(Story.CREATE_BDOC_FROM_CODE)
public class TestBDocReport {

	private static final String PROJECT_NAME = "testProject";
	
	@Test
	public void shouldExtractDocumentationFromAnyClassThatSpecifiesTests() throws ClassNotFoundException, IOException {
		BDocReport bdocReport = new BDocReportImpl();
		bdocReport.setProjectName(PROJECT_NAME);
		bdocReport.setProjectVersion("version");
		bdocReport.setTestClassDirectory( new File( "target/test-classes"));
		bdocReport.setClassLoader(getClass().getClassLoader() );
		bdocReport.setIncludesFilePattern( new String[] { "integrationtestclasses/stack/**" });
		bdocReport.run( BDocTestHelper.SRC_TEST_JAVA );
		String xml = bdocReport.getXml();
		
		assertXPathContains("Stack", "//bddDoc", xml );
		assertXPathContains("StackBehavior", "//bddDoc", xml );		
	}
}
