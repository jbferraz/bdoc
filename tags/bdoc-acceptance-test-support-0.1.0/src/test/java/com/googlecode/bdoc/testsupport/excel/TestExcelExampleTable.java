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
