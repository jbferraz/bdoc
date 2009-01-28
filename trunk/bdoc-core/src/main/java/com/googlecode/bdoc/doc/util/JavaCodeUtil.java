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

package com.googlecode.bdoc.doc.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.googlecode.bdoc.doc.domain.Scenario;

/**
 * Util-methods for getting data from java source code.
 */
public class JavaCodeUtil {

	static final char LEFT = '{';
	static final char RIGHT = '}';

	private JavaCodeUtil() {
	}

	public static String javaBlockAfter(String javaSourceCode, String token) {
		String cutCode = StringUtils.substringAfter(javaSourceCode, token);
		int startIndex = StringUtils.indexOf(cutCode, LEFT) + 1;
		int endIndex = findIndexWhereMethodsBlockEnds(cutCode);
		return StringUtils.substring(cutCode, startIndex, endIndex).trim();
	}

	public static int findIndexWhereMethodsBlockEnds(String javaSourceCode) {
		int endIndex = javaSourceCode.length();
		int nrOfLeft = 0;
		int nrOfRight = 0;

		for (int index = 0; index < javaSourceCode.length(); index++) {
			char charAt = javaSourceCode.charAt(index);
			if (charAt == LEFT) {
				nrOfLeft++;
			} else if (charAt == RIGHT) {
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
				result.add(new Scenario.Part(behaviourByKeyword));
				javaTmp = javaTmp.substring(behaviourByKeyword.length());
			} else {
				moreToFind = false;
			}
		}

		return result;
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
}
