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

package com.googlecode.bdoc.doc.report;

import java.io.File;
import java.lang.annotation.Annotation;

import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.ScenarioFactory;
import com.googlecode.bdoc.doc.util.ClassesDirectory;

/**
 * @author Per Otto Bergum Christensen
 */
public class BDocReportImpl implements BDocReportInterface {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.bdoc.doc.report.BDocReport#setScenarioLinesFormatter(com
	 * .googlecode.bdoc.doc.report.ScenarioLinesFormatter)
	 */
	public void setScenarioLinesFormatter(ScenarioLinesFormatter scenarioLinesFormatter) {
		this.scenarioLinesFormatter = scenarioLinesFormatter;
	}

	private ClassesDirectory classesDirectory = new ClassesDirectory();

	private ProjectInfo projectInfo;
	private ClassLoader classLoader;
	private Class<? extends Annotation> storyRefAnnotation;
	private Class<? extends Annotation> testAnnotation;
	private Class<? extends Annotation> ignoreAnnotation;
	private String html, xml;
	private ScenarioLinesFormatter scenarioLinesFormatter;

	public BDocReportImpl() {
		super();
	}

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.bdoc.doc.report.BDocReport#setTestClassDirectory(java.
	 * io.File)
	 */
	public void setTestClassDirectory(File testClassDirectory) {
		classesDirectory.setBaseDir(testClassDirectory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.bdoc.doc.report.BDocReport#setIncludesFilePattern(java
	 * .lang.String[])
	 */
	public void setIncludesFilePattern(String[] includesFilePattern) {
		classesDirectory.setIncludes(includesFilePattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.bdoc.doc.report.BDocReport#setExcludesFilePattern(java
	 * .lang.String[])
	 */
	public void setExcludesFilePattern(String[] excludesFilePattern) {
		classesDirectory.setExcludes(excludesFilePattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.googlecode.bdoc.doc.report.BDocReport#setClassLoader(java.lang.
	 * ClassLoader)
	 */
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.bdoc.doc.report.BDocReport#setStoryRefAnnotation(java.
	 * lang.Class)
	 */
	public void setStoryRefAnnotation(Class<? extends Annotation> storyRefAnnotation) {
		this.storyRefAnnotation = storyRefAnnotation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.googlecode.bdoc.doc.report.BDocReport#setTestAnnotation(java.lang
	 * .Class)
	 */
	public void setTestAnnotation(Class<? extends Annotation> testAnnotation) {
		this.testAnnotation = testAnnotation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.bdoc.doc.report.BDocReport#run(java.io.File)
	 */
	public BDoc run(ScenarioFactory scenarioFactory) {
		BDoc bdoc = new BDoc(testAnnotation, storyRefAnnotation, ignoreAnnotation);
		
		bdoc.setProject(projectInfo);
		bdoc.addBehaviourFrom(classesDirectory, classLoader, scenarioFactory);

		xml = new XmlReport(bdoc).xml();
		if (scenarioLinesFormatter != null) {
			html = new HtmlReport(bdoc, scenarioLinesFormatter).html();
		} else {
			html = new HtmlReport(bdoc).html();
		}
		return bdoc;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.bdoc.doc.report.BDocReport#getHtml()
	 */
	public String getHtml() {
		return html;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.googlecode.bdoc.doc.report.BDocReport#getXml()
	 */
	public String getXml() {
		return xml;
	}

	public void setIgnoreAnnotation(Class<? extends Annotation> ignoreAnnotation) {
		this.ignoreAnnotation = ignoreAnnotation;
	}
}