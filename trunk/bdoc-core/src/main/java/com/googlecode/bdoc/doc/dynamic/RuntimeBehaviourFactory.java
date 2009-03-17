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

package com.googlecode.bdoc.doc.dynamic;

import static com.googlecode.bdoc.doc.util.CamelCaseToSentenceTranslator.SPACE_CHAR;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.bdoc.doc.domain.BehaviourFactory;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.Scenario.Pattern;

/**
 * @author Per Otto Bergum Christensen
 */
public class RuntimeBehaviourFactory implements BehaviourFactory {

	private File javaSourceDir;
	private TestTableFactory testTableFactory;
	private List<TestTable> testTables;
	private List<Scenario> scenarios;

	public RuntimeBehaviourFactory(File javaSourceDir) {
		this.javaSourceDir = javaSourceDir;
		this.testTableFactory = new TestTableFactory(javaSourceDir);
	}

	private static Scenario create(List<MethodCall> methodCalls) {
		List<Scenario.Part> scenarioParts = new ArrayList<Scenario.Part>();

		Pattern locale = null;
		for (MethodCall methodCall : methodCalls) {
			String camelCaseDescription = methodCall.getName();

			if (null == locale) {
				locale = Scenario.Pattern.find(camelCaseDescription);
				if (null == locale) {
					return null;
				}
			}

			for (Argument argument : methodCall.getArguments()) {

				if (0 < methodCall.getArguments().indexOf(argument)) {
					camelCaseDescription = camelCaseDescription + locale.and();
				}

				camelCaseDescription = camelCaseDescription + SPACE_CHAR + argument.value() + SPACE_CHAR;
			}

			scenarioParts.add(new Scenario.Part(camelCaseDescription));
		}
		return new Scenario(scenarioParts);
	}

	private Scenario createScenarioInternal(Class<?> testClass, String testMethodName) {
		List<MethodCall> methodCalls = new RuntimeClassAnalyzer(testClass).invoke(testMethodName);

		if ((null == methodCalls) || (methodCalls.isEmpty())) {
			return null;
		}

		return create(methodCalls);
	}

	public File javaSourceDir() {
		return javaSourceDir;
	}

	public void analyze(TestMethod method) {
		Scenario scenario = createScenarioInternal(method.getTestClass().clazz(), method.getName());
		scenarios = new ArrayList<Scenario>();
		if (null != scenario) {
			scenarios.add(scenario);
		}

		TestTable testTable = testTableFactory.createTestTable(method.getTestClass(), method.getName());
		testTables = new ArrayList<TestTable>();
		if (null != testTable) {
			testTables.add(testTable);
		}
	}

	public List<Scenario> getCreatedScenarios() {
		return scenarios;
	}

	public List<TestTable> getCreatedTestTables() {
		return testTables;
	}
}
