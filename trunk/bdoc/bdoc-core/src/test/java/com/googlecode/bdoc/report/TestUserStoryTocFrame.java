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
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.report.testdata.BDocReportTestStory;

public class TestUserStoryTocFrame {

	List<UserStorySpecificationsFrame> userStorySpecFrames = new ArrayList<UserStorySpecificationsFrame>();
	private String html;

	public TestUserStoryTocFrame() {
		userStorySpecFrames.add(new UserStorySpecificationsFrame(new UserStory(BDocReportTestStory.STORY_NR_ONE), new BDocConfig()));
		userStorySpecFrames.add(new UserStorySpecificationsFrame(new UserStory(BDocReportTestStory.STORY_NR_TWO), new BDocConfig()));

		html = new UserStoryTocFrame(userStorySpecFrames, new BDocConfig()).html();
	}

	@Test
	public void shouldPresentATableOfContentsWithAllUserStoryTitles() {
		assertXPathContains("Story nr one", "//ul[@class='toc']", html);
		assertXPathContains("Story nr two", "//ul[@class='toc']", html);
	}

}