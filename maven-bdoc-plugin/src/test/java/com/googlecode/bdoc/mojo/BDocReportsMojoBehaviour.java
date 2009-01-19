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
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.maven.reporting.MavenReportException;
import org.codehaus.doxia.sink.SinkAdapter;
import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.clog.ChangeLog;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.report.AndInBetweenScenarioLinesFormatter;
import com.googlecode.bdoc.doc.util.ItemInListNotFoundException;
import com.googlecode.bdoc.mojo.testdata.TestIt;
import com.googlecode.bdoc.mojo.testdata.TestIt2;

@SuppressWarnings("unchecked")
public class BDocReportsMojoBehaviour {

	private BDocReportsMojo bdocMojo = new BDocReportsMojo() {
		@SuppressWarnings("deprecation")
		org.codehaus.doxia.sink.Sink sinkStub = new SinkAdapter();
		{
			bdocReportsXmlDirectoryPath = TestBDocReportsMojo.TARGET;
			project = new MavenProjectMock();

			testAnnotationClassName = Test.class.getName();
			ignoreAnnotationClassName = Ignore.class.getName();
			scenarioFormatterClassName = AndInBetweenScenarioLinesFormatter.class.getName();
		}

		@SuppressWarnings("deprecation")
		@Override
		public org.codehaus.doxia.sink.Sink getSink() {
			return sinkStub;
		};
	};

	private void givenATestSourceDirectory() {
		bdocMojo.testSourceDirectory = new File("src/test/java");
	}

	private void givenATestClassDirectory() {
		bdocMojo.testClassDirectory = new File("target/test-classes");
	}

	private void givenAnOutputDirectory() {
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

	private void whenTheChangeLogMojoHasRun() throws MavenReportException {
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
		assertTrue(new File("target/" + BDocReportsMojo.BDOC_HTML).exists());
	}

	@Test
	public void shouldRunBDocOnTheProjectTestCode() throws MavenReportException {
		givenATestSourceDirectory();
		givenATestClassDirectory();
		givenAnOutputDirectory();
		whenTheChangeLogMojoHasRun();
		thenEnsureABDocHtmlReportHasBeenGenerated();
	}

	@Test
	public void shouldIncludeTestsThatAreSpecified() throws MavenReportException {
		givenATestSourceDirectory();
		givenATestClassDirectory();
		givenAnOutputDirectory();
		Class testClass = givenAnIncludeThatSpecifiesOneTestClass(TestIt.class);
		whenTheChangeLogMojoHasRun();
		thenEnsureTheBDocContainsClassBehaviourSpecifiedByTheTestClass(testClass);
		thenEnsureTheChangeLogDoesNotContainBehaviourFromOtherTestClasses(TestIt2.class);
	}

	@Test
	public void shouldExcludeTestsThatAreSpecified() throws MavenReportException {
		givenATestSourceDirectory();
		givenATestClassDirectory();
		givenAnOutputDirectory();
		Class testClass = givenAnExcludeThatSpecifiesOneTestClass(TestIt.class);
		whenTheChangeLogMojoHasRun();
		thenEnsureTheChangeLogDoesNotContainClassBehaviourSpecifiedByTheTestClass(testClass);
		thenEnsureTheBDocContainsBehaviourFromOtherTestClasses(TestIt2.class);
	}

	// --------------------------------------------------------------------------------------------

	private void bdocInChangeLogShouldContain(Class testClass) {
		ChangeLog changeLog = ChangeLog.fromXmlFile(bdocMojo.getBDocChangeLogFile());
		ClassBehaviour classBehaviourFromFile = changeLog.latestBDoc().getGeneralBehaviour().classBehaviourFor(testClass);
		assertEquals(new ClassBehaviour(testClass), classBehaviourFromFile);
	}

	private void bdocInChangeLogShouldNotContain(Class testClass) {
		ChangeLog changeLog = ChangeLog.fromXmlFile(bdocMojo.getBDocChangeLogFile());
		try {
			changeLog.latestBDoc().getGeneralBehaviour().classBehaviourFor(testClass);
			fail("testclass did exist in bdoc: " + testClass);
		} catch (ItemInListNotFoundException e) {
			// should occur
		}
	}
}
