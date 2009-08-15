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

package com.googlecode.bdoc.doc.analyzer;

import java.util.Locale;

import org.apache.commons.lang.Validate;

/**
 * @author Micael Vesterlund
 */
public class Scenario {

	private String camelCaseSentence;

	public Scenario(String camelCaseSentence) {
		this.camelCaseSentence = camelCaseSentence;
	}

	public String getCamelCaseSentence() {
		return camelCaseSentence;
	}

	public enum Pattern {
		NO(new Locale("no"), "og", "gitt", "Naar", "Saa"), EN(Locale.ENGLISH, "and", "given", "When", "Then"), SV(new Locale("sv"), "och",
				"givet", "Naar", "Saa");

		public static final int KEYWORD_COUNT = 3;
		private final String and;
		private final String[] keywords;
		private final Locale locale;

		private Pattern(Locale locale, String and, String... keywords) {
			Validate.isTrue(KEYWORD_COUNT == keywords.length, "Scenario keywords length should be 3");
			this.keywords = keywords;
			this.locale = locale;
			this.and = and;
		}

		public Locale locale() {
			return locale;
		}

		public static boolean isScenario(String camelCaseSentence) {
			for (Pattern pattern : Pattern.values()) {
				for (String keyword : pattern.keywords) {
					if (camelCaseSentence.startsWith(keyword.toLowerCase())) {
						return true;
					}
				}
			}
			return false;
		}

		public String and() {
			return and;
		}
	}

}
