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

import org.junit.Test;

/**
 * TODO: - Add license information - Add bdoc plugin - Add story for testing with excel as source of testdata - Add reference to story for
 * this test - Use reflection and index of method arguments on testmethod in order to execute tests
 * 
 * Learn to Read and Write Microsoft Excel Documents with Jakarta's POI - http://www.devx.com/Java/Article/17301/1954
 * 
 * @author Per Otto Bergum Christensen
 * 
 * Useful links:
 * http://studios.thoughtworks.com/twist-agile-test-automation/1.0beta/help/working_with_a_scenario.html
 * http://schuchert.wikispaces.com/FitNesse.Tutorials.ScenarioTables
 * http://www.testearly.com/2007/04/13/take-heed-of-mixing-junit-4s-parameterized-tests/
 * http://junit.sourceforge.net/javadoc_40/org/junit/runners/Parameterized.html
 * http://junit.sourceforge.net/javadoc_40/org/junit/runner/JUnitCore.html
 * 
 */
public class TestExcelExampleTables {

	private ExcelExampleTables testTables;

	public TestExcelExampleTables() {
		testTables = new ExcelExampleTables("./src/test/resources/calc-operation-examples.xls");
	}

	@Test
	public void shouldLoadEverySheetInTheGivenExcelFile() {
		assertEquals(asList("SheetA", "SheetB", "SheetC"), testTables.sheetNames());
	}

	@Test
	public void shouldFindTableByDescription() {
		assertEquals(asList("a", "b", "sum"), testTables.getTable("Example on addition").columnNames());
		assertEquals(asList("x", "y", "result"), testTables.getTable("Example on subtraction").columnNames());
	}

	@Test
	public void shouldReturnAListWithTestdataValuesForEachRowWithTestdata() {
		ExcelExampleTable excelExampleTable = testTables.getTable("Example on addition");
		assertEquals(2, excelExampleTable.rowCount());
		assertEquals(asList(1D, 1D, 2D), excelExampleTable.getRow(0));
		assertEquals(asList(2D, 2D, 4D), excelExampleTable.getRow(1));
		
		excelExampleTable = testTables.getTable("Example on subtraction");
		assertEquals(3, excelExampleTable.rowCount());
		assertEquals(asList(4D, 4D, 0D), excelExampleTable.getRow(0));
		assertEquals(asList(3D, 1D, 2D), excelExampleTable.getRow(1));
		assertEquals(asList(9D, 6D, 3D), excelExampleTable.getRow(2));
	}
}
