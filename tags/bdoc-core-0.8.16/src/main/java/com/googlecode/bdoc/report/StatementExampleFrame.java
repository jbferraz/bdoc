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

package com.googlecode.bdoc.report;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.Statement;

public class StatementExampleFrame extends AbstractBDocReportContent implements Comparable<StatementExampleFrame> {

	private Statement statement;
	private String className;

	public StatementExampleFrame(String className, Statement statement, BDocConfig bdocConfig) {
		super("statement_examples_frame.ftl", bdocConfig);
		this.className = className;
		this.statement = statement;
		put("statement", statement);
		put("this", this);
	}

	public String getFileName() {
		return getFileName(className, statement);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof StatementExampleFrame) && getFileName().equals(((StatementExampleFrame) obj).getFileName());
	}

	public static String getFileName(String className, Statement statement) {
		return fileNamePartOf(className) + "-" + fileNamePartOf(statement.getCamelCaseSentence()) + "-examples_frame.html";
	}

	private static String fileNamePartOf(String camelCaseSentence) {
		return camelCaseSentence.toLowerCase();
	}

	public int compareTo(StatementExampleFrame other) {
		if (this.equals(other)) {
			return 0;
		}
		return 1;
	}
}
