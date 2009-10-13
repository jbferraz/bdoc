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

package com.googlecode.bdoc.report.testdata;

import static java.util.Arrays.asList;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.doc.tinybdd.testdata.TinyScenarioSupport;

public class StatementExampleFrameTestExampleBehaviour extends TinyScenarioSupport<StatementExampleFrameTestExampleBehaviour> {

	@Test
	public void theTest() {
		given.listWithPrimtiv(items());
		and.listWith(objects());

	}

	void listWith(List<SimpleObject> objects) {
	}

	List<SimpleObject> objects() {
		return asList(new SimpleObject("OperatorA", "OperatorB"), new SimpleObject("OperatorI", "OperatorII"));
	}

	List<String> items() {
		return asList("ItemA", "ItemB", "ItemC");
	}

	void listWithPrimtiv(List<String> items) {
	}

	public class SimpleObject {
		private String operator1;
		private String operator2;

		public SimpleObject(String operator1, String operator2) {
			this.operator1 = operator1;
			this.operator2 = operator2;
		}

		public String getOperator1() {
			return operator1;
		}

		public String getOperator2() {
			return operator2;
		}
	}
}
