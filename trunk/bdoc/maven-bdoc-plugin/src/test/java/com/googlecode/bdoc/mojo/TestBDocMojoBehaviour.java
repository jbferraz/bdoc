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
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.sink.SinkAdapter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.difflog.DiffLog;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.report.AndInBetweenScenarioLinesFormatter;
import com.googlecode.bdoc.doc.util.ItemInListNotFoundException;
import com.googlecode.bdoc.mojo.testdata.TestIt;
import com.googlecode.bdoc.mojo.testdata.TestIt2;

/**
 *  @author Per Otto Bergum Christensen
 */
@SuppressWarnings("unchecked")
public class TestBDocMojoBehaviour {

	private BDocMojo bdocMojo = null;

	@Before
	public void resetMojo() {
		bdocMojo = new BDocMojo() {
			org.codehaus.doxia.sink.Sink sinkStub = new SinkAdapter();
			{
				bdocReportsXmlDirectoryPath = TestBDocMojo.TARGET;
				project = new MavenProjectMock();

				testAnnotationClassName = Test.class.getName();
				ignoreAnnotationClassName = Ignore.class.getName();
				scenarioFormatterClassName = AndInBetweenScenarioLinesFormatter.class.getName();
			}

			@Override
			public org.codehaus.doxia.sink.Sink getSink() {
				return sinkStub;
			};
		};
		
		bdocMojo.getBDocChangeLogFile().delete();
		bdocMojo.scenarioAnalyzer = "static";
	}

	private void givenMavenInjectedValuesForSourceDirectoryAndTestClassDirectoryAndOutputDirectory() {
		bdocMojo.testSourceDirectory = new File("src/test/java");
		bdocMojo.testClassDirectory = new File("target/test-classes");
		bdocMojo.outputDirectory = new File("target");
	}

	private Class givenAnIncludeThatSpecifiesOneTestClass(Class testClass) {
		String testClassPathInclude = testClass.getName().replace('.', '/');
		bdocMojo.includes = new String[] { testClassPathInclude + ".class" };
		return testClass;
	}

	private Class givenAnExcludeThatSpecifiesOneTestClass(Class testClass) {
		String testClassPathInclude = testClass.getName().replace('.', '/');
		bdocMojo.excludes = new String[] { testClassPathInclude + ".class" };
		return testClass;
	}

	private void whenTheBDocReportsMojoHasRun() throws MavenReportException {
		bdocMojo.executeReport(null);
	}

	private void thenEnsureTheBDocContainsClassBehaviourSpecifiedByTheTestClass(Class testClass) {
		bdocInChangeLogShouldContain(testClass);
	}

	private void thenEnsureTheBDocContainsBehaviourFromOtherTestClasses(Class<TestIt2> testClass) {
		bdocInChangeLogShouldContain(testClass);
	}

	private void thenEnsureTheChangeLogDoesNotContainBehaviourFromOtherTestClasses(Class testClass) {
		bdocInChangeLogShouldNotContain(testClass);
	}

	private void thenEnsureTheChangeLogDoesNotContainClassBehaviourSpecifiedByTheTestClass(Class testClass) {
		bdocInChangeLogShouldNotContain(testClass);
	}

	private void thenEnsureABDocHtmlReportHasBeenGenerated() {
		assertTrue(new File("target/" + BDocMojo.BDOC_USERSTORY_REPORT).exists());
	}

	@Test
	public void shouldRunBDocOnTheProjectTestCode() throws MavenReportException {
		givenMavenInjectedValuesForSourceDirectoryAndTestClassDirectoryAndOutputDirectory();
		whenTheBDocReportsMojoHasRun();
		thenEnsureABDocHtmlReportHasBeenGenerated();
	}

	@Test
	public void shouldIncludeTestsThatAreSpecified() throws MavenReportException {
		givenMavenInjectedValuesForSourceDirectoryAndTestClassDirectoryAndOutputDirectory();
		Class testClass = givenAnIncludeThatSpecifiesOneTestClass(TestIt.class);
		whenTheBDocReportsMojoHasRun();
		thenEnsureTheBDocContainsClassBehaviourSpecifiedByTheTestClass(testClass);
		thenEnsureTheChangeLogDoesNotContainBehaviourFromOtherTestClasses(TestIt2.class);
	}

	@Test
	public void shouldExcludeTestsThatAreSpecified() throws MavenReportException {
		givenMavenInjectedValuesForSourceDirectoryAndTestClassDirectoryAndOutputDirectory();
		Class testClass = givenAnExcludeThatSpecifiesOneTestClass(TestIt.class);
		whenTheBDocReportsMojoHasRun();
		thenEnsureTheChangeLogDoesNotContainClassBehaviourSpecifiedByTheTestClass(testClass);
		thenEnsureTheBDocContainsBehaviourFromOtherTestClasses(TestIt2.class);
	}

	// --------------------------------------------------------------------------------------------

	private void givenAnExistingBDocReportsXmlFile() {
		DiffLog diffLog = new DiffLog();
		BDoc bdoc = new BDoc(org.junit.Test.class, null, org.junit.Ignore.class);
		bdoc.setProject(new ProjectInfo("test", "test"));
		diffLog.scan(bdoc);
		diffLog.writeToFile( bdocMojo.getBDocChangeLogFile() );
	}

	private void thenEnsureTheBDocReportsXmlFileHasBeenUpdated() {
		DiffLog diffLog = DiffLog.fromXmlFile(bdocMojo.getBDocChangeLogFile() );
		assertTrue( 0 < diffLog.getDiffList().size() );
	}

	@Test
	public void shouldUpdateExistingBDocReportsXmlFile() throws MavenReportException {
		givenMavenInjectedValuesForSourceDirectoryAndTestClassDirectoryAndOutputDirectory();
		givenAnExistingBDocReportsXmlFile();
		whenTheBDocReportsMojoHasRun();
		thenEnsureTheBDocReportsXmlFileHasBeenUpdated();
	}

	// --------------------------------------------------------------------------------------------

	private void bdocInChangeLogShouldContain(Class testClass) {
		DiffLog diffLog = DiffLog.fromXmlFile(bdocMojo.getBDocChangeLogFile());
		ClassBehaviour classBehaviourFromFile = diffLog.latestBDoc().getModuleBehaviour().classBehaviourFor(testClass);
		assertEquals(new ClassBehaviour(testClass), classBehaviourFromFile);
	}

	private void bdocInChangeLogShouldNotContain(Class testClass) {
		DiffLog diffLog = DiffLog.fromXmlFile(bdocMojo.getBDocChangeLogFile());
		try {
			diffLog.latestBDoc().getModuleBehaviour().classBehaviourFor(testClass);
			fail("testclass did exist in bdoc: " + testClass);
		} catch (ItemInListNotFoundException e) {
			// should occur
		}
	}
}
