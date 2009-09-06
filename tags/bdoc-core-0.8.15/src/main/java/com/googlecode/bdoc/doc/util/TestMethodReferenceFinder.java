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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.googlecode.bdoc.BDocException;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestMethodReference;

public class TestMethodReferenceFinder {

	public static Map<String, TestMethodReference> findTestMethodReferenceFor(TestClass testClass, File sourceTestDir) {

		Map<String, TestMethodReference> result = new HashMap<String, TestMethodReference>();
		if (testClass.clazz().getName().contains("$")) {
			return result;
		}

		List<String> sourceLines = readSourceLines(sourceTestDir, testClass.clazz());

		for (TestMethod testMethod : testClass.getTestMethods()) {
			int testMethodLineNumber = findTestMethodLineNumber(testMethod.getName(), sourceLines);
			result.put(testMethod.getName(), new TestMethodReference(testClass.clazz().getName(), testMethodLineNumber));
		}

		return result;
	}

	private static int findTestMethodLineNumber(String testMethodName, List<String> sourceLines) {
		int lineNumber = -1;
		for (String sourceLine : sourceLines) {
			if (sourceLine.contains(testMethodName)) {
				return lineNumber;
			}
			lineNumber++;
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private static List<String> readSourceLines(File sourceTestDir, Class<?> testClass) {
		try {
			return FileUtils.readLines(sourceFile(sourceTestDir, testClass));
		} catch (IOException e) {
			throw new BDocException(e);
		}
	}

	private static File sourceFile(File sourceDir, Class<?> clazz) {
		return new File(sourceDir, clazz.getName().replace('.', '/') + ".java");
	}

}
