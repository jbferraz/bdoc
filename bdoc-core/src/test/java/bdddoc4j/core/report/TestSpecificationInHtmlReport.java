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

package bdddoc4j.core.report;

import static bdddoc4j.testutil.HtmlAssert.assertXPathContains;
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.domain.BDoc;
import bdddoc4j.core.domain.TestClass;
import bdddoc4j.core.testdata.BddDocTestHelper;
import bdddoc4j.core.testdata.ExReference;
import bdddoc4j.core.testdata.RefClass;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.HTML_REPORT)
@RefClass(HtmlReport.class)
public class TestSpecificationInHtmlReport {
	
	private String html;

	public TestSpecificationInHtmlReport() throws IOException {
		BDoc bddDoc = new BDoc(org.junit.Test.class, ExReference.class);
		bddDoc.setProject(BddDocTestHelper.testProject());
		bddDoc.addBehaviourFrom(new TestClass(TestClassWithASpecification.class), BddDocTestHelper.SRC_TEST_JAVA);

		html = new HtmlReport(bddDoc).html();
		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);
	}
	
	@Test
	public void shouldConvertTheCamelCaseSpecificationToASentence() {
		assertXPathContains("Should be a specification", "//ul[@class='specifications']", html);		
	}
	
	
	//Testdata =>
	public class TestClassWithASpecification {
		
		@Test
		public void shouldBeASpecification() {
		}
	}

}
