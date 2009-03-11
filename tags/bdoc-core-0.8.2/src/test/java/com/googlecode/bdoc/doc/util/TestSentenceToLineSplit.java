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

package com.googlecode.bdoc.doc.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.doc.util.SentenceToLineSplit;


/**
 * @author Per Otto Bergum Christensen
 */
public class TestSentenceToLineSplit {

	@Test
	public void givenASentenceWhenSplitWithASetOfSeperatorsThenEachSeperatorShouldResultInANewLine()
	{
		String sentence = "givenASentenceWhenSplitWithASetOfSeperatorsThenEachSeperatorShouldResultInANewLine";
		List<String> result = SentenceToLineSplit.split( sentence, "When","Then");
		assertEquals("givenASentence", result.get(0));
		assertEquals("WhenSplitWithASetOfSeperators", result.get(1));
		assertEquals("ThenEachSeperatorShouldResultInANewLine", result.get(2));
	}
	
	@Test
	public void shouldSplitAStringOfTwoWordsIntoTwoStringsWhenTheSeperatorIsFoundInTheSentence()
	{
		List<String> result = SentenceToLineSplit.split( "givenThen", "Then");
		assertEquals(2, result.size());
		assertEquals("given", result.get(0));
		assertEquals("Then", result.get(1));
	}

	@Test
	public void shouldSplitAStringOfThreeWordsIntoThreeStringsWhenTheTwoSeperatorIsFoundInTheSentence()
	{
		List<String> result = SentenceToLineSplit.split( "givenWhenThen", "When","Then");
		assertEquals(3, result.size());
		assertEquals("given", result.get(0));
		assertEquals("When", result.get(1));
		assertEquals("Then", result.get(2));
	}

	@Test
	public void shouldNotSplitAStringIfTheSeperatorIsNotFoundInTheSentence()
	{
		List<String> result = SentenceToLineSplit.split( "givenThen", "When");
		assertEquals(1, result.size());
		assertEquals("givenThen", result.get(0));
	}
}
