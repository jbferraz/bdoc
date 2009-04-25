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
