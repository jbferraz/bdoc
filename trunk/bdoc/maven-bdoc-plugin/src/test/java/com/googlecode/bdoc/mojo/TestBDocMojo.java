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
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.sink.SinkAdapter;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.difflog.DiffLog;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.JavaTestSourceBehaviourParser;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.report.AndInBetweenScenarioLinesFormatter;
import com.googlecode.bdoc.doc.report.BDocReportInterface;
import com.googlecode.bdoc.doc.report.ScenarioLinesFormatter;

/**
 * @author Per Otto Bergum Christensen
 */
@SuppressWarnings("unchecked")
public class TestBDocMojo {

	public static final String TARGET = "target";

	private Mockery context;

	private BDocReportInterface bdocReport;
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
		bdocReport = context.mock(BDocReportInterface.class);
		bdocDocMojo.bdocReport = bdocReport;

		context.checking(new Expectations() {
			{
				one(bdocReport).setTestClassDirectory(null);
				one(bdocReport).setIncludesFilePattern(null);
				one(bdocReport).setExcludesFilePattern(null);
				one(bdocReport).setClassLoader(with(any(ClassLoader.class)));
				one(bdocReport).setProjectInfo(with(any(ProjectInfo.class)));
				one(bdocReport).run(with(any(JavaTestSourceBehaviourParser.class)));
				will(returnValue(bdoc));
			}
		});

		bdocDocMojo.getBDocChangeLogFile().delete();
	}

	public void expectDefaultMavenConfigurationSetOnBDocReport() {
		bdocDocMojo.testAnnotationClassName = Test.class.getName();
		bdocDocMojo.ignoreAnnotationClassName = Ignore.class.getName();
		bdocDocMojo.scenarioFormatterClassName = AndInBetweenScenarioLinesFormatter.class.getName();
		expectConfiguredTestAnnotationSetOnTheBDocReport(Test.class);
		expectConfiguredIgnoreAnnotationSetOnTheBDocReport(Ignore.class);
		expectConfiguredScenarioFormatterClassSetOnTheBDocReport(new AndInBetweenScenarioLinesFormatter());
	}

	@Test
	public void shouldCreateANewChangeLogXmlWhenOneDoesNotExist() throws MavenReportException {
		expectDefaultMavenConfigurationSetOnBDocReport();
		whenTheReportIsExecuted();
		assertTrue(bdocDocMojo.getBDocChangeLogFile().exists());
	}

	@Test
	public void shouldUpdateTheLatestBDocInThePersistedChangeLog() throws MavenReportException, IOException {
		expectDefaultMavenConfigurationSetOnBDocReport();
		whenTheReportIsExecuted();
		DiffLog updatedChangeLog = DiffLog.fromXmlFile(bdocDocMojo.getBDocChangeLogFile());
		assertEquals(bdoc.getProject(), updatedChangeLog.latestBDoc().getProject());
	}

	@Test
	public void shouldUseTheUserHomeDirectoryConcatenatedWithBDocAsRootDirectoryForPersistedBDocChangeLogs() {
		assertEquals(System.getProperty("user.home") + "/bdoc", new BDocMojo().getBDocChangeLogRootDirectoryPath());
	}

	@Test
	public void shouldBuildTheBDocChangeLogFileUpFromProjectGroupIdAndProjectArticfactId() {
		String separator = System.getProperty("file.separator");
		String absolutePath = bdocDocMojo.getBDocChangeLogFile().getAbsolutePath();
		assertTrue(absolutePath.contains(separator + "groupId" + separator + "artifactId" + separator + AbstractBDocMojo.BDOC_REPORTS_XML));
	}

	// ------ Scenarios
	// ----------------------------------------------------------------------------------------------

	private void given_TestAnnotationClassName_IsConfiguredOtherThanDefaultValue(Class clazz) {
		bdocDocMojo.testAnnotationClassName = clazz.getName();
	}

	private void given_IgnoreAnnotationClassName_IsConfiguredOtherThanDefaultValue(Class clazz) {
		bdocDocMojo.ignoreAnnotationClassName = clazz.getName();
	}

	private void given_ScenarioFormatterClassName_IsConfiguredOtherThanDefaultValue(Class clazz) {
		bdocDocMojo.scenarioFormatterClassName = clazz.getName();
	}

	private void given_AStoryRefAnnotationAnnotation_IsConfigured(final Class<? extends Annotation> clazz) {
		bdocDocMojo.storyRefAnnotationClassName = clazz.getName();
	}

	private void expectConfiguredTestAnnotationSetOnTheBDocReport(final Class clazz) {
		context.checking(new Expectations() {
			{
				one(bdocReport).setTestAnnotation(with(clazz));
			}
		});
	}

	private void expectConfiguredIgnoreAnnotationSetOnTheBDocReport(final Class clazz) {
		context.checking(new Expectations() {
			{
				one(bdocReport).setIgnoreAnnotation(with(clazz));
			}
		});
	}

	private void expectConfiguredScenarioFormatterClassSetOnTheBDocReport(final ScenarioLinesFormatter scenarioLinesFormatter) {
		context.checking(new Expectations() {
			{
				one(bdocReport).setScenarioLinesFormatter(scenarioLinesFormatter);
			}
		});
	}

	private void expectConfiguredRefAnnotationSetOnTheBDocReport(final Class<? extends Annotation> clazz) {
		context.checking(new Expectations() {
			{
				one(bdocReport).setStoryRefAnnotation(clazz);
			}
		});
	}

	private void whenTheReportIsExecuted() throws MavenReportException {
		bdocDocMojo.executeReport(null);
	}

	private void thenEnsureExpectionsAreSatisfied() {
		context.assertIsSatisfied();
	}

	@Test
	public void shouldConfigureTheBDocReportFromMavenConfiguration() throws MavenReportException {
		given_TestAnnotationClassName_IsConfiguredOtherThanDefaultValue(MyTestAnnotation.class);
		given_IgnoreAnnotationClassName_IsConfiguredOtherThanDefaultValue(MyIgnoreAnnotation.class);
		given_ScenarioFormatterClassName_IsConfiguredOtherThanDefaultValue(MyScenarioLinesFormatter.class);
		given_AStoryRefAnnotationAnnotation_IsConfigured(MyRef.class);

		expectConfiguredTestAnnotationSetOnTheBDocReport(MyTestAnnotation.class);
		expectConfiguredIgnoreAnnotationSetOnTheBDocReport(MyIgnoreAnnotation.class);
		expectConfiguredScenarioFormatterClassSetOnTheBDocReport(new MyScenarioLinesFormatter());
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

	public static class MyScenarioLinesFormatter implements ScenarioLinesFormatter {
		public List<String> getLines(Scenario scenario) {
			return null;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof MyScenarioLinesFormatter;
		}
	}
}
