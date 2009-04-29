package com.googlecode.bdoc.testsupport.excel;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

public class TestExcelExampleTable {

	private static final String DATATYPE_EXAMPLES = "./src/test/resources/datatype-examples.xls";
	private ExcelExampleTable excelExampleTable;

	public TestExcelExampleTable() throws FileNotFoundException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(DATATYPE_EXAMPLES));
		excelExampleTable = new ExcelExampleTable(workbook.getSheetAt(0), 0, 0);
	}

	@Test
	public void shouldReturnDoubleValueForCellOfTypeNumeric() {
		assertEquals(Double.class, excelExampleTable.getRow(0).get(0).getClass());
	}

	@Test
	public void shouldReturnStringValueForCellOfTypeString() {
		assertEquals(String.class, excelExampleTable.getRow(1).get(0).getClass());
	}
	
	@Test
	public void shouldReturnDoubleValueForCellOfTypeFormula() {
		assertEquals(Double.class, excelExampleTable.getRow(2).get(0).getClass());
	}

	
}
