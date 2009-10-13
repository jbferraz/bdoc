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

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestClass {

	private Class<? extends Object> clazz;
	private Map<String, TestMethodReference> testMethodReferences = new HashMap<String, TestMethodReference>();

	public TestClass(Class<? extends Object> clazz) {
		Validate.notNull(clazz, "clazz can't be null");
		this.clazz = clazz;
	}

	public Class<? extends Object> clazz() {
		return clazz;
	}

	public boolean shouldBeAnalyzedForExtendedBehaviour() {
		return clazz.getName().endsWith("Behaviour") || clazz.getName().endsWith("Behavior");
	}

	public String getSource(File testSrcDir) {
		if (null == testSrcDir) {
			throw new IllegalStateException("Test source dir must be set on TestClass before getSource can run");
		}

		File source = new File(testSrcDir, clazz.getName().replace('.', '/') + ".java");
		try {
			return FileUtils.readFileToString(source);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> storyRefAnnotation) {
		return clazz.isAnnotationPresent(storyRefAnnotation);
	}

	public Annotation getAnnotation(Class<? extends Annotation> storyRefAnnotation) {
		return clazz.getAnnotation(storyRefAnnotation);
	}

	public Method[] getMethods() {
		return clazz.getMethods();
	}

	public List<TestMethod> getTestMethods() {
		List<TestMethod> result = new ArrayList<TestMethod>();
		Method[] methods = getMethods();
		for (Method method : methods) {
			TestMethod testMethod = new TestMethod(this, method, testMethodReferences.get(method.getName()));
			if (testMethod.isTest()) {
				result.add(testMethod);
			}
		}
		return result;
	}

	public boolean classIsAnnotatedWithIgnore() {

		Annotation[] annotations = clazz.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().endsWith(".Ignore")) {
				return true;
			}
		}

		return false;
	}

	public TestMethod getTestMethod(String methodName) {
		try {
			return new TestMethod(this, clazz.getMethod(methodName, new Class[0]), testMethodReferences.get(methodName));
		} catch (Exception e) {
			throw new IllegalStateException("Error getting testmethod [" + methodName + "] from [" + clazz.getName() + "]", e);
		}
	}

	@Override
	public String toString() {
		return clazz.getName();
	}

	public void registerTestMethodReferences(Map<String, TestMethodReference> testMethodReferences) {
		this.testMethodReferences = testMethodReferences;
	}

}
