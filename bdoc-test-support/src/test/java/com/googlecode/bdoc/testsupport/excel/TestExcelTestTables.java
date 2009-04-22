package com.googlecode.bdoc.testsupport.excel;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import org.junit.Ignore;
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
public class TestExcelTestTables {

	private ExcelTestTables testTables;

	public TestExcelTestTables() {
		testTables = new ExcelTestTables("./src/test/resources/calc-operation-examples.xls");
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
	@Ignore
	public void shouldReturnAListWithTestdataValuesForEachRowWithTestdata() {
		Table table = testTables.getTable("Example on addition");
		assertEquals(2, table.rowCount());
		assertEquals(asList(1, 1, 2), table.getRow(0) );
		assertEquals(asList(2, 2, 4), table.getRow(1) );
		
	}

	// shouldCreateTestTables based on example runs
	// should add testTables based on example runs
	// put marker on test and run bdoc:excel, add arguments, such as file name on excel file

	// testTables.run( "exampleOnAddition", this );
}
