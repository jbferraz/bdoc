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

import static com.googlecode.bdoc.doc.domain.BDoc.TEST_METHOD_PREFIX;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;

public class TestMethod {

	private Method method;
	private TestClass testClass;

	private List<Scenario> scenarios = new ArrayList<Scenario>();
	private List<TestTable> testTables = new ArrayList<TestTable>();
	private TestMethodReference testMethodReference;

	public TestMethod(TestClass testClass, Method method,TestMethodReference testMethodReference) {
		this.testClass = testClass;
		this.method = method;
		this.testMethodReference = testMethodReference;
	}

	/**
	 * Constructor made for test purpose
	 * 
	 * @param testClass
	 * @param method
	 */
	public TestMethod(Class<? extends Object> testClass, String method) {
		this.testClass = new TestClass(testClass);
		try {
			this.method = testClass.getDeclaredMethod(method);
		} catch (Exception e) {
			throw new IllegalArgumentException("Could not acces method [" + method + "] for class [" + testClass.getName() + "]", e);
		}
	}

	public Method method() {
		return method;
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> storyRefAnnotation) {
		return method.isAnnotationPresent(storyRefAnnotation);
	}

	public Annotation getAnnotation(Class<? extends Annotation> storyRefAnnotation) {
		return method.getAnnotation(storyRefAnnotation);
	}

	public String getName() {
		return method.getName();
	}

	/**
	 * Gets the camelCaseSentence from the testmethod, removing 'test' if JUnit
	 * 3 is used
	 * 
	 * @param testMethod
	 *            that specifies the test
	 * @return camelCaseSentence describeing behaviour
	 */
	public String camelCaseSentence() {
		String camelCaseSentence = getName();
		if( camelCaseSentence.equalsIgnoreCase( "test")) {
			return camelCaseSentence;
		}
		if (camelCaseSentence.startsWith(TEST_METHOD_PREFIX)) {
			camelCaseSentence = camelCaseSentence.substring(TEST_METHOD_PREFIX.length());
			camelCaseSentence = camelCaseSentence.substring(0, 1).toLowerCase()
					+ camelCaseSentence.substring(1, camelCaseSentence.length());
		}
		return camelCaseSentence;
	}

	/**
	 * Tells if the metod is a testMethod
	 * 
	 * @param method
	 *            to check
	 * @return true if method is a test
	 */
	public boolean isTest() {

		boolean testMethod = false;
		boolean ignore = false;
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			String name = annotation.annotationType().getName();
			if (name.endsWith(".Test")) {
				testMethod = true;
			} else if (name.endsWith(".Ignore")) {
				ignore = true;
			}
		}

		if (testMethod && !ignore) {
			return true;
		}

		if (method.getName().startsWith(TEST_METHOD_PREFIX)) {
			return true;
		}
		return false;
	}

	public TestClass getTestClass() {
		return testClass;
	}

	public Class<?> clazz() {
		return testClass.clazz();
	}

	public String getSpec() {
		Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
		for (Annotation annotation : declaredAnnotations) {

			if (annotation.annotationType().getName().endsWith(".Spec")) {
				try {
					return String.valueOf(MethodUtils.invokeMethod(annotation, "value", null));
				} catch (Exception e) {
					return null;
				}
			}
		}
		return null;
	}

	public boolean hasSpec() {
		return null != getSpec();
	}

	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public void setScenarios(List<Scenario> scenarios) {
		this.scenarios = scenarios;
	}

	public List<TestTable> getTestTables() {
		return testTables;
	}

	public void setTestTables(List<TestTable> testTables) {
		this.testTables = testTables;
	}

	public boolean hasScenarios() {
		return null != scenarios;
	}

	public boolean hasTestTables() {
		return null != testTables && testTables.size() > 0;
	}

	public TestMethodReference getTestMethodReference() {
		return testMethodReference;
	}

	public void setTestMethodReference(TestMethodReference testMethodReference) {
		this.testMethodReference = testMethodReference;
	}

	public void addScenario(Scenario scenario) {
		this.scenarios.add(scenario);
		
	}

	public void addTestTable(TestTable testTable) {
		this.testTables.add( testTable );
	}
}
