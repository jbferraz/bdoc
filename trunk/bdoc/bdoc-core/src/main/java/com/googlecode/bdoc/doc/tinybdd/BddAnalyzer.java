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

import static com.googlecode.bdoc.doc.tinybdd.ProxyUtils.createProxy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;

import com.googlecode.bdoc.BDocException;
import com.googlecode.bdoc.doc.domain.BehaviourFactory;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;

public class BddAnalyzer implements BehaviourFactory {

	private File srcTestJava;
	private List<Scenario> scenarios = new ArrayList<Scenario>();

	public BddAnalyzer(File srcTestJava) {
		this.srcTestJava = srcTestJava;
	}

	public void analyze(TestMethod testMethod) {
		try {
			internalAnalyzeOf(testMethod);
		} catch (Exception e) {
			throw new BDocException(e);
		}
	}

	private void internalAnalyzeOf(TestMethod testMethod) throws Exception {
		TestClassProxyWrapper testClassProxy = new TestClassProxyWrapper();
		Object proxy = createProxy(testMethod.clazz(), new TestMethodCallbackAnalyzer(testClassProxy,testMethod,scenarios));
		testClassProxy.setProxy( proxy );
		testClassProxy.runTest( testMethod );		
	}

	public List<Scenario> getCreatedScenarios() {
		return scenarios;
	}

	public List<TestTable> getCreatedTestTables() {
		return null;
	}

	public File sourceTestDirectory() {
		return srcTestJava;
	}

}
