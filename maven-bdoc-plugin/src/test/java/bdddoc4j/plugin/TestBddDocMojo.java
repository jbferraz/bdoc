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

import java.io.File;

import org.apache.maven.project.MavenProject;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bdddoc4j.core.report.BddDocReport;

public class TestBddDocMojo {

	private BddDocMojo bddDocMojo;
	private boolean writeFileRun;

	@Before
	public void setupBddDocMojo() {
		writeFileRun = false;
		bddDocMojo = new BddDocMojo() {

			@Override
			protected ClassLoader getClassLoader() {
				return TestBddDocMojo.class.getClassLoader();
			}

			@Override
			protected void writeReport(String content) {
			}

			@Override
			protected void writeFile(Object reportContent, File outDir, String reportFile) {
				writeFileRun = true;
			}
		};

		MavenProject project = new MavenProject();
		bddDocMojo.setProject(project);
		bddDocMojo.setBddDocReportClassName(BddDocReport.class.getName());
		bddDocMojo.setTestAnnotationClassName(Test.class.getName());
		bddDocMojo.setTestClassDirectory(new File("target/test-classes"));
	}

	@Test
	public void shouldRunWithoutAStoryRefAnnotation() throws Exception {
		bddDocMojo.executeInternal();
	}

	@Test
	public void shouldNotWriteFileToLogDirectoryWhenLogDirectoryIsNotSet() throws Exception {
		bddDocMojo.executeInternal();
		assertFalse(writeFileRun);
	}

	@Test
	public void shouldWriteFileToLogDirectoryWhenLogDirectoryIsSet() throws Exception {
		bddDocMojo.setLogDirectory( new File( "") );
		bddDocMojo.executeInternal();
		assertTrue(writeFileRun);
	}

}
