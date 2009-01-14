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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.clog.ChangeLog;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.report.BDocReportInterface;

public class TestBDocMojo {

	public static final String TARGET = "target";

	private Mockery context;

	private BDocReportInterface bdocReport;
	private BDoc bdoc;

	private BDocMojo bDocMojo = new BDocMojo();

	public TestBDocMojo() {
		bDocMojo.changeLogDirectoryPath = TARGET;
		bDocMojo.outputDirectory = new File(TARGET);

		MavenProject mavenProject = new MavenProjectMock();
		mavenProject.setGroupId("groupId");
		mavenProject.setArtifactId("artifactId");

		bDocMojo.project = mavenProject;
		bDocMojo.testAnnotationClassName = Test.class.getName();
	}

	@Before
	public void resetBDoc() {
		bdoc = new BDoc();
		bdoc.setProject(new ProjectInfo("name", "version"));

		context = new Mockery();
		bdocReport = context.mock(BDocReportInterface.class);
		bDocMojo.bdocReport = bdocReport;
	}

	public void initalizeStandardBDocReportMock() {
		bDocMojo.getBDocChangeLogFile().delete();

		context.checking(new Expectations() {
			{
				one(bdocReport).setTestClassDirectory(null);
				one(bdocReport).setIncludesFilePattern(null);
				one(bdocReport).setExcludesFilePattern(null);
				one(bdocReport).setClassLoader(with(any(ClassLoader.class)));
				one(bdocReport).setProjectInfo(with(any(ProjectInfo.class)));
				one(bdocReport).setTestAnnotation(with(Test.class));
				one(bdocReport).run(null);
				will(returnValue(bdoc));
			}
		});
	}

	@Test
	public void shouldCreateANewChangeLogXmlWhenOneDoesNotExist() throws MavenReportException {
		initalizeStandardBDocReportMock();
		bDocMojo.executeReport(null);
		assertTrue(bDocMojo.getBDocChangeLogFile().exists());
	}

	@Test
	public void shouldUpdateTheLatestBDocInThePersistedChangeLog() throws MavenReportException, IOException {
		initalizeStandardBDocReportMock();
		bDocMojo.executeReport(null);
		ChangeLog updatedChangeLog = ChangeLog.fromXmlFile(bDocMojo.getBDocChangeLogFile());
		assertEquals(bdoc.getProject(), updatedChangeLog.latestBDoc().getProject());
	}

	@Test
	public void shouldUseTheUserHomeDirectoryConcatenatedWithBDocAsRootDirectoryForPersistedBDocChangeLogs() {
		assertEquals(System.getProperty("user.home") + "/bdoc", new BDocMojo().getBDocChangeLogRootDirectoryPath());
	}

	@Test
	public void shouldChangeRootDirectoryForPersistedBDocChangesIfThisIsSpecifiedInConfiguration() {
		BDocMojo changeLogMojo2 = new BDocMojo();
		changeLogMojo2.changeLogDirectoryPath = "mypath";
		assertEquals("mypath", changeLogMojo2.getBDocChangeLogRootDirectoryPath());
	}

	@Test
	public void shouldBuildTheBDocChangeLogFileUpFromBDocChangeLogRootDirectoryAndProjectGroupIdAndProjectArticfactId() {
		File expectedChangeLogFile = new File("target/groupId/artifactId/" + BDocMojo.BDOC_CHANGE_LOG_XML);
		assertEquals(expectedChangeLogFile, bDocMojo.getBDocChangeLogFile());
	}

	@Test
	public void shouldConfigureTheBDocReportWithAGivenTestAnnotation() throws MavenReportException {
		bDocMojo.testAnnotationClassName = MyTestAnnotation.class.getName();

		context.checking(new Expectations() {
			{
				one(bdocReport).setTestAnnotation(with(MyTestAnnotation.class));
				
				one(bdocReport).setTestClassDirectory(null);
				one(bdocReport).setIncludesFilePattern(null);
				one(bdocReport).setExcludesFilePattern(null);
				one(bdocReport).setClassLoader(with(any(ClassLoader.class)));
				one(bdocReport).setProjectInfo(with(any(ProjectInfo.class)));
				one(bdocReport).run(null);
				will(returnValue(bdoc));
			}
		});

		bDocMojo.executeReport(null);
		context.assertIsSatisfied();
	}

	@Target( { ElementType.METHOD, ElementType.TYPE })
	public @interface MyTestAnnotation {
	}
}
