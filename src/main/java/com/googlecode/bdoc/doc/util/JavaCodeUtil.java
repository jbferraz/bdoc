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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestClass;

/**
 * Util-methods for getting data from java source code.
 * 
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
public class JavaCodeUtil {

	static final char LEFT_BRACE = '{';
	static final char RIGHT_BRACE = '}';
	static final char LEFT_BRACKET = '(';
	static final char RIGHT_BRACKET = ')';
	static String NEWLINE = System.getProperty("line.separator");

	private JavaCodeUtil() {
	}

	public static String javaBlockAfter(String javaSourceCode, String token) {
		String cutCode = StringUtils.substringAfter(javaSourceCode, token);
		int startIndex = findIndexWhereMethodsBlockStarts(cutCode, token);
		cutCode = StringUtils.substring(cutCode, startIndex, cutCode.length());
		if (!cutCode.startsWith(Character.toString(LEFT_BRACE))) {
			cutCode = LEFT_BRACE + cutCode;
		}
		int endIndex = findIndexWhereMethodsBlockEnds(cutCode);
		return StringUtils.substring(cutCode, 0, endIndex).trim();
	}

	private static int findIndexWhereMethodsBlockStarts(String javaSourceCode, String token) {
		boolean leftBracket = false;
		boolean rightBracket = false;
		for (int index = 0; index < javaSourceCode.length(); index++) {
			char charAt = javaSourceCode.charAt(index);
			String string = Character.toString(charAt);
			if (!StringUtils.isWhitespace(string) && !string.equals(NEWLINE)) {
				if (!leftBracket) {
					if (charAt == LEFT_BRACKET) {
						leftBracket = true;
					} else {
						return nextIndex(javaSourceCode, token);
					}
				} else if (!rightBracket) {
					if (charAt == RIGHT_BRACKET) {
						rightBracket = true;
					} else {
						return nextIndex(javaSourceCode, token);
					}
				} else if (charAt == LEFT_BRACE) {
					return index;
				} else {
					int nextIndex = nextIndex(javaSourceCode, token);
					if (nextIndex != -1) {
						return nextIndex;
					} else {
						int indexOfLeftBrace = StringUtils.indexOf(javaSourceCode, Character.toString(LEFT_BRACE));
						return indexOfLeftBrace;
					}
				}
			}
		}
		return 0;
	}

	private static int nextIndex(String javaSourceCode, String token) {
		int nextIndex = StringUtils.indexOf(javaSourceCode, token);
		if (nextIndex == -1) {
			return -1;
		}
		String cutCode = StringUtils.substringAfter(javaSourceCode, token);
		return nextIndex + findIndexWhereMethodsBlockStarts(cutCode, token) + token.length();
	}

	public static int findIndexWhereMethodsBlockEnds(String javaSourceCode) {
		int endIndex = javaSourceCode.length();
		int nrOfLeft = 0;
		int nrOfRight = 0;

		for (int index = 0; index < javaSourceCode.length(); index++) {
			char charAt = javaSourceCode.charAt(index);
			if (charAt == LEFT_BRACE) {
				nrOfLeft++;
			} else if (charAt == RIGHT_BRACE) {
				nrOfRight++;
			}
			if (balance(nrOfLeft, nrOfRight)) {
				endIndex = index;
				break;
			}
		}
		return endIndex;
	}

	private static boolean balance(int nrOfLeft, int nrOfRight) {
		return nrOfLeft > 0 && nrOfLeft == nrOfRight;
	}

	public static List<Scenario.Part> getGivenWhenThenMethods(String javaMethodContent) {
		List<Scenario.Part> result = new ArrayList<Scenario.Part>();

		String javaTmp = javaMethodContent;
		boolean moreToFind = true;
		while (moreToFind) {
			int[] indexes = findFirstIndexForEachKeyword(javaTmp);
			int firstFoundIndex = getKeywordWithLowestIndex(indexes, javaMethodContent.length());

			if (foundKeyword(javaMethodContent, firstFoundIndex)) {
				javaTmp = javaTmp.substring(firstFoundIndex);
				String behaviourByKeyword = StringUtils.substringBefore(javaTmp, "(");
				behaviourByKeyword += getParametersWithFirstLetterInUpperCase(javaTmp);
				result.add(new Scenario.Part(behaviourByKeyword));
				javaTmp = javaTmp.substring(behaviourByKeyword.length());
			} else {
				moreToFind = false;
			}
		}

		return result;
	}

	private static String getParametersWithFirstLetterInUpperCase(String javaTmp) {
		return StringUtils.capitalize(StringUtils.substringBetween(javaTmp, "(", ")"));
	}

	private static boolean foundKeyword(String javaMethodContent, int firstFoundIndex) {
		return firstFoundIndex > -1 && firstFoundIndex < javaMethodContent.length();
	}

	private static int getKeywordWithLowestIndex(int[] indexes, int max) {
		int firstFoundIndex = max;
		for (int index : indexes) {
			if (index > -1 && index < firstFoundIndex) {
				firstFoundIndex = index;
			}
		}
		return firstFoundIndex;
	}

	private static int[] findFirstIndexForEachKeyword(String javaTmp) {
		int indexes[] = new int[Scenario.KEYWORD_COUNT];
		for (int keywordIndex = 0; keywordIndex < Scenario.KEYWORD_COUNT; keywordIndex++) {
			int keywordStart = Scenario.Pattern.indexOfScenarioKeyword(javaTmp, keywordIndex);
			indexes[keywordIndex] = keywordStart;
		}
		return indexes;
	}

	/**
	 * Gets the argument names for the specified methode
	 * Uses regular expression: http://java.sun.com/docs/books/tutorial/essential/regex/intro.html
	 * @param testClass
	 *            specifies source
	 * @param methodName
	 *            to look for
	 * @return name of arguments in methodName
	 */
	public static List<String> getArgumentNames(TestClass testClass, String methodName, File srcTestJava) {
		
		String methodSignature = JavaSourceMethodSignatureFinder.getSignature(methodName, testClass.getSource(srcTestJava));
		
		String[] arguments = StringUtils.substringBetween(methodSignature, "(", ")").split(",");

		List<String> result = new ArrayList<String>();
		for (String rawArgumentDeclaration : arguments) {
			String arg = rawArgumentDeclaration.trim();
			result.add(arg.substring(arg.lastIndexOf(' ') + 1, arg.length()));
		}

		return result;
	}
}
