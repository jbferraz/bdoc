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

import java.util.ArrayList;
import java.util.List;

import com.googlecode.bdoc.doc.domain.TableColumn;
import com.googlecode.bdoc.doc.domain.TableRow;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.util.JavaCodeUtil;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestTableFactory {

	public TestTable createTestTable(TestClass testClass, String testMethodName) {
		List<MethodCall> methodCalls = new RuntimeClassAnalyzer(testClass.clazz()).invoke(testMethodName);
		if (methodCalls.isEmpty()) {
			return null;
		}

		List<MethodCall> methodCallsClone = clone(methodCalls);

		TestTable testTable = new TestTable(methodCalls.get(0).getName());

		boolean headerAdded = false;
		for (MethodCall methodCall : methodCallsClone) {

			if (!headerAdded) {

				for (String argumentName : JavaCodeUtil.getArgumentNames(testClass, methodCall.getName())) {
					testTable.addHeaderColumn(new TableColumn(argumentName));
				}

			}

			TableRow tableRow = new TableRow();
			testTable.addRow(tableRow);

			for (Argument argument : methodCall.getArguments()) {
				tableRow.addColumn(new TableColumn(argument.value()));
			}
		}

		return testTable;
	}

	private List<MethodCall> clone(List<MethodCall> methodCalls) {
		List<MethodCall> methodCallsClone = new ArrayList<MethodCall>();
		for (MethodCall methodCall : methodCalls) {
			MethodCall methodCallClone = new MethodCall(methodCall.getName(), methodCall.getArguments());
			methodCallsClone.add(methodCallClone);
		}
		return methodCallsClone;
	}

}
