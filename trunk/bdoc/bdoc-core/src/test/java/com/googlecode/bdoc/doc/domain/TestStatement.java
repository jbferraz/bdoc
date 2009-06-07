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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;

public class TestStatement {

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionIfASpecIsUsedWithAReferenceByTheCamelCaseSentence() {
		new Statement("behaviourIsSpecifiedByXXX", "spec");
	}

	@Test
	public void shouldTellIfASpecExists() {
		Statement statement = new Statement("behaviourIsSpecifiedBy$spec$", "spec");
		assertTrue(statement.hasSpec());
		assertEquals("spec", statement.getSpec());
	}

	@Test
	public void shouldTellIfASpecDoesNotExists() {
		assertFalse(new Statement("test").hasSpec());
	}

	@Test
	@Ref(Story.NATURAL_LANGUAGE)
	public void shouldReplaceSpecRefInCamelCaseSentenceWithSpecContent() {
		Statement statement = new Statement("behaviourIsSpecifiedBy$spec$", "mySpec");
		assertFalse(statement.getSentence().contains("$spec$"));
		assertTrue(statement.getSentence().contains("mySpec"));
	}

	@Test
	public void shouldAddASpaceBeforeAndAfterAnInsertedSpec() {
		Statement statement = new Statement("behaviourIsSpecifiedBy$spec$IsBest", "mySpec");
		assertEquals("behaviourIsSpecifiedBy mySpec IsBest", statement.getSentence());
	}

	@Test
	public void trailingSpacesShouldBeRemovedFromSentence() {
		assertEquals("statement", new Statement("    statement   ").getSentence());
	}

}
