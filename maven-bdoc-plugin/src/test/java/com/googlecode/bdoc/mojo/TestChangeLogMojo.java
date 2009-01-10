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

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.googlecode.bdoc.clog.ChangeLog;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.report.BDocReportInterface;

public class TestChangeLogMojo {

	public static final String TARGET = "target";

	private Mockery context = new Mockery();

	private BDocReportInterface bdocReport;
	private BDoc bdoc;

	private ChangeLogMojo changeLogMojo = new ChangeLogMojo() {
		@Override
		String getBDocChangeLogRootDirectoryPath() {
			return TARGET;
		}

		@Override
		String getBDocChangeLogFileName() {
			return "fileName.xml";
		}
	};

	public TestChangeLogMojo() {
		changeLogMojo.changeLogDirectoryPath = TARGET;

		MavenProject mavenProject = new MavenProjectMock();
		mavenProject.setGroupId("groupId");
		mavenProject.setArtifactId("artifactId");

		changeLogMojo.project = mavenProject;

	}

	public void initalizeBDocReportMock() {
		changeLogMojo.getBDocChangeLogFile().delete();
		bdocReport = context.mock(BDocReportInterface.class);

		bdoc = new BDoc();
		bdoc.setProject(new ProjectInfo("name", "version"));

		context.checking(new Expectations() {
			{
				one(bdocReport).setTestClassDirectory(null);
				one(bdocReport).setClassLoader(with(any(ClassLoader.class)));
				one(bdocReport).setProjectInfo(with(any(ProjectInfo.class)));
				one(bdocReport).run(null);
				will(returnValue(bdoc));
			}
		});

		changeLogMojo.bdocReport = bdocReport;
	}

	@Test
	public void shouldCreateANewChangeLogXmlWhenOneDoesNotExist() throws MavenReportException {
		initalizeBDocReportMock();
		changeLogMojo.executeReport(null);
		assertTrue(changeLogMojo.getBDocChangeLogFile().exists());
	}

	@Test
	public void shouldUpdateTheLatestBDocInThePersistedChangeLog() throws MavenReportException, IOException {
		initalizeBDocReportMock();
		changeLogMojo.executeReport(null);
		ChangeLog updatedChangeLog = ChangeLog.fromXmlFile(changeLogMojo.getBDocChangeLogFile());
		assertEquals(bdoc.getProject(), updatedChangeLog.latestBDoc().getProject());
	}

	@Test
	public void shouldUseTheUserHomeDirectoryConcatenatedWithBDocAsRootDirectoryForPersistedBDocChangeLogs() {
		assertEquals(System.getProperty("user.home") + "/bdoc", new ChangeLogMojo().getBDocChangeLogRootDirectoryPath());
	}

	@Test
	public void shouldBuildTheBDocChangeLogFileUpFromBDocChangeLogRootDirectoryAndProjectGroupIdAndProjectArticfactId() {
		File expectedChangeLogFile = new File("target/groupId/artifactId/fileName.xml");
		assertEquals(expectedChangeLogFile, changeLogMojo.getBDocChangeLogFile());
	}
}
