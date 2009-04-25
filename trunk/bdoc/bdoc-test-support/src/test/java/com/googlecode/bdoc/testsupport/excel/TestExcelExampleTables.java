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
