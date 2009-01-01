/**
 * The MIT License
 * 
 * Copyright (c) 2008 Per Otto Bergum Christensen
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

package com.googlecode.bdoc.mojo.diff;

import static org.apache.commons.beanutils.MethodUtils.invokeMethod;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.googlecode.bdoc.diff.report.DiffReport;

public class DiffExecutorImpl implements DiffExecutor {

	private File logDirectory;
	
	public DiffExecutorImpl(File logDirectory) {
		super();
		this.logDirectory = logDirectory;
	}

	public DiffReport createDiffReport(String oldBddDocFileName, String newBddDocFileName) {
		try {
			Object bddDocDiffReport = Class.forName("bdddoc4j.diff.report.BddDocDiffReport").newInstance();

			String oldXmlVersion = FileUtils.readFileToString(new File(logDirectory, oldBddDocFileName));
			String newXmlVersion = FileUtils.readFileToString(new File(logDirectory, newBddDocFileName));

			return (DiffReport) invokeMethod(bddDocDiffReport, "execute", new Object[] { oldXmlVersion, newXmlVersion });
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
