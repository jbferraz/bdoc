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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.apache.maven.project.MavenProject;
import org.codehaus.doxia.sink.SinkAdapter;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.difflog.DiffLog;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.report.AndInBetweenScenarioLinesFormatter;
import com.googlecode.bdoc.doc.report.BDocFactory;
import com.googlecode.bdoc.doc.ztatic.JavaTestSourceBehaviourParser;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestBDocMojo {

	public static final String TARGET = "target";

	private Mockery context;

	private BDocFactory bdocReport;
	private BDoc bdoc;

	private BDocMojo bdocDocMojo = new BDocMojo() {
		org.codehaus.doxia.sink.Sink sinkStub = new SinkAdapter();

		@Override
		public org.codehaus.doxia.sink.Sink getSink() {
			return sinkStub;
		};
	};

	public TestBDocMojo() {
		bdocDocMojo.outputDirectory = new File(TARGET);

		MavenProject mavenProject = new MavenProjectMock();
		mavenProject.setGroupId("groupId");
		mavenProject.setArtifactId("artifactId");

		bdocDocMojo.project = mavenProject;
		bdocDocMojo.scenarioAnalyzer = "static";
	}

	@Before
	public void reset() {
		bdoc = new BDoc();
		bdoc.setProject(new ProjectInfo("name", "version"));

		context = new Mockery();
		bdocReport = context.mock(BDocFactory.class);
		bdocDocMojo.bdocFactory = bdocReport;

		context.checking(new Expectations() {
			{
				one(bdocReport).setTestClassDirectory(null);
				one(bdocReport).setIncludesFilePattern(null);
				one(bdocReport).setExcludesFilePattern(null);
				one(bdocReport).setClassLoader(with(any(ClassLoader.class)));
				one(bdocReport).setProjectInfo(with(any(ProjectInfo.class)));
				one(bdocReport).createBDoc(with(any(JavaTestSourceBehaviourParser.class)));
				will(returnValue(bdoc));
			}
		});

		bdocDocMojo.getBDocChangeLogFile().delete();
	}

	public void expectDefaultMavenConfigurationSetOnBDocReport() {
		bdocDocMojo.scenarioFormatterClassName = AndInBetweenScenarioLinesFormatter.class.getName();
	}

	@Test
	public void shouldCreateANewChangeLogXmlWhenOneDoesNotExist() throws Exception {
		expectDefaultMavenConfigurationSetOnBDocReport();
		whenTheReportIsExecuted();
		assertTrue(bdocDocMojo.getBDocChangeLogFile().exists());
	}

	@Test
	public void shouldUpdateTheLatestBDocInThePersistedChangeLog() throws Exception {
		expectDefaultMavenConfigurationSetOnBDocReport();
		whenTheReportIsExecuted();
		DiffLog updatedChangeLog = DiffLog.fromXmlFile(bdocDocMojo.getBDocChangeLogFile());
		assertEquals(bdoc.getProject(), updatedChangeLog.latestBDoc().getProject());
	}

	@Test
	public void shouldUseTheUserHomeDirectoryConcatenatedWithBDocAsRootDirectoryForPersistedBDocChangeLogs() {
		assertEquals(System.getProperty("user.home") + "/bdoc", BDocMojo.getBDocChangeLogRootDirectoryPath());
	}

	@Test
	public void shouldBuildTheBDocChangeLogFileUpFromProjectGroupIdAndProjectArticfactId() {
		String separator = System.getProperty("file.separator");
		String absolutePath = bdocDocMojo.getBDocChangeLogFile().getAbsolutePath();
		assertTrue(absolutePath.contains(separator + "groupId" + separator + "artifactId" + separator + AbstractBDocMojo.BDOC_REPORTS_XML));
	}

	// ------ Scenarios
	// ----------------------------------------------------------------------------------------------

	private void given_AStoryRefAnnotationAnnotation_IsConfigured(final Class<? extends Annotation> clazz) {
		bdocDocMojo.storyRefAnnotationClassName = clazz.getName();
	}

	private void expectConfiguredRefAnnotationSetOnTheBDocReport(final Class<? extends Annotation> clazz) {
		context.checking(new Expectations() {
			{
				one(bdocReport).setStoryRefAnnotation(clazz);
			}
		});
	}

	private void whenTheReportIsExecuted() throws Exception {
		bdocDocMojo.executeInternal();
	}

	private void thenEnsureExpectionsAreSatisfied() {
		context.assertIsSatisfied();
	}

	@Test
	public void shouldConfigureTheBDocReportFromMavenConfiguration() throws Exception {
		given_AStoryRefAnnotationAnnotation_IsConfigured(MyRef.class);

		expectConfiguredRefAnnotationSetOnTheBDocReport(MyRef.class);

		whenTheReportIsExecuted();
		thenEnsureExpectionsAreSatisfied();
	}

	// -------- Classes used as test data
	// -------------------------------------------------------

	@Target( { ElementType.METHOD, ElementType.TYPE })
	public @interface MyRef {
	}

	@Target( { ElementType.METHOD, ElementType.TYPE })
	public @interface MyTestAnnotation {
	}

	@Target( { ElementType.METHOD, ElementType.TYPE })
	public @interface MyIgnoreAnnotation {
	}

}
