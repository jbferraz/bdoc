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

package com.googlecode.bdoc.testsupport.excel;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestExcelExampleTableTestMethodSupport {

	private ExcelExampleTableTestMethodSupport excelExampleTableTestMethodSupport = new ExcelExampleTableTestMethodSupport(
			"./src/test/resources/calc-operation-examples.xls", this);
	private List<List<Double>> doubleArguments;
	private List<List<String>> stringArguments;

	@Before
	public void resetArgumentsCollector() {
		doubleArguments = new ArrayList<List<Double>>();
		stringArguments = new ArrayList<List<String>>();
	}

	@Test
	public void shouldUseTestMethodNameToIdentifyExampleTableAndVerifyEachRowByRunningTheTestMethodWithTableColoumsAsArguments() {
		excelExampleTableTestMethodSupport.verify("exampleOnAddition");
		assertEquals(asList(1D, 1D, 2D), doubleArguments.get(0));
		assertEquals(asList(2D, 2D, 4D), doubleArguments.get(1));
	}

	public void exampleOnAddition(Double a, Double b, Double sum) {
		doubleArguments.add(asList(a, b, sum));
	}

	@Test
	public void shouldSupportPrimitivTypesInTestMethod() {
		excelExampleTableTestMethodSupport.verify("exampleOnSubtraction");
		assertEquals(asList(4D, 4D, 0D), doubleArguments.get(0));
		assertEquals(asList(3D, 1D, 2D), doubleArguments.get(1));
		assertEquals(asList(9D, 6D, 3D), doubleArguments.get(2));
	}

	public void exampleOnSubtraction(double x, double y, double result) {
		doubleArguments.add(asList(x, y, result));
	}

	@Test
	public void shouldHandleFormulaInTableCell() {
		excelExampleTableTestMethodSupport.verify("exampleOnDivision");
		assertEquals(asList(6D, 3D, 2D), doubleArguments.get(0));
		assertEquals(asList(2D, 2D, 1D), doubleArguments.get(1));
	}

	public void exampleOnDivision(double dividend, double divisor, double quotient) {
		doubleArguments.add(asList(dividend, divisor, quotient));
	}

	@Test
	public void shouldHandleStringValues() {
		excelExampleTableTestMethodSupport.verify("exampleOnConcatenation");
		assertEquals(asList("a", "b", "ab"), stringArguments.get(0));
		assertEquals(asList("abc", "def", "abcdef"), stringArguments.get(1));
	}

	public void exampleOnConcatenation(String string1, String string2, String resultString) {
		stringArguments.add(asList(string1, string2, resultString));
	}

}
