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

package com.googlecode.bdoc.calc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.bdoc.calc.bdoc.Ref;
import com.googlecode.bdoc.calc.bdoc.RefClass;
import com.googlecode.bdoc.calc.bdoc.Spec;
import com.googlecode.bdoc.calc.bdoc.Story;
import com.googlecode.bdoc.testsupport.excel.ExcelExampleTableTestMethodSupport;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.ADDITION)
@RefClass(Calc.class)
public class TestCalcBehaviour {

	private ExcelExampleTableTestMethodSupport excelExampleTableTestMethodSupport = new ExcelExampleTableTestMethodSupport(
			"./src/test/resources/calc-operation-examples.xls", this);

	private Calc calc = new Calc();

	private int value1;
	private int value2;
	private int sum;

	@Test
	@Spec("( value1 + value2 ) / 2")
	public void averageIsCalculatedAs$spec$() {
		assertEquals(15, calc.average(10, 20), .001);
	}

	@Test
	public void shouldAddTwoNumbers() {
		givenTheTwoValues(4, 5);
		whenTheAddOperationIsExecuted();
		thenTheResultShouldBe(9);

		exampleOnAddition(1, 1, 2);
		exampleOnAddition(1, 2, 3);
		exampleOnAddition(2, 2, 4);
	}

	void givenTheTwoValues(int i, int j) {
		this.value1 = i;
		this.value2 = j;
	}

	void whenTheAddOperationIsExecuted() {
		this.sum = calc.add(value1, value2);
	}

	void thenTheResultShouldBe(int expectedSum) {
		assertEquals(expectedSum, sum);
	}

	void exampleOnAddition(int value1, int value2, int expectedSum) {
		assertEquals(expectedSum, new Calc().add(value1, value2));
	}

	@Test
	public void shouldSubtractTwoNumbers() {
		excelExampleTableTestMethodSupport.verify("exampleOnSubtraction");
	}

	public void exampleOnSubtraction(double x, double y, double result) {
		assertEquals(result, calc.subtract(x, y), 0.01);
	}

}