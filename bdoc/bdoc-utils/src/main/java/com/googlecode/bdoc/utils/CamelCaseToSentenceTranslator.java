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

package com.googlecode.bdoc.utils;

import java.util.Locale;

/**
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
public class CamelCaseToSentenceTranslator {

	public static final Character SPACE_CHAR = '_';

	public static String translate(String camelCaseSentence) {

		StringBuilder sentence = new StringBuilder();

		boolean firstChar = true;
		Character previousChar = null;
		for (char character : camelCaseSentence.toCharArray()) {

			if (Character.isUpperCase(character) && !SPACE_CHAR.equals(previousChar)) {
				sentence.append(' ');
			}

			if (firstChar) {
				sentence.append(Character.toUpperCase(character));
				firstChar = false;
			} else {
				sentence.append(Character.toLowerCase(character));
			}
			previousChar = character;
		}

		StringBuilder sentenceWithNumberFormatted = new StringBuilder();

		boolean lastCharIsNumber = false;
		for (int i = 0; i < sentence.length(); i++) {

			char c = sentence.charAt(i);

			if (isNumber(c) && !lastCharIsNumber) {
				sentenceWithNumberFormatted.append(" ");
				lastCharIsNumber = true;
			} else {
				lastCharIsNumber = isNumber(c);
			}
			sentenceWithNumberFormatted.append(c);
		}

		return sentenceWithNumberFormatted.toString().replace(SPACE_CHAR, ' ').toString().trim();
	}

	private static boolean isNumber(char c) {
		return "0123456789".contains(String.valueOf(c)) || isSpecialCharacterInNumber(c);
	}

	private static boolean isSpecialCharacterInNumber(char c) {
		return "-.".contains(String.valueOf(c));
	}

	public static String translate(String camelCaseSentence, Locale locale) {
		String sentence = translate(camelCaseSentence);

		if (Locale.ENGLISH.equals(locale)) {
			return sentence.replaceAll(" i ", " I ");
		}

		return sentence;

	}
}
