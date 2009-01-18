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

package com.googlecode.bdoc.mojo;

import java.util.Locale;

import org.apache.maven.reporting.MavenReportException;


import com.googlecode.bdoc.diff.report.DiffReport;
import com.googlecode.bdoc.mojo.diff.BddLogDirDiff;
import com.googlecode.bdoc.mojo.diff.DiffExecutorImpl;

/**
 * @goal diff
 * 
 * @author Per Otto Bergum Christensen
 */
public class BddDocDiffMojo extends AbstractBddDocMojo {

	static final String DIFF_IS_FOUND = "Diff is found";

	static final String NO_DIFF_IS_FOUND = "No diff is found";

	static final String LOG_DIRECTORY_NOT_SPECIFIED = "Logdirectory not configured for plugin. "
			+ "Example of config: <configuration> <logDirectory>bddlog</logDirectory></configuration>";

	/**
	 * @parameter expression="${old}"
	 */

	private String oldBddDocFileName;

	/**
	 * @parameter expression="${new}"
	 */
	private String newBddDocFileName;

	protected void executeReport(Locale arg0) throws MavenReportException {
		executeInternal();
	}

	void executeInternal() {
		if (null == logDirectory) {
			errorMessage(LOG_DIRECTORY_NOT_SPECIFIED);
			return;
		}

		DiffExecutorImpl diffExecutor = new DiffExecutorImpl(logDirectory);

		DiffReport result = null;
		if ((null != oldBddDocFileName) && (null != newBddDocFileName)) {
			getLog().info("Diff is run on [" + oldBddDocFileName + "] and [" + newBddDocFileName + "]");
			result = diffExecutor.createDiffReport(oldBddDocFileName, newBddDocFileName);
		} else {
			BddLogDirDiff bddLogDirDiff = new BddLogDirDiff();
			bddLogDirDiff.setDiffExecutor(diffExecutor);
			result = bddLogDirDiff.process(logDirectory.list());
		}

		if (result.diffExists()) {
			logMessage(DIFF_IS_FOUND);
			writeReport(result.getHtml());

		} else {
			logMessage(NO_DIFF_IS_FOUND);
		}
	}

	public String getDescription(Locale arg0) {
		return "BDoc Diff Report";
	}

	public String getName(Locale arg0) {
		return "bdoc-diff";
	}

	public String getOutputName() {
		return "bdoc-diff";
	}
}
