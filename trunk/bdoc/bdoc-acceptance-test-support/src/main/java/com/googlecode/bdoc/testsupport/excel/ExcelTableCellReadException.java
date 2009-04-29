package com.googlecode.bdoc.testsupport.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelTableCellReadException extends RuntimeException {

	private int rowIndex;
	private int cellnum;
	private HSSFCell cell;
	private Exception cause;

	public ExcelTableCellReadException(int rowIndex, int cellnum, HSSFCell cell, Exception cause) {
		super(cause);
		this.rowIndex = rowIndex;
		this.cellnum = cellnum;
		this.cell = cell;
		this.cause = cause;

	}

	@Override
	public String getMessage() {
		StringBuilder msg = new StringBuilder();
		msg.append("Error reading cell at [row=");
		msg.append(rowIndex);
		msg.append(", cellnum=");
		msg.append(cellnum);
		msg.append(", content=");
		msg.append( cell );
		msg.append( ", cellType=" );
		msg.append( cell.getCellType() );
		msg.append( "]: " );
		msg.append(cause.getMessage());
		return msg.toString();
	}
}
