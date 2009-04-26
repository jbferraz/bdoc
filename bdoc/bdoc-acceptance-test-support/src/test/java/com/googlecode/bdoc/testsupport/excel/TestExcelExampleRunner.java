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
public class TestExcelExampleRunner {

	private ExcelExampleTableRunner excelExampleTableRunner = new ExcelExampleTableRunner("./src/test/resources/calc-operation-examples.xls", this);
	private List<List<Double>> arguments;

	@Test //skille ut camelcase metoder i egen bdoc-utils, lage skikkelig parent-pom, slik at alt kan deplyes samtidig.
	//kan denne skives som et scenario?
	@Ignore 
	public void shouldUseTestMethodNameToIdentifyExampleTableAndVerifyEachRowByRunningTheTestMethodWithItsArguments() {
		arguments = new ArrayList<List<Double>>();
		excelExampleTableRunner.verify("exampleOnAddition");
		assertEquals(asList(1D, 1D, 2D), arguments.get(0));
		assertEquals(asList(2D, 2D, 4D), arguments.get(1));
	}

	public void exampleOnAddition(double a, double b, double sum) {
		arguments.add(asList(a, b, sum));
	}
}
