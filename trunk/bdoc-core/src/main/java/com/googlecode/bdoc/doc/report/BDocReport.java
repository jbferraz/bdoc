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

package com.googlecode.bdoc.doc.report;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;

import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.Project;
import com.googlecode.bdoc.doc.util.ClassesDirectory;


public class BDocReport {

	public void setScenarioLinesFormatter(ScenarioLinesFormatter scenarioLinesFormatter) {
		this.scenarioLinesFormatter = scenarioLinesFormatter;
	}

	private ClassesDirectory classesDirectory = new ClassesDirectory();

	private String projectName;
	private String projectVersion;
	private ClassLoader classLoader;
	private Class<? extends Annotation> storyRefAnnotation;
	private Class<? extends Annotation> testAnnotation;
	private String html, xml;
	private ScenarioLinesFormatter scenarioLinesFormatter;

	public BDocReport() {
		super();
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	public void setTestClassDirectory(File testClassDirectory) {
		classesDirectory.setBaseDir(testClassDirectory);
	}

	public void setIncludesFilePattern(String[] includesFilePattern) {
		classesDirectory.setIncludes(includesFilePattern);
	}

	public void setExcludesFilePattern(String[] excludesFilePattern) {
		classesDirectory.setExcludes(excludesFilePattern);
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public void setStoryRefAnnotation(Class<? extends Annotation> storyRefAnnotation) {
		this.storyRefAnnotation = storyRefAnnotation;
	}

	public void setTestAnnotation(Class<? extends Annotation> testAnnotation) {
		this.testAnnotation = testAnnotation;
	}

	public void run(File testSrcDir) throws ClassNotFoundException, IOException {
		BDoc bddDoc = new BDoc(testAnnotation, storyRefAnnotation);
		bddDoc.setProject(new Project(projectName, projectVersion));
		bddDoc.addBehaviourFrom(classesDirectory, classLoader, testSrcDir);

		xml = new XmlReport(bddDoc).xml();
		if (scenarioLinesFormatter != null) {
			html = new HtmlReport(bddDoc, scenarioLinesFormatter).html();
		} else {
			html = new HtmlReport(bddDoc).html();
		}

	}

	public String getHtml() {
		return html;
	}

	public String getXml() {
		return xml;
	}
}