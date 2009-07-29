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

package com.googlecode.bdoc.mojo;

import java.io.File;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.maven.reporting.MavenReportException;

/**
 * @goal reset
 * 
 * @author Micael Vesterlund
 */
public class ResetMojo extends AbstractBDocMojo {

	@Override
	protected void executeReport(Locale arg0) throws MavenReportException {
		try {
			executeInternal();
		} catch (Exception e) {
			getLog().error("BDoc error:" + e, e);
		}
	}

	private void executeInternal() throws Exception {

		File docChangeLogFile = getBDocChangeLogFile();

		if (docChangeLogFile.exists()) {
			getLog().info("Deleting file: " + docChangeLogFile);
			FileUtils.cleanDirectory(docChangeLogFile.getParentFile());
		}

	}

	public String getDescription(Locale arg0) {
		return "Resets the BDoc changes in the project";
	}

	public String getName(Locale arg0) {
		return "BDoc reset";
	}

	public String getOutputName() {
		return "bdoc-reset";
	}
}