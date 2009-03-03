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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.Validate;

import com.googlecode.bdoc.doc.util.SentenceToLineSplit;

/**
 * @author Per Otto Bergum Christensen
 */
public class Scenario {

	public static final int KEYWORD_COUNT = 3;

	public static class Part {
		private String camelCaseDescription;

		public Part(String camelCaseDescription) {
			this.camelCaseDescription = camelCaseDescription;
		}

		public String camelCaseDescription() {
			return camelCaseDescription;
		}

		@Override
		public boolean equals(Object obj) {
			return ((obj instanceof Part) && (camelCaseDescription.equals(((Part) obj).camelCaseDescription)));
		}

		public int scenarioKeyword() {
			for (Pattern pattern : Pattern.values()) {
				String[] keywords = pattern.keywords;

				for (int index = 0; index < keywords.length; index++) {
					if (camelCaseDescription.toLowerCase().startsWith(keywords[index].toLowerCase())) {
						return index;
					}
				}
			}
			throw new IllegalStateException("Didn't match [" + camelCaseDescription + "] with any scenario keyword");
		}

		@Override
		public String toString() {
			return camelCaseDescription;
		}
	}

	public enum Pattern {
		NO("og", new Locale("no"), "gitt", "Naar", "Saa"), EN("and", Locale.ENGLISH, "given", "When", "Then"), SV("och", new Locale("sv"),
				"givet", "Naar", "Saa");

		private final String and;
		private final String[] keywords;
		private final Locale locale;

		private Pattern(String and, Locale locale, String... template) {
			Validate.isTrue(KEYWORD_COUNT == template.length, "Scenario keywords length should be 3");
			this.keywords = template;
			this.locale = locale;
			this.and = and;
		}

		public Locale locale() {
			return locale;
		}

		public static boolean match(String camelCaseSentence) {
			return (null != find(camelCaseSentence));
		}

		public static Pattern find(String camelCaseSentence) {
			for (Pattern pattern : Pattern.values()) {
				if (camelCaseSentence.startsWith(pattern.keywords[0])) {
					return pattern;
				}
			}
			return null;
		}

		public static int indexOfScenarioKeyword(String string, int scenarioKeyWord) {
			for (Pattern pattern : Pattern.values()) {
				int index = string.indexOf(pattern.keywords[scenarioKeyWord].toLowerCase());
				if (-1 < index) {
					return index;
				}
			}
			return -1;
		}

		public String and() {
			return and;
		}
	}

	private final List<Part> part = new ArrayList<Part>();

	public Scenario(String camelCaseSentence) {
		Pattern pattern = Pattern.find(camelCaseSentence);
		Validate.notNull(pattern, "pattern not found for " + camelCaseSentence);

		for (String ln : SentenceToLineSplit.split(camelCaseSentence, pattern.keywords[1], pattern.keywords[2])) {
			part.add(new Part(ln));
		}
	}

	public Scenario(List<Part> parts) {
		part.addAll(parts);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Scenario) && ((Scenario) obj).part.equals(part);
	}

	public List<Part> getParts() {
		return part;
	}

	@Override
	public String toString() {
		return part.toString();
	}

}
