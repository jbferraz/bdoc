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
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.Locale;

import org.apache.maven.reporting.MavenReportException;

import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.report.BDocReportImpl;
import com.googlecode.bdoc.doc.report.BDocReportInterface;
import com.googlecode.bdoc.doc.report.ScenarioLinesFormatter;

/**
 * @goal doc
 * @requiresDependencyResolution test
 * @execute phase=test-compile
 * @phase test-compile
 * 
 * @author Per Otto Bergum Christensen
 */
public class BddDocMojo extends AbstractBddDocMojo {

	/**
	 * @parameter default-value="${project.build.testSourceDirectory}"
	 * @required
	 */
	public File testSourceDirectory;

	/**
	 * @parameter default-value="${project.build.directory}/test-classes"
	 * @required
	 */
	private File testClassDirectory;

	/**
	 * @parameter default-value="org.junit.Test"
	 * @required
	 */
	private String testAnnotationClassName;

	/**
	 * @parameter default-value="org.junit.Ignore"
	 * @required
	 */
	private String ignoreAnnotationClassName;

	/**
	 * @parameter
	 */
	private String storyRefAnnotationClassName;

	/**
	 * Specifies files, which are included in the check. By default, all files
	 * are included.
	 * 
	 * @parameter
	 */
	protected String[] includes;
	/**
	 * Specifies files, which are excluded in the check. By default, no files
	 * are excluded.
	 * 
	 * @parameter
	 */
	protected String[] excludes;

	/**
	 * @parameter default-value=
	 *            "com.googlecode.bdoc.doc.report.AndInBetweenScenarioLinesFormatter"
	 * @required
	 */
	private String scenarioFormatterClassName;

	protected void executeReport(Locale arg0) throws MavenReportException {
		try {
			executeInternal();
		} catch (Exception e) {
			getLog().error(e);
		}
	}

	@SuppressWarnings("unchecked")
	void executeInternal() throws Exception {
		ClassLoader classLoader = getClassLoader();
		BDocReportInterface bdocReport = new BDocReportImpl();

		bdocReport.setProjectInfo(new ProjectInfo(getProject().getName(), getProject().getVersion()));
		bdocReport.setClassLoader(classLoader);
		bdocReport.setTestClassDirectory(testClassDirectory);
		bdocReport.setTestAnnotation((Class<? extends Annotation>) classLoader.loadClass(testAnnotationClassName));
		bdocReport.setIgnoreAnnotation((Class<? extends Annotation>) classLoader.loadClass(ignoreAnnotationClassName));
		bdocReport.setIncludesFilePattern(includes);
		bdocReport.setExcludesFilePattern(excludes);

		if (null != scenarioFormatterClassName) {
			bdocReport.setScenarioLinesFormatter((ScenarioLinesFormatter) classLoader.loadClass(scenarioFormatterClassName).newInstance());
		}

		if (null != storyRefAnnotationClassName) {
			bdocReport.setStoryRefAnnotation((Class<? extends Annotation>) classLoader.loadClass(storyRefAnnotationClassName));
		}

		bdocReport.run(testSourceDirectory);

		if (null != logDirectory) {
			writeFile(bdocReport.getXml(), logDirectory, "bddDoc." + new Date().getTime() + ".xml");
		}
		writeReport(bdocReport.getHtml());
	}

	protected ClassLoader getClassLoader() throws Exception {

		return new URLClassLoader(new URL[0], getClass().getClassLoader()) {
			{
				for (Object filePath : getProject().getTestClasspathElements()) {
					addURL(new File(filePath.toString()).toURI().toURL());
				}
			}
		};
	}

	public String getOutputName() {
		return "bdoc";
	}

	public String getDescription(Locale arg0) {
		return "BDoc Report*";
	}

	public String getName(Locale arg0) {
		return "bdoc";
	}

	public void setTestAnnotationClassName(String testAnnotationClassName) {
		this.testAnnotationClassName = testAnnotationClassName;
	}

	public void setTestClassDirectory(File testClassDirectory) {
		this.testClassDirectory = testClassDirectory;
	}

	public void setLogDirectory(File file) {
		this.logDirectory = file;
	}

	public void setScenarioFormatterClassName(String scenarioFormatterClassName) {
		this.scenarioFormatterClassName = scenarioFormatterClassName;
	}

	public void setIgnoreAnnotationClassName(String ignoreAnnotationClassName) {
		this.ignoreAnnotationClassName = ignoreAnnotationClassName;
	}
}