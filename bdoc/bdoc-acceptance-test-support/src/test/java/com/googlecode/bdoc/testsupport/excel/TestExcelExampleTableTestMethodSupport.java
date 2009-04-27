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

import java.util.ArrayList;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestExcelExampleTableTestMethodSupport {

	private ExcelExampleTableTestMethodSupport excelExampleTableTestMethodSupport = new ExcelExampleTableTestMethodSupport(
			"./src/test/resources/calc-operation-examples.xls", this);
	private List<List<Double>> arguments;

	@Test
	public void shouldUseTestMethodNameToIdentifyExampleTableAndVerifyEachRowByRunningTheTestMethodWithItsArguments() {
		arguments = new ArrayList<List<Double>>();
		excelExampleTableTestMethodSupport.verify("exampleOnAddition");
		assertEquals(asList(1D, 1D, 2D), arguments.get(0));
		assertEquals(asList(2D, 2D, 4D), arguments.get(1));

		arguments = new ArrayList<List<Double>>();
		excelExampleTableTestMethodSupport.verify("exampleOnSubtraction");
		assertEquals(asList(4D, 4D, 0D), arguments.get(0));
		assertEquals(asList(3D, 1D, 2D), arguments.get(1));
		assertEquals(asList(9D, 6D, 3D), arguments.get(2));
	}

	public void exampleOnAddition(Double a, Double b, Double sum) {
		arguments.add(asList(a, b, sum));
	}

	public void exampleOnSubtraction(Double x, Double y, Double result) {
		arguments.add(asList(x, y, result));
	}

	// utvid eksempelprosjekt

}
