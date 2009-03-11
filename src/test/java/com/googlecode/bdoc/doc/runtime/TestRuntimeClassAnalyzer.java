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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.runtime.testdata.AccountBehaviour;

/**
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
@Ref(Story.ADVANCED_SCENARIO_SPECIFICATION)
public class TestRuntimeClassAnalyzer {

	private RuntimeClassAnalyzer runtimeClassAnalyzer = new RuntimeClassAnalyzer(AccountBehaviour.class);

	@Test
	public void shouldReturnAllMethodCallsInTheTestClassWhenRunningTheTest() {

		List<MethodCall> methodCalls = runtimeClassAnalyzer.invoke("withdraw");

		int index = 0;
		assertEquals("givenMyAccountHasAnInitialBalanceOf", methodCalls.get(index++).getName());
		assertEquals("whenIAskToWithdraw", methodCalls.get(index++).getName());
		assertEquals("thenIShouldBeGiven", methodCalls.get(index++).getName());
		assertEquals("andTheNewBalanceShouldBe", methodCalls.get(index++).getName());
	}

	@Test
	public void shouldAddArgumentValuesToMethodCalls() {
		MethodCall methodCall = runtimeClassAnalyzer.invoke("create").get(0);

		assertEquals("givenAnAccountNameAndInitalBalance", methodCall.getName());
		assertEquals("MyAccount", methodCall.getArguments().get(0).value());
	}

	@Test
	public void shouldNotIncludeTheInitialMethodCallToTheTestMethod() {
		List<MethodCall> methodCalls = runtimeClassAnalyzer.invoke("withdraw");
		assertFalse(methodCalls.contains(new MethodCall("withdraw")));
	}
	
	@Test
	public void shouldNotCreateMethodCallsFromTestMethodThatThrowsException() {
		assertNull( runtimeClassAnalyzer.invoke("shouldThrowException") );
	}
	
}
