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
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class ExcelExampleTable {
	private HSSFSheet sheet;
	private int startingRowIndex;
	private int startingCellnum;

	public ExcelExampleTable(HSSFSheet sheet, int startingRowIndex, int startingCellnum) {
		this.sheet = sheet;
		this.startingRowIndex = startingRowIndex;
		this.startingCellnum = startingCellnum;
	}

	public List<String> columnNames() {
		List<String> columnNames = new ArrayList<String>();
		HSSFRow headerRow = sheet.getRow(startingRowIndex + 1);
		for (int cellnum = 0; cellnum < headerRow.getLastCellNum(); cellnum++) {
			columnNames.add(headerRow.getCell(cellnum).getRichStringCellValue().getString());
		}
		return columnNames;
	}

	public int rowCount() {
		int count =0;
		while( null != sheet.getRow(startingRowIndex + 3 + count++) );
		return count;		
	}

	public List<Object> getRow(int rowIndex) {
		List<Object> result = new ArrayList<Object>();
		HSSFRow headerRow = sheet.getRow(startingRowIndex + 2 + rowIndex);
		for (int cellnum = 0; cellnum < headerRow.getLastCellNum(); cellnum++) {
			result.add(headerRow.getCell(cellnum).getNumericCellValue());
		}
		return result;
	}

}
