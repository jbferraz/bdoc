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

package com.googlecode.bdoc.doc.domain;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;

public class TestClass {

	private Class<? extends Object> clazz;

	private JavaTestSourceBehaviourParser javaTestSourceBehaviourParser;

	public TestClass(Class<? extends Object> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends Object> clazz() {
		return clazz;
	}

	public boolean isMarkedAsContainerOfScenariosSpecifiedInTestMethodBlocks() {
		return clazz.getName().endsWith("Behaviour") || clazz.getName().endsWith("Behavior");
	}

	public String getSource(File testSrcDir) {
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

	public Scenario getScenarioFromTestMethodBlock(String testMethodName, File testSrcDir) {
		if (null == javaTestSourceBehaviourParser) {
			javaTestSourceBehaviourParser = new JavaTestSourceBehaviourParser(getSource(testSrcDir));
		}

		Scenario scenario = javaTestSourceBehaviourParser.getScenario(testMethodName);

		return scenario;
	}
}
