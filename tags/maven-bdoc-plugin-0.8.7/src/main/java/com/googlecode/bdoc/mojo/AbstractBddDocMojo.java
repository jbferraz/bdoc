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

import org.apache.commons.io.FileUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.codehaus.doxia.site.renderer.SiteRenderer;

/**
 *  @author Per Otto Bergum Christensen
 */
public abstract class AbstractBddDocMojo extends AbstractMavenReport {

	/**
	 * @parameter default-value="${project.reporting.outputDirectory}"
	 * @required
	 */
	File outputDirectory;

	/**
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	MavenProject project;
	
	/**
	 * @parameter
	 */
	File logDirectory;
	
	/**
	 * @component
	 * @required
	 * @readonly
	 */
	SiteRenderer siteRenderer;

	public AbstractBddDocMojo() {
		super();
	}

	protected void writeReport(String content) {
		getSink().rawText(content);
		getSink().flush();
	}

	protected void writeFile(Object reportContent, File outDir, String reportFile) {

		File file = new File(outDir, reportFile);
		getLog().info("Writing file to: " + file.getAbsolutePath());
		try {
			FileUtils.writeStringToFile(file, String.valueOf(reportContent));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void logMessage(String msg) {
		getLog().info(msg);
	}

	protected void errorMessage(String msg) {
		getLog().error(msg);
	}
	

	@Override
	protected MavenProject getProject() {
		return project;
	}
	
	public void setProject(MavenProject project) {
		this.project = project;
	}

	@Override
	protected String getOutputDirectory() {
		return outputDirectory.getAbsolutePath();
	}

	@Override
	protected SiteRenderer getSiteRenderer() {
		return siteRenderer;
	}
}