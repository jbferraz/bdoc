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

import static com.googlecode.bdoc.doc.domain.TestTable.createTestTableFromObjectList;

import java.util.ArrayList;
import java.util.Collection;
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
		private List<TestTable> argumentTables = new ArrayList<TestTable>();
		private List<Part> indentedParts = new ArrayList<Part>();

		public Part(String camelCaseDescription) {
			this.camelCaseDescription = camelCaseDescription;
		}

		public String camelCaseDescription() {
			return camelCaseDescription;
		}

		@Override
		public boolean equals(Object obj) {
			return ((obj instanceof Part) && (camelCaseDescription.equalsIgnoreCase(((Part) obj).camelCaseDescription)));
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

		public List<TestTable> getArgumentTables() {
			return argumentTables;
		}

		public void addArgumentTable(TestTable testTable) {
			argumentTables.add(testTable);
		}

		public void appendArgument(Object arg ) {
			camelCaseDescription = camelCaseDescription + " " + arg;
		}
		public void appendListArgument(String tableDescription, Collection<? extends Object> collection) {
			camelCaseDescription = camelCaseDescription + " " + tableDescription;
			addArgumentTable(createTestTableFromObjectList(tableDescription, collection) );			
		}

		public boolean hasIndentedParts() {
			return !indentedParts.isEmpty();
		}

		public List<Part> getIndentedParts() {
			return indentedParts;
		}

		public void addIndentedPart(Part scenarioPart) {
			indentedParts.add(scenarioPart);
		}
	}

	public enum Pattern {
		NO("og", new Locale("no"), "gitt", "Naar", "Saa"), EN("and", Locale.ENGLISH, "given", "When", "Then"), SV("och", new Locale(
				"sv"), "givet", "Naar", "Saa");

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

		public static boolean match(TestMethod testMethod) {
			return (null != find(testMethod.camelCaseSentence()));
		}

		public static Pattern find(String camelCaseSentence) {
			for (Pattern pattern : Pattern.values()) {
				for (String keyword : pattern.keywords) {
					if (camelCaseSentence.toLowerCase().startsWith(keyword.toLowerCase())) {
						return pattern;
					}
				}
			}
			return null;
		}

		public boolean keywordMatch(String camelCaseSentence) {
			for (String keyword : keywords) {
				if (camelCaseSentence.toLowerCase().startsWith(keyword.toLowerCase())) {
					return true;
				}
			}
			return false;
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

	private final List<Part> parts = new ArrayList<Part>();	

	public Scenario(String camelCaseSentence) {
		Pattern pattern = Pattern.find(camelCaseSentence);
		Validate.notNull(pattern, "pattern not found for " + camelCaseSentence);

		for (String ln : SentenceToLineSplit.split(camelCaseSentence, pattern.keywords[1], pattern.keywords[2])) {
			parts.add(new Part(ln));
		}
	}

	public Scenario(List<Part> parts) {
		Validate.isTrue(!parts.isEmpty(), "Can't create scenario with no parts");
		this.parts.addAll(parts);
	}

	public Scenario(TestMethod testMethod) {
		this(testMethod.camelCaseSentence());
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Scenario) && ((Scenario) obj).parts.equals(parts);
	}

	public List<Part> getParts() {
		return parts;
	}

	@Override
	public String toString() {
		return parts.toString();
	}

	public void addPart(Part scenarioPart, boolean indented) {
		if (indented) {
			parts.get(parts.size() - 1).addIndentedPart(scenarioPart);
		} else {
			parts.add(scenarioPart);
		}
	}
	
	public List<TestTable> getArgumentTables() {
		List<TestTable> result = new ArrayList<TestTable>();
		for (Part part : parts )  {
			result.addAll(part.getArgumentTables());
			
			for (Part indentedPart : part.getIndentedParts()) {
				result.addAll(indentedPart.getArgumentTables());
			}
		}
		return result;
	}
	
	public boolean hasArgumentTables() {
		return !getArgumentTables().isEmpty();
	}

	/**
	 * Factory for easier testing
	 */
	public static Part part(String description) {
		return new Part(description);
	}

	/**
	 * Factory for easier testing
	 */
	public static List<Part> parts(String... sentences) {
		List<Part> result = new ArrayList<Part>();
		for (String sentence : sentences) {
			result.add(new Part(sentence));
		}
		return result;
	}

}
