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

package com.googlecode.bdoc.doc.testdata;

import org.junit.Test;

public class TestClassWithTestTablesBehaviour {

	@Test
	public void shouldAddTwoNumbers() {
		exampleOnSumOfTwoValues(10, 10, 20);
		exampleOnSumOfTwoValues(20, 20, 40);
		exampleOnSumOfTwoValues(20, 40, 60);

		exampleOnResultOfMultiply(1, 1000, 1000);
		exampleOnResultOfMultiply(200, 200, 4000);
		exampleOnResultOfMultiply(2000, 4, 8000);
	}

	@Test
	public void shouldConvertValuesBetweenTypes() {
		exampleOnTypeConversionSuppert(Long.class, String.class);
		exampleOnTypeConversionSuppert(Integer.class, String.class);
	}

	void exampleOnTypeConversionSuppert(Class<?> class1, Class<?> class2) {
	}

	void exampleOnSumOfTwoValues(int value1, int value2, int sum) {
	}

	void exampleOnResultOfMultiply(int value1, int value2, int sum) {
	}

	@Test
	public void shouldSubtractTwoValues() {
		exampleOnSubtractionBetweenValueOneAndValueTwo(3, 1, 2);
		exampleOnSubtractionBetweenValueOneAndValueTwo(6, 2, 4);
		exampleOnSubtractionBetweenValueOneAndValueTwo(10, 4, 6);
	}

	void exampleOnSubtractionBetweenValueOneAndValueTwo(int value1, int value2, int result) {
	}

	@Test
	public void shouldHandleBothScenarioAndTestTableInOneTest() {
		givenADynamicScenario();
		when();
		then();

		exampleOnNumber(1);
		exampleOnNumber(2);
		exampleOnNumber(3);
		exampleOnNumber(4);
	}

	void givenADynamicScenario() {
	}

	void when() {
	}

	void then() {
	}

	void exampleOnNumber(int i) {
	}
}
