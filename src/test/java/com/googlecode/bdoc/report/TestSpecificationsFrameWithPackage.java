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

import static com.googlecode.bdoc.doc.report.ReportTestHelper.sentence;
import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.ClassStatements;
import com.googlecode.bdoc.doc.domain.Package;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.report.BDocMacroHelper;
import com.googlecode.bdoc.doc.testdata.RefClass;

@RefClass( SpecificationsFrame.class )
public class TestSpecificationsFrameWithPackage {

	private String html;
	private Package _package = new Package(getClass().getPackage());
	
	public TestSpecificationsFrameWithPackage() {
		_package.addBehaviour(new TestMethod(TestClassInAPackage.class, "isTestData"));
		_package.addBehaviour(new TestMethod(TestClassInAPackage.class, "shouldBeSpecified"));
		_package.addBehaviour(new TestMethod(TestClassInAPackage.class, "givenXWhenYThenZ"));
		html = new SpecificationsFrame(_package, new BDocConfig()).html();
		
	}

	@Test
	public void shouldPresentTheNameOfThePackage() {
		assertXPathContains(_package.getName(), "//body", html);
	}
	

	@Test
	public void shouldPresentTheNameOfTheClassThatHasSpecifiedBehaviour() {
		ClassStatements classStatements = _package.getClassStatements().get(0);
		String formatedClassName = new BDocMacroHelper(new BDocConfig()).format(classStatements.getClassName());
		assertXPathContains(formatedClassName, "//div[@class='classBehaviour']", html);
	}

	@Test
	public void shouldPresentTheStatementsAssociatedWithTheStory() {
		List<Statement> statements = _package.getClassStatements().get(0).getStatements();
		assertXPathContains(sentence(statements.get(0)), "//ul[@class='specifications']", html);
	}

	@Test
	public void shouldPresentTheSpecificationsAssociatedWithTheStory() {
		List<Specification> specifications = _package.getClassSpecifications().get(0).getSpecifications();
		assertXPathContains(sentence(specifications.get(0)), "//ul[@class='specifications']", html);
	}
	
	@Test
	public void shouldHaveFileNameBuildtUpFromPackageNameConcatenatedWithStandardPostfix() {
		SpecificationsFrame userStorySpec = new SpecificationsFrame(_package, new BDocConfig());
		
		assertEquals( _package.getName().replace('.', '_') + "_specifications_frame.html", userStorySpec.getFileName() );
	}
	
	public class TestClassInAPackage {

		@Test
		public void isTestData() {
		}

		@Test
		public void shouldBeSpecified() {

		}

		@Test
		public void givenXWhenYThenZ() {
		}
	}
}
