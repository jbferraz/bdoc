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

import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelTableCellReadException extends RuntimeException {

	private static final long serialVersionUID = 1L;
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
