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

import java.util.Locale;

/**
 * @author Per Otto Bergum Christensen
 */
public class CamelCaseToSentenceTranslator {

	public static String translate(String camelCaseSentence) {

		StringBuilder sentence = new StringBuilder();

		boolean firstChar = true;
		for (char character : camelCaseSentence.toCharArray()) {

			if (Character.isUpperCase(character)) {
				sentence.append(' ');
			}

			if (firstChar) {
				sentence.append(Character.toUpperCase(character));
				firstChar = false;
			} else {
				sentence.append(Character.toLowerCase(character));
			}
		}

		StringBuilder sentenceWithNumberFormatted = new StringBuilder();

		boolean lastCharIsNumber = false;
		for (int i = 0; i < sentence.length(); i++) {

			char c = sentence.charAt(i);

			if (number(c) && !lastCharIsNumber) {
				sentenceWithNumberFormatted.append(" ");
				lastCharIsNumber = true;
			} else {
				lastCharIsNumber = number(c);
			}
			sentenceWithNumberFormatted.append(c);
		}

		return sentenceWithNumberFormatted.toString().trim();
	}

	private static boolean number(char c) {
		return "0123456789".contains(String.valueOf(c));
	}

	public static String translate(String camelCaseSentence, Locale locale) {
		String sentence = translate(camelCaseSentence);

		if (Locale.ENGLISH.equals(locale)) {
			return sentence.replaceAll(" i ", " I ");
		}

		return sentence;

	}
}
