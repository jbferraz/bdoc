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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.TEST_TABLES)
public class TestTestTable {

	private List<MethodCall> methodCalls;

	@Before
	public void resetMethodCalls() {
		methodCalls = new ArrayList<MethodCall>();
	}

	@Test
	public void shouldContainOneRowForEachMethodCall() {
		methodCalls.add(new MethodCall("assertEquals", new Object[] { 1, 1 }));
		methodCalls.add(new MethodCall("assertEquals", new Object[] { 2, 2 }));
		assertEquals(2, new TestTable(methodCalls).getRows().size());
	}

	@Test
	public void shouldHaveOneColumnForEachArgumentInAMethodCall() {
		methodCalls.add(new MethodCall("assertValuePlussValueIsSum", new Object[] { 2, 2, 4 }));
		assertEquals(3, new TestTable(methodCalls).getRows().get(0).getColumns().size());
	}
}
