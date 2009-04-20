package com.googlecode.bdoc.testsupport.excel;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TODO:
 * - Add license information
 * - Add bdoc plugin
 * - Add story for testing with excel as source of testdata
 * - Add reference to story for this test
 * - Use reflection and index of method arguments on testmethod in order to execute tests
 * 
 * @author Per Otto Bergum Christensen
 *
 */
public class TestExcelTestTables {

	@Test
	public void shouldLoadEverySheetInTheGivenExcelFile() {
		ExcelTestTables testTables = new ExcelTestTables( "./src/test/resources/calc-operation-examples.xls" );

		assertEquals( asList( "SheetA", "SheetB", "SheetC" ), testTables.sheetNames() );
	}

	//shouldSearchForTestTableDataWhenRun
	
	//testTables.run( "exampleOnAddition", this );
}
