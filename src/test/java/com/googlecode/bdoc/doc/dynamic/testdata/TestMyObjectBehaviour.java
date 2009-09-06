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

package com.googlecode.bdoc.doc.dynamic.testdata;

import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestMyObjectBehaviour {

	@Test
	public void shouldAddTwoNumbers() {
		given();
		when();
		then();

		example(1);
		example(1);
		example(2);
	}

	void example(int i) {
	}

	void then() {
	}

	void when() {
	}

	void given() {
	}

	void exampleOnAddition(int value) {
	}

	@Test
	public void shouldMakeACallWithoutArguments() {
		examleWithoutArgument();
		examleWithoutArgument();
	}

	void examleWithoutArgument() {
	}

	@Test
	public void shouldExtractTestTableWithOnlyOnRow() {
		example(4);
	}

	@Test
	public void testThatMakesCallScenarioMethods() {
		givenMyFavoriteValue(4);
		gittMinFavorittVerdi(4);
		saaSkalJegHoppe( 10 );
	}

	public void saaSkalJegHoppe(int i) {
	}

	public void givenMyFavoriteValue(int i) {
	}

	public void gittMinFavorittVerdi(int i) {
	}
	
	

}