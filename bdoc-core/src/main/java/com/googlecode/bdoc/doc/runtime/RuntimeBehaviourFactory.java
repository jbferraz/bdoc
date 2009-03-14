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

package com.googlecode.bdoc.doc.runtime;

import static com.googlecode.bdoc.doc.util.CamelCaseToSentenceTranslator.SPACE_CHAR;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.bdoc.doc.domain.BehaviourFactory;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TableColumn;
import com.googlecode.bdoc.doc.domain.TableRow;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.Scenario.Pattern;

/**
 * @author Per Otto Bergum Christensen
 */
public class RuntimeBehaviourFactory implements BehaviourFactory {

	public static Scenario create(List<MethodCall> methodCalls) {
		List<Scenario.Part> scenarioParts = new ArrayList<Scenario.Part>();

		Pattern locale = null;
		for (MethodCall methodCall : methodCalls) {
			String camelCaseDescription = methodCall.getName();

			if (null == locale) {
				locale = Scenario.Pattern.find(camelCaseDescription);
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

	Scenario createScenarioInternal(Class<?> testClass, String testMethodName) {
		List<MethodCall> methodCalls = new RuntimeClassAnalyzer(testClass).invoke(testMethodName);

		if ((null == methodCalls) || (methodCalls.isEmpty())) {
			return null;
		}

		return create(methodCalls);
	}

	public Scenario createScenario(TestClass testClass, TestMethod method) {
		return createScenarioInternal(testClass.clazz(), method.getName());
	}

	public TestTable createTestTable(Class<?> testClass, String testMethodName) {
		List<MethodCall> methodCalls = new RuntimeClassAnalyzer(testClass).invoke(testMethodName);
		if (methodCalls.isEmpty()) {
			return null;
		}

		TestTable testTable = new TestTable(methodCalls.get(0).getName());
		for (MethodCall methodCall : methodCalls) {

			TableRow tableRow = new TableRow();
			testTable.addRow(tableRow);

			for (Argument argument : methodCall.getArguments()) {
				tableRow.addColumn(new TableColumn(argument.value()));
			}
		}

		return testTable;
	}

}
