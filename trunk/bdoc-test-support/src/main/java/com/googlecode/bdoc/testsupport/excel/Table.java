package com.googlecode.bdoc.testsupport.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class Table {
	private HSSFSheet sheet;
	private int startingRowIndex;
	private int startingCellnum;

	public Table(HSSFSheet sheet, int startingRowIndex, int startingCellnum) {
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
		return 2;
	}

	public List<Object> getRow(int rowIndex) {
		return null;
	}

}
