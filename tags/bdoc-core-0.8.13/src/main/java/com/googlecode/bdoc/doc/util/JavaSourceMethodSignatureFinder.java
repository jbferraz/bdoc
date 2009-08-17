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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Help: http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
public class JavaSourceMethodSignatureFinder {

	public static String getSignature(String methodName, String javaSource) {
		String signaturePostfixRegexp = "\\("
				+ "[a-z || A-Z || \\d || _ || , || \t || < || > || ? || \n || \r || \u0085 || \u2028 || \u2029]+" + "\\)[\\s]*\\{";

		Pattern patternForMethodSignature = Pattern.compile(methodName + signaturePostfixRegexp);

		Matcher signatureMatcher = patternForMethodSignature.matcher(javaSource);
		if (!signatureMatcher.find()) {
			throw new IllegalStateException("Could not find method signature (or signature not supported):  [" + methodName + "] in ["
					+ javaSource + "]");
		}

		return javaSource.substring(signatureMatcher.start(), signatureMatcher.end() - 1).trim();
	}
}
