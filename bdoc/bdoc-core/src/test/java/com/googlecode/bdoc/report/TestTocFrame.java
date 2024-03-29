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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.Package;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.report.testdata.BDocReportTestStory;

public class TestTocFrame {

	@Test
	public void shouldPresentATableOfContentsWithAllUserStoryTitles() {
		List<SpecificationsFrame> userStorySpecFrames = new ArrayList<SpecificationsFrame>();

		userStorySpecFrames.add(new SpecificationsFrame(new UserStory(BDocReportTestStory.STORY_NR_ONE), new BDocConfig()));
		userStorySpecFrames.add(new SpecificationsFrame(new UserStory(BDocReportTestStory.STORY_NR_TWO), new BDocConfig()));

		String html = new TocFrame("toc.userstories", userStorySpecFrames, new BDocConfig()).html();

		assertXPathContains("Story nr one", "//body", html);
		assertXPathContains("Story nr two", "//body", html);
	}

	@Test
	public void shouldPresentATableOfContentsWithAllPackages() {
		List<SpecificationsFrame> specFrames = new ArrayList<SpecificationsFrame>();

		specFrames.add(new SpecificationsFrame(new Package("my.package.one"), new BDocConfig()));
		specFrames.add(new SpecificationsFrame(new Package("my.package.two"), new BDocConfig()));

		String html = new TocFrame("toc.packages", specFrames, new BDocConfig()).html();

		assertXPathContains("my.package.one", "//body", html);
		assertXPathContains("my.package.two", "//body", html);
	}

	@Test
	public void headerShouldBeSetByHeaderKey() {
		String html = new TocFrame("toc.packages", new ArrayList<SpecificationsFrame>(), new BDocConfig()).html();
		assertXPathContains("Modules", "//body", html);
	}

}
