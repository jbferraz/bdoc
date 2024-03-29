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

package com.googlecode.bdoc.doc.tinybdd;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.tinybdd.testdata.TestWithBeforeAndAfter;

public class TestTestClassProxyWrapper {

	@Test
	public void shouldCallMethodAnnotatedWithAtBeforeRunningATest() {
		TestWithBeforeAndAfter.beforeObject = null;

		TestClassProxyWrapper testClassProxyWrapper = new TestClassProxyWrapper();
		testClassProxyWrapper.setProxy(new TestWithBeforeAndAfter());
		testClassProxyWrapper.runTest(new TestMethod(TestWithBeforeAndAfter.class, "test"));

		assertNotNull(TestWithBeforeAndAfter.beforeObject);
	}

	@Test
	public void shouldCallMethodAnnotatedWithAtAfterWhenTheTestHasRun() {
		TestWithBeforeAndAfter.afterObject = null;

		TestClassProxyWrapper testClassProxyWrapper = new TestClassProxyWrapper();
		testClassProxyWrapper.setProxy(new TestWithBeforeAndAfter());
		testClassProxyWrapper.runTest(new TestMethod(TestWithBeforeAndAfter.class, "test"));

		assertNotNull(TestWithBeforeAndAfter.afterObject);
	}
}
