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

package com.googlecode.bdoc.doc.tinybdd.testdata;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TeztTinyTestdataScenarios {

	private TeztTinyTestdataScenarios given = createGiven();
	private TeztTinyTestdataScenarios when = createWhen();
	private TeztTinyTestdataScenarios then = createThen();
	private TeztTinyTestdataScenarios and = createAnd();
	private int number;

	@Test
	public void simpleGivenWhenThen() {
		given.criteriaA();
		when.actionA();
		then.ensureA();
	}

	void criteriaA() {
	}

	void actionA() {
	}

	void ensureA() {
	}

	@Test
	public void willFailIfStateIsNotHandledByBddAnalyzer() {
		given.numberWithValue(42);
		then.ensureNumberIs(42);

	}

	void numberWithValue(int i) {
		this.number = i;
	}

	void ensureNumberIs(int i) {
		assertEquals(i, number);
	}

	// ----------------------------------------------------------------------------------------

	TeztTinyTestdataScenarios createAnd() {
		return this;
	}

	TeztTinyTestdataScenarios createThen() {
		return this;
	}

	TeztTinyTestdataScenarios createWhen() {
		return this;
	}

	TeztTinyTestdataScenarios createGiven() {
		return this;
	}
/*
	public Object invoke(String methodName, Object[] args) throws Exception {
		Method[] methods = this.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equals(methodName)) {
				return methods[i].invoke(this, args);
			}
		}
		throw new IllegalArgumentException("Method [" + methodName + "] not found");
	}
	*/

}
