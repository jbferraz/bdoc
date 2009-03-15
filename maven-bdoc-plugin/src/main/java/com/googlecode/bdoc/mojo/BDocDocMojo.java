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
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.apache.maven.reporting.MavenReportException;

import com.googlecode.bdoc.difflog.DiffLog;
import com.googlecode.bdoc.difflog.DiffLogReport;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.BehaviourFactory;
import com.googlecode.bdoc.doc.domain.JavaTestSourceBehaviourParser;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.dynamic.RuntimeBehaviourFactory;
import com.googlecode.bdoc.doc.report.BDocReportImpl;
import com.googlecode.bdoc.doc.report.BDocReportInterface;
import com.googlecode.bdoc.doc.report.HtmlReport;
import com.googlecode.bdoc.doc.report.ScenarioLinesFormatter;

/**
 * @goal doc
 * @requiresDependencyResolution test
 * @execute phase=test-compile
 * @phase test-compile
 * 
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
public class BDocDocMojo extends AbstractBddDocMojo {

	private static final String BDOC_REPORTS_TITLE = "BDoc reports";

	static final String BDOC_DIR = "bdoc";

	static final String BDOC_REPORTS_XML = "bdoc-reports.xml";

	static final String BDOC_HTML = "bdoc.html";

	private static final String BDOC_DIFF_LOG_HTML = "bdoc_diff_log.html";

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

	/**
	 * Specifies files, which are excluded in the check. By default, no files
	 * are excluded.
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
	 * @parameter default-value=
	 *            "com.googlecode.bdoc.doc.report.AndInBetweenScenarioLinesFormatter"
	 * @required
	 */
	String scenarioFormatterClassName;

	/**
	 * Specifies where to save the bdoc-reports.xml, optional
	 * 
	 * @parameter
	 */
	String bdocReportsXmlDirectoryPath;

	/**
	 * @parameter
	 */
	String storyRefAnnotationClassName;

	/**
	 * @parameter default-value= "static"
	 * @required
	 */
	String scenarioAnalyzer;

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

		getLog().info("scenarioAnalyzer: " + scenarioAnalyzer);
		BehaviourFactory behaviourFactory = new JavaTestSourceBehaviourParser(testSourceDirectory);
		if (scenarioAnalyzer.equals("dynamic")) {
			behaviourFactory = new RuntimeBehaviourFactory(testSourceDirectory);
		}

		BDoc bdoc = bdocReport.run(behaviourFactory);

		DiffLog diffLog = new DiffLog();
		if (getBDocChangeLogFile().exists()) {
			diffLog = DiffLog.fromXmlFile(getBDocChangeLogFile());
		}

		diffLog.scan(bdoc);

		getLog().info("Updating file: " + getBDocChangeLogFile());
		diffLog.writeToFile(getBDocChangeLogFile());

		writeReport(BDOC_HTML, new HtmlReport(bdoc, (ScenarioLinesFormatter) classLoader.loadClass(scenarioFormatterClassName)
				.newInstance()).html());

		writeReport(BDOC_DIFF_LOG_HTML, new DiffLogReport().run(diffLog).result());

		makeBDocReportsHtml();
	}

	protected BDoc bdoc() {
		return bdocReport.run(new JavaTestSourceBehaviourParser(testSourceDirectory));
	}

	private void makeBDocReportsHtml() {
		getSink().head();
		getSink().title();
		getSink().text(BDOC_REPORTS_TITLE);
		getSink().title_();
		getSink().head_();

		getSink().body();

		getSink().lineBreak();
		getSink().lineBreak();
		getSink().text(BDOC_REPORTS_TITLE);
		getSink().lineBreak();

		getSink().list();

		getSink().listItem();
		getSink().link(BDOC_HTML);
		getSink().text("BDoc");
		getSink().link_();
		getSink().listItem_();

		getSink().listItem();
		getSink().link(BDOC_DIFF_LOG_HTML);
		getSink().text("BDocDiffLog");
		getSink().link_();
		getSink().listItem_();

		getSink().list_();

		getSink().body_();

		getSink().flush();
	}

	private void writeReport(String fileName, String reportHtmlContent) throws IOException {
		File file = new File(outputDirectory, fileName);
		getLog().info("Writing to file: " + new File(outputDirectory, fileName));
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
		return BDOC_REPORTS_TITLE;
	}

	public String getOutputName() {
		return "bdoc-reports";
	}

	File getBDocChangeLogFile() {
		File dir = new File(getBDocChangeLogRootDirectoryPath(), getProject().getGroupId() + "/" + getProject().getArtifactId());
		return new File(dir, getBDocChangeLogFileName());
	}

	String getBDocChangeLogRootDirectoryPath() {
		if (null != bdocReportsXmlDirectoryPath) {
			return bdocReportsXmlDirectoryPath;
		}
		return System.getProperty("user.home") + "/" + BDOC_DIR;
	}

	String getBDocChangeLogFileName() {
		return BDOC_REPORTS_XML;
	}
}