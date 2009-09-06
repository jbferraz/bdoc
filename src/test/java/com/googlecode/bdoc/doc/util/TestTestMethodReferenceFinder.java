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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestMethodReference;
import com.googlecode.bdoc.doc.util.testdata.TeztMethodReferenceTestdata;

public class TestTestMethodReferenceFinder {

	private static final File SRC = new File("./src/test/java");
	private TestClass testClass = new TestClass(TeztMethodReferenceTestdata.class);

	@Test
	public void shouldReturnAnEntryForEachTestMethod() {
		Map<String, TestMethodReference> result = TestMethodReferenceFinder.findTestMethodReferenceFor(testClass, SRC);
		assertNotNull(result.containsKey("shouldBeTestDefinedWithAnnotationAtLine30"));
	}

	@Test
	public void shouldHaveATestMethodReferenceForEachTestMethod() {
		Map<String, TestMethodReference> result = TestMethodReferenceFinder.findTestMethodReferenceFor(testClass, SRC);
		TestMethodReference testMethodReference = result.get("shouldBeTestDefinedWithAnnotationAtLine30");
		assertEquals(TeztMethodReferenceTestdata.class.getName(), testMethodReference.getClassName());
		assertEquals(30, testMethodReference.getLineNumberForStartOfTestMethodInClass());
	}

	@Test
	public void shouldIgnoreTestClassesWrappedInsideAnotherClass() {
		TestMethodReferenceFinder.findTestMethodReferenceFor(new TestClass(MyTestClass.class), SRC);
	}

	public class MyTestClass {
		@Test
		public void aTest() {
		}
	}
}
