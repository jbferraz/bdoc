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

package com.googlecode.bdoc.doc.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.bdoc.doc.domain.UserStoryDescriptionWrapper;
import com.googlecode.bdoc.doc.domain.UserStoryDescription.Narrative;
import com.googlecode.bdoc.doc.testdata.ExStory2;

/**
 *  @author Per Otto Bergum Christensen
 */
public class TestUserStoryDescriptionWrapper {

	private UserStoryDescriptionWrapper userStoryDescriptionWrapper = new UserStoryDescriptionWrapper(ExStory2.TEST_STORY);

	@Test
	public void shouldReturnTheIdValue() {
		assertEquals(new Integer(1), userStoryDescriptionWrapper.getId());
	}

	@Test
	public void shouldReturnTheTranslatedStoryNameToATitle() {
		assertEquals("Test story", userStoryDescriptionWrapper.getTitle());
	}
	
	@Test
	public void shouldReturnTheNarrativeObject()
	{
		assertEquals( new Narrative("As a","I want","so that"), userStoryDescriptionWrapper.getNarrative());
	}

}
