package com.googlecode.bdoc.testsupport.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelTestTables {

	private HSSFWorkbook workbook;

	public ExcelTestTables(String xlsFilePath) {
		try {
			workbook = new HSSFWorkbook(new FileInputStream(xlsFilePath));
		} catch (Exception e) {
			throw new IllegalArgumentException("Error reading xls from: " + xlsFilePath, e);
		}
	}

	public List<String> sheetNames() {
		List<String> result = new ArrayList<String>();

		int numberOfSheets = workbook.getNumberOfSheets();
		for (int index = 0; index < numberOfSheets; index++) {
			result.add(workbook.getSheetName(index));
		}

		return result;
	}

}
