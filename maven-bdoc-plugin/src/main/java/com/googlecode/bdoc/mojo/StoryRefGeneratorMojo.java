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

import static org.apache.commons.beanutils.MethodUtils.invokeMethod;
import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import static org.apache.commons.beanutils.PropertyUtils.setSimpleProperty;

import java.io.File;
import java.util.ArrayList;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * @goal generate
 * @author Per Otto Bergum Christensen
 */
public class StoryRefGeneratorMojo extends AbstractMojo {

	public static final String NL = System.getProperty("line.separator");

	/**
	 * @parameter default-value="${project.testCompileSourceRoots}"
	 * @required
	 */
	private ArrayList<String> testCompileSourceRoots;

	/**
	 * @parameter default-value="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * @parameter expression="${javaPackage}"
	 */
	private String javaPackage;

	public void execute() throws MojoExecutionException {

		if (testCompileSourceRoots.isEmpty()) {
			getLog().error("No testCompileSourceRoots for project");
			return;
		}

		if (null == javaPackage) {
			javaPackage = RootPackageGenerator.calculateRootPackage(project);
			getLog().info("No package is set with option -DjavaPackage=<package>, will use: " + javaPackage);
		}
		try {
			executeInternal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeInternal() throws Exception {

		Object storyRefCodeGenerator = Class.forName("bdddoc4j.help.StoryRefCodeGenerator").newInstance();
		setSimpleProperty(storyRefCodeGenerator, "javaPackage", javaPackage);
		setSimpleProperty(storyRefCodeGenerator, "testDirectory", new File(testCompileSourceRoots.get(0)));

		Object result = invokeMethod(storyRefCodeGenerator, "execute", null);
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Result: " + result + NL + String.valueOf(getSimpleProperty(storyRefCodeGenerator, "resultLog")));
	}
}
