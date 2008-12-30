/**
 * The MIT License
 * 
 * Copyright (c) 2008 Per Otto Bergum Christensen
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

import java.util.Locale;

/**
 * @author Per Otto Bergum Christensen
 */
public class Specification extends Statement {

	public enum Pattern {
		NO(new Locale("no"), "skal"), EN(Locale.ENGLISH, "should");
		private final String prefix;
		private final Locale locale;

		private Pattern(Locale locale, String prefix) {
			this.prefix = prefix;
			this.locale = locale;
		}

		public Locale locale() {
			return locale;
		}

		public static boolean match(String camelCaseSentence) {
			return (null != find(camelCaseSentence));
		}

		public static Pattern find(String camelCaseSentence) {
			for (Pattern pattern : Pattern.values()) {
				if (camelCaseSentence.startsWith(pattern.prefix)) {
					return pattern;
				}
			}
			return null;
		}
	}

	/**
	 * Constructor
	 * 
	 * @param camelCaseSentence
	 *            for the specification
	 * @throws IllegalArgumentException
	 *             if camelCaseSentence isn't accepted as a specification
	 */
	public Specification(String camelCaseSentence) {
		super(camelCaseSentence );
		validatedPattern(Pattern.find(camelCaseSentence), camelCaseSentence);
	}

	static Pattern validatedPattern(Pattern pattern, String camelCaseSentence) {
		if (null == pattern) {
			throw new IllegalArgumentException("Not accepted as a specification: " + camelCaseSentence);
		}
		return pattern;

	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Specification) && ((Specification) obj).camelCaseSentence.equals(camelCaseSentence);
	}

}
