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

import java.io.File;
import java.util.Locale;

import org.apache.maven.reporting.MavenReportException;

import com.googlecode.bdoc.clog.ChangeLog;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.report.BDocReport;
import com.googlecode.bdoc.doc.report.BDocReportImpl;

public class ChangeLogMojo extends AbstractBddDocMojo {

	public static final String BDOC_CHANGE_LOG_XML = "bdoc-change-log.xml";

	/**
	 * @parameter default-value="${project.build.testSourceDirectory}"
	 * @required
	 */
	File testSourceDirectory;

	/**
	 * @parameter default-value="${project.build.directory}/test-classes"
	 * @required
	 */
	File testClassDirectory;

	/**
	 * Specifies files, which are included in the check. By default, all files
	 * are included.
	 * 
	 * @parameter
	 */
	String[] includes;

	String changeLogDirectoryPath;

	BDocReport bdocReport = new BDocReportImpl();

	@Override
	protected void executeReport(Locale arg0) throws MavenReportException {

		bdocReport.setTestClassDirectory(testClassDirectory);

		bdocReport.setClassLoader(getClass().getClassLoader());
		bdocReport.setProjectName("projectName");
		bdocReport.setProjectVersion("projectVersion");

		BDoc bdoc = bdocReport.run(testSourceDirectory);

		ChangeLog changeLog = new ChangeLog();

		changeLog.scan(bdoc);

		changeLog.writeToFile(getBDocChangeLogFile());
	}

	public String getDescription(Locale arg0) {
		return "Documents the BDoc changes in the project";
	}

	public String getName(Locale arg0) {
		return "Change Log";
	}

	public String getOutputName() {
		return "changelog";
	}

	public File getBDocChangeLogFile() {
		return new File(new File(changeLogDirectoryPath), BDOC_CHANGE_LOG_XML);
	}
}
