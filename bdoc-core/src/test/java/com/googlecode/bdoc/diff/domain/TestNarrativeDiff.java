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

package com.googlecode.bdoc.diff.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.NarrativeDiff;
import com.googlecode.bdoc.doc.domain.UserStoryDescription.Narrative;


/**
 *  @author Per Otto Bergum Christensen
 */
@Ref(Story.DIFF_OF_BDOCS)
public class TestNarrativeDiff {

	private final Narrative oldVersion = new Narrative("role1", "action1", "benefit1");
	private final Narrative newVersion = new Narrative("role2", "action2", "benefit2");
	
	@Test
	public void shouldCreateNarrativeDiffForTwoDifferentNarratives() {
		assertNotNull(NarrativeDiff.create(oldVersion, newVersion));
	}

	@Test
	public void shouldNotCreateNarrativeDiffForToEqualNarratives() {
		assertNull(NarrativeDiff.create(oldVersion, oldVersion));
	}
}
