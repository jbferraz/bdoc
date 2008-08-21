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

package bdddoc4j.plugin;

import static org.apache.commons.beanutils.MethodUtils.invokeMethod;
import static org.apache.commons.beanutils.PropertyUtils.getSimpleProperty;
import static org.apache.commons.beanutils.PropertyUtils.setSimpleProperty;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.Locale;

import org.apache.maven.reporting.MavenReportException;

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
	 * @parameter default-value="${project.build.directory}/test-classes"
	 * @required
	 */
	private File testClassDirectory;

	/**
	 * @parameter default-value="bdddoc4j.core.report.BddDocReport"
	 * @required
	 */
	private String bddDocReportClassName;

	/**
	 * @parameter default-value="org.junit.Test"
	 * @required
	 */
	private String testAnnotationClassName;

	/**
	 * @parameter
	 * @required
	 */
	private String storyRefAnnotationClassName;

	protected void executeReport(Locale arg0) throws MavenReportException {
		try {
			executeInternal();
		} catch (Exception e) {
			getLog().error(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void executeInternal() throws Exception {
		ClassLoader classLoader = createClassLoader();
		Object bddDocReport = classLoader.loadClass(bddDocReportClassName).newInstance();

		setSimpleProperty(bddDocReport, "projectName", getProject().getName());
		setSimpleProperty(bddDocReport, "projectVersion", getProject().getVersion());
		setSimpleProperty(bddDocReport, "classLoader", classLoader);
		setSimpleProperty(bddDocReport, "testClassDirectory", testClassDirectory);
		setSimpleProperty(bddDocReport, "testAnnotation", classLoader.loadClass(testAnnotationClassName));
		setSimpleProperty(bddDocReport, "storyRefAnnotation", classLoader.loadClass(storyRefAnnotationClassName));

		invokeMethod(bddDocReport, "run", null);

		writeFile(getSimpleProperty(bddDocReport, "xml"), logDirectory, "bddDoc." + new Date().getTime() + ".xml");
		writeReport(String.valueOf(getSimpleProperty(bddDocReport, "html")));
	}

	private ClassLoader createClassLoader() throws Exception {

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
}
