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
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestClass {

	private File testSrcDir;
	private Class<? extends Object> clazz;


	public TestClass(File testSrcDir, Class<? extends Object> clazz) {
		Validate.notNull(clazz, "clazz can't be null");
		this.clazz = clazz;
		this.testSrcDir = testSrcDir;
	}

	public Class<? extends Object> clazz() {
		return clazz;
	}

	public boolean isMarkedAsContainerOfScenariosSpecifiedInTestMethodBlocks() {
		return clazz.getName().endsWith("Behaviour") || clazz.getName().endsWith("Behavior");
	}

	public String getSource() {
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

	public List<TestMethod> getTestMethods(TestAnnotations testAnnotations) {
		List<TestMethod> result = new ArrayList<TestMethod>();
		Method[] methods = getMethods();
		for (Method method : methods) {
			TestMethod testMethod = new TestMethod(method);
			if (testMethod.isTest(testAnnotations)) {
				result.add(testMethod);
			}
		}
		return result;
	}

	public boolean classIsAnnotatedWithIgnore(TestAnnotations testAnnotations) {
		Validate.notNull(testAnnotations, "testAnnotations can't be null");
		Validate.notNull(testAnnotations.getIgnoreAnnotation());

		return (clazz.isAnnotationPresent(testAnnotations.getIgnoreAnnotation()));
	}

	public TestMethod getTestMethod(String methodName) {
		try {
			return new TestMethod(clazz.getMethod(methodName, new Class[0]));
		} catch (Exception e) {
			throw new IllegalStateException("Error getting testmethod [" + methodName + "] from [" + clazz.getName() + "]", e);
		}
	}

}
