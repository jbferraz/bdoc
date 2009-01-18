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
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.maven.reporting.MavenReportException;

import com.googlecode.bdoc.clog.ChangeLog;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.report.BDocReportImpl;
import com.googlecode.bdoc.doc.report.BDocReportInterface;
import com.googlecode.bdoc.doc.report.HtmlReport;
import com.googlecode.bdoc.doc.report.ScenarioLinesFormatter;

/**
 * @goal ci
 * @requiresDependencyResolution test
 * @execute phase=test-compile
 * @phase test-compile
 * 
 * @author Per Otto Bergum Christensen
 */
public class BDocMojo extends AbstractBddDocMojo {

	static final String BDOC_DIR = "bdoc";

	static final String BDOC_CHANGE_LOG_XML = "bdoc-change-log.xml";

	static final String BDOC_REPORT_HTML = "bdoc-report.html";

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
	 * Specifies files, which are included in the check. By default, all files are included.
	 * 
	 * @parameter
	 */
	String[] includes;

	/**
	 * Specifies files, which are excluded in the check. By default, no files are excluded.
	 * 
	 * @parameter
	 */
	String[] excludes;

	/**
	 * @parameter default-value="org.junit.Test"
	 * @required
	 */
	String testAnnotationClassName;

	/**
	 * @parameter default-value="org.junit.Ignore"
	 * @required
	 */
	String ignoreAnnotationClassName;

	/**
	 * @parameter default-value= "com.googlecode.bdoc.doc.report.AndInBetweenScenarioLinesFormatter"
	 * @required
	 */
	String scenarioFormatterClassName;

	/**
	 * Specifies where to save to changelog, optional
	 * 
	 * @parameter
	 */
	String changeLogDirectoryPath;
	
	/**
	 * @parameter
	 */
	String storyRefAnnotationClassName;

	BDocReportInterface bdocReport = new BDocReportImpl();

	@Override
	protected void executeReport(Locale arg0) throws MavenReportException {
		try {
			executeInternal();
		} catch (Exception e) {
			getLog().error("BDoc error:" + e, e);
		}
	}

	@SuppressWarnings("unchecked")
	private void executeInternal() throws Exception {
		ClassLoader classLoader = getClassLoader();
		
		bdocReport.setClassLoader(classLoader);
		bdocReport.setTestClassDirectory(testClassDirectory);
		
		bdocReport.setTestAnnotation((Class<? extends Annotation>) classLoader.loadClass(testAnnotationClassName));
		bdocReport.setIgnoreAnnotation((Class<? extends Annotation>) classLoader.loadClass(ignoreAnnotationClassName));
		bdocReport.setScenarioLinesFormatter((ScenarioLinesFormatter) classLoader.loadClass(scenarioFormatterClassName).newInstance());

		bdocReport.setIncludesFilePattern(includes);
		bdocReport.setExcludesFilePattern(excludes);
		
		bdocReport.setProjectInfo(new ProjectInfo(getProject().getName(), getProject().getVersion()));

		if (null != storyRefAnnotationClassName) {
			bdocReport.setStoryRefAnnotation((Class<? extends Annotation>) classLoader.loadClass(storyRefAnnotationClassName));
		}
		
		BDoc bdoc = bdocReport.run(testSourceDirectory);

		// TODO, don't new each time, check on disk first
		ChangeLog changeLog = new ChangeLog();

		changeLog.scan(bdoc);

		//Refactor to pretty code
		File docChangeLogFile = getBDocChangeLogFile();
		getLog().info("Writing to file: " + docChangeLogFile);
		changeLog.writeToFile(docChangeLogFile);

		writeReport(BDOC_REPORT_HTML, new HtmlReport(bdoc).html());
		
		
		getSink().head();
		getSink().title();
		getSink().text("BDoc");
		getSink().title_();
		getSink().head_();

		getSink().body();
		getSink().text("Summeray");
		getSink().rawText("<br/><a href='bdoc-report.html'>Latest BDoc report</a>");
		
		getSink().body_();

		getSink().flush();
		
	}

	private void writeReport(String fileName, String reportHtmlContent) throws IOException {
		File file = new File(outputDirectory, fileName);
		getLog().info("Writing to file: " + fileName);
		FileUtils.writeStringToFile(file, reportHtmlContent);
	}

	protected ClassLoader getClassLoader() {

		try {
			return new URLClassLoader(new URL[0], getClass().getClassLoader()) {
				{
					for (Object filePath : getProject().getTestClasspathElements()) {
						addURL(new File(filePath.toString()).toURI().toURL());
					}
				}
			};
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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

	File getBDocChangeLogFile() {
		File dir = new File(getBDocChangeLogRootDirectoryPath(), getProject().getGroupId() + "/" + getProject().getArtifactId());
		return new File(dir, getBDocChangeLogFileName());
	}

	String getBDocChangeLogRootDirectoryPath() {
		if (null != changeLogDirectoryPath) {
			return changeLogDirectoryPath;
		}
		return System.getProperty("user.home") + "/" + BDOC_DIR;
	}

	String getBDocChangeLogFileName() {
		return BDOC_CHANGE_LOG_XML;
	}
}