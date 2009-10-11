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
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.ClassStatements;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.doc.domain.UserStoryDescription.Narrative;
import com.googlecode.bdoc.doc.report.BDocMacroHelper;
import com.googlecode.bdoc.doc.testdata.RefClass;
import com.googlecode.bdoc.report.testdata.BDocReportTestRef;
import com.googlecode.bdoc.report.testdata.BDocReportTestStory;

@RefClass( SpecificationsFrame.class )
public class TestSpecificationsFrameWithUserstory {

	private String html;
	private UserStory userStory = new UserStory(BDocReportTestStory.STORY_NR_ONE);

	public TestSpecificationsFrameWithUserstory() {
		userStory.addBehaviour(new TestMethod(TestClassWithThatSpecifiesAStory.class, "isTestData"));
		userStory.addBehaviour(new TestMethod(TestClassWithThatSpecifiesAStory.class, "shouldBeSpecified"));
		userStory.addBehaviour(new TestMethod(TestClassWithThatSpecifiesAStory.class, "givenXWhenYThenZ"));
		html = new SpecificationsFrame(userStory, new BDocConfig()).html();
	}

	@Test
	public void shouldPresentTheTitleOfTheStory() {
		assertXPathContains(userStory.getTitle(), "//body", html);
	}
	
	@Test
	public void shouldPresentTheNarrativeOfTheStory() {
		Narrative narrative = userStory.getNarrative();
		assertXPathContains(narrative.getRole(), "//div[@class='narrative']", html);
		assertXPathContains(narrative.getAction(), "//div[@class='narrative']", html);
		assertXPathContains(narrative.getBenefit(), "//div[@class='narrative']", html);
	}

	@Test
	public void shouldPresentTheNameOfTheClassThatHasSpecifiedBehaviour() {
		ClassStatements classStatements = userStory.getClassStatements().get(0);
		String formatedClassName = new BDocMacroHelper(new BDocConfig()).format(classStatements.getClassName());
		assertXPathContains(formatedClassName, "//div[@class='classBehaviour']", html);
	}

	@Test
	public void shouldPresentTheStatementsAssociatedWithTheStory() {
		List<Statement> statements = userStory.getClassStatements().get(0).getStatements();
		assertXPathContains(sentence(statements.get(0)), "//ul[@class='specifications']", html);
	}

	@Test
	public void shouldPresentTheSpecificationsAssociatedWithTheStory() {
		List<Specification> specifications = userStory.getClassSpecifications().get(0).getSpecifications();
		assertXPathContains(sentence(specifications.get(0)), "//ul[@class='specifications']", html);
	}
	
	@Test
	public void shouldHaveFileNameBuildtUpFromUserStoryTitleConcatenatedWithStandardPostfix() {
		SpecificationsFrame userStorySpec = new SpecificationsFrame(userStory, new BDocConfig());
		assertEquals( "story_nr_one_specifications_frame.html", userStorySpec.getFileName() );
	}

	@BDocReportTestRef(BDocReportTestStory.STORY_NR_ONE)
	public class TestClassWithThatSpecifiesAStory {

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
