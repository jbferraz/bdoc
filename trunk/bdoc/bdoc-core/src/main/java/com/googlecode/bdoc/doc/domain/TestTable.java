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

package com.googlecode.bdoc.doc.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestTable extends Statement {

	private List<TableColumn> headerColumns = new ArrayList<TableColumn>();
	private List<TableRow> rows = new ArrayList<TableRow>();

	public TestTable(String camelCaseDescription) {
		super(camelCaseDescription);
	}

	public TestTable(String camelCaseDescription, List<TableColumn> headerColumns) {
		super(camelCaseDescription);
		this.headerColumns = headerColumns;
	}

	public List<TableRow> getRows() {
		return rows;
	}

	public void addRow(TableRow tableRow) {
		rows.add(tableRow);
	}

	public List<TableColumn> getHeaderColumns() {
		return headerColumns;
	}

	public void addHeaderColumn(TableColumn tableColumn) {
		headerColumns.add(tableColumn);
	}

	public void addCollectionToRows(Collection<? extends Object> collection) {
		for (Object object : collection) {
			addRow(new TableRow(object));
		}
	}

	public void setHeaderColumns(List<TableColumn> columns) {
		this.headerColumns = columns;
		
	}

}
