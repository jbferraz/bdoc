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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.doc.domain.testdata.TestDomainBehavior;
import com.googlecode.bdoc.doc.domain.testdata.TestDomainBehaviour;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestTestClass {

	@Test
	public void shouldSpecifyCheckForScenariosInTestMethodBlockWhenTestClassHasPostfixBehaviour() {
		TestClass testClass = new TestClass(BConst.SRC_TEST_JAVA,TestDomainBehaviour.class);
		assertTrue(testClass.shouldBeAnalyzedForExtendedBehaviour());
	}

	@Test
	public void shouldSpecifyCheckForScenariosInTestMethodBlockWhenTestClassHasPostfixBehavior() {
		TestClass testClass = new TestClass(BConst.SRC_TEST_JAVA,TestDomainBehavior.class);
		assertTrue(testClass.shouldBeAnalyzedForExtendedBehaviour());
	}

	@Test
	public void shouldLoadTheJavaSourceForTheTestClass() {
		String source = new TestClass(BConst.SRC_TEST_JAVA, TestDomainBehaviour.class).getSource();
		assertTrue(source.contains("DomainBehaviour"));
	}

	@Test
	public void shouldReturnTestMethods() {
		TestClass testClass = new TestClass(BConst.SRC_TEST_JAVA,MyTest.class);
		assertEquals(1, testClass.getTestMethods(new TestAnnotations()).size());
	}

	@Test
	public void shouldTellIfTestClassIsMarkedWithIgnore() {
		assertFalse(new TestClass(BConst.SRC_TEST_JAVA,MyTest.class).classIsAnnotatedWithIgnore(new TestAnnotations()));
		assertTrue(new TestClass(BConst.SRC_TEST_JAVA,MyIgnoredTest.class).classIsAnnotatedWithIgnore(new TestAnnotations()));
	}

	@Ignore
	public class MyIgnoredTest {
	}

	public class MyTest {
		public void nonTestMethod() {
		}

		@Test
		public void shouldBeATest() {
		}
	}

}
