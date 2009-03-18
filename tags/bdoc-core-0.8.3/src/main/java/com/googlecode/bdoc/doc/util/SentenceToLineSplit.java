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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Per Otto Bergum Christensen
 */
public class SentenceToLineSplit {

	/**
	 * Splits a sentence into an array of strings, e.g: split("givenWhenThen",
	 * "When","Then") gives ["given", "When", "Then"]
	 * 
	 * @param sentence to split
	 * @param seperators to split on
	 * @return result split into array of string
	 */
	public static List<String> split(String sentence, String... seperators) {
		List<String> result = new ArrayList<String>();

		String restSentence = sentence;
		for (String seperator : seperators) {
			int seperatorIndex = restSentence.indexOf(seperator);

			if (-1 < seperatorIndex) {
				result.add(restSentence.substring(0, seperatorIndex));
				restSentence = restSentence.substring(seperatorIndex);
			}
		}
		result.add(restSentence);

		return result;
	}

}
