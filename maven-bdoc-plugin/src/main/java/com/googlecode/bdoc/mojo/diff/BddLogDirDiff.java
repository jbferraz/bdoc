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

package com.googlecode.bdoc.mojo.diff;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.googlecode.bdoc.diff.report.DiffReport;

/**
 *  @author Per Otto Bergum Christensen
 */
public class BddLogDirDiff {

	private DiffExecutor diffReportFactory;

	public BddLogDirDiff() {
	}

	public void setDiffExecutor(DiffExecutor diffReportFactory) {
		this.diffReportFactory = diffReportFactory;

	}

	public DiffReport process(String[] bddLogFilesInput) {

		List<String> bddLogFiles = Arrays.asList(bddLogFilesInput);

		Collections.sort(bddLogFiles);

		final int lastPos = bddLogFiles.size() - 1;
		int compareIndex = lastPos - 1;

		while (-1 < compareIndex) {
			DiffReport diffReport = diffReportFactory.createDiffReport(bddLogFiles.get(compareIndex), bddLogFiles.get(lastPos));
			if (diffReport.diffExists()) {
				return diffReport;
			}
			compareIndex--;
		}
		return new DiffReport(false);
	}
}
