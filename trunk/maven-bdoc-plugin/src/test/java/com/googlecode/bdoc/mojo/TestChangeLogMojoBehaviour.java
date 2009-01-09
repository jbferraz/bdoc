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

import java.io.File;

import org.apache.maven.reporting.MavenReportException;
import org.junit.Test;

import com.googlecode.bdoc.clog.ChangeLog;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.mojo.testdata.TestIt;

public class TestChangeLogMojoBehaviour {

	private ChangeLogMojo changeLogMojo = new ChangeLogMojo();

	public TestChangeLogMojoBehaviour() {
		changeLogMojo.changeLogDirectoryPath = TestChangeLogMojo.TARGET;
	}

	@Test
	@SuppressWarnings("unchecked")
	public void shouldRunBDocOnTheProjectTestCode() throws MavenReportException {
		givenATestSourceDirectory();
		givenATestClassDirectory();
		Class testClass = givenAnIncludeThatSpecifiesOneTestClass();
		whenTheChangeLogMojoHasRun();
		thenEnsureTheChangeLogContainsClassBehaviourSpecifiedByTheTestClass(testClass);
	}

	private void givenATestSourceDirectory() {
		changeLogMojo.testSourceDirectory = new File("src/test/java");
	}

	private void givenATestClassDirectory() {
		changeLogMojo.testClassDirectory = new File("target/test-classes");
	}

	@SuppressWarnings("unchecked")
	private Class givenAnIncludeThatSpecifiesOneTestClass() {
		Class testClass = TestIt.class;
		String testClassPathInclude = testClass.getName().replace('.', '/');
		changeLogMojo.includes = new String[] { testClassPathInclude + ".class" };
		return testClass;
	}

	private void whenTheChangeLogMojoHasRun() throws MavenReportException {
		changeLogMojo.executeReport(null);
	}

	@SuppressWarnings("unchecked")
	private void thenEnsureTheChangeLogContainsClassBehaviourSpecifiedByTheTestClass(Class testClass) {
		ChangeLog changeLog = ChangeLog.fromXmlFile(changeLogMojo.getBDocChangeLogFile());
		ClassBehaviour classBehaviourFromFile = changeLog.latestBDoc().getGeneralBehaviour().classBehaviourFor(testClass);
		assertEquals(new ClassBehaviour(testClass), classBehaviourFromFile);
	}

}