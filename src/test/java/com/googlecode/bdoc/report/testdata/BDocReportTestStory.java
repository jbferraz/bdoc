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

package com.googlecode.bdoc.report.testdata;

import com.googlecode.bdoc.doc.domain.UserStoryDescription;
import com.googlecode.bdoc.doc.util.ConstantNameToSentenceTranslator;

/**
 * @author Per Otto Bergum Christensen
 */
public enum BDocReportTestStory implements UserStoryDescription {

	STORY_NR_ONE(1, "As a", "I want", "so that1"), // 
	STORY_NR_TWO(2, "As a", "I want", "so that2");

	private Integer id;
	private Narrative narrative;

	BDocReportTestStory(Integer id, String role, String action, String benefit) {
		this.id = id;
		this.narrative = new Narrative(role, action, benefit);
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return ConstantNameToSentenceTranslator.translate(name());
	}

	public Narrative getNarrative() {
		return narrative;
	}
}
