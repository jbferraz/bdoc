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

package bdddoc4j.core.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;

import bdddoc4j.core.util.CamelCaseToSentenceTranslator;
import bdddoc4j.core.util.SentenceToLineSplit;

/**
 * @author Per Otto Bergum Christensen
 */
public class Scenario  {

	public enum Pattern {
		NO("gitt", "Naar", "Saa"), EN("given", "When", "Then");
		private String[] template;

		private Pattern(String... template) {
			this.template = template;
		}

		public static boolean match(String camelCaseSentence) {
			return (null != find(camelCaseSentence));
		}

		public static Pattern find(String camelCaseSentence) {
			for (Pattern pattern : Pattern.values()) {
				if (camelCaseSentence.startsWith(pattern.template[0])) {
					return pattern;
				}
			}
			return null;
		}
	}

	private final List<String> line = new ArrayList<String>();

	public Scenario(String camelCaseSentence) {
		super();

		Pattern pattern = Pattern.find(camelCaseSentence);
		Validate.notNull(pattern, "pattern not found for " + camelCaseSentence);

		for (String ln : SentenceToLineSplit.split(camelCaseSentence, pattern.template[1], pattern.template[2])) {
			line.add(CamelCaseToSentenceTranslator.translate(ln));
		}
	}

	public List<String> getLines() {
		return Collections.unmodifiableList(line);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Scenario) && ((Scenario) obj).line.equals(line);
	}
}
