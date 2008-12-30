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

package com.googlecode.bdoc.doc.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.doc.util.ClassesDirectory;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestClassesDirectory {

	private static String basedirPath;

	private ClassesDirectory classesDirectory;

	@Before
	public void setup() {
		classesDirectory = new ClassesDirectory();
		classesDirectory.setBaseDir(testClassesDirectory());
	}

	@Test
	public void shouldVerifyThatTestdataDirectoryIsPresent() {
		assertTrue(testClassesDirectory().exists());
	}

	@Test
	public void shouldFindAllJavaClassesInADirectory() {
		List<String> classes = classesDirectory.classes();
		assertTrue(classes.contains("integrationtestclasses.SomeClass"));
		assertTrue(classes.contains("integrationtestclasses.subpackage.AnotherClass"));
		assertTrue(classes.contains("integrationtestclasses.subpackage.SomeOtherClass"));
	}

	@Test
	public void shouldOnlyAcceptFilesThatIsIncludedWithAntFilePattern() {
		classesDirectory.setIncludes(new String[] { "integrationtestclasses/SomeClass.class" });

		List<String> classes = classesDirectory.classes();
		assertTrue(classes.contains("integrationtestclasses.SomeClass"));
		assertFalse(classes.contains("integrationtestclasses.subpackage.AnotherClass"));
	}

	@Test
	public void shouldOnlyAcceptFilesThatIsIncludedWithAntFilePatternAndDontMatchTheAntExcludeFilePatterns() {

		classesDirectory.setIncludes(new String[] { "integrationtestclasses/" });
		classesDirectory.setExcludes(new String[] { "integrationtestclasses/subpackage/" });

		List<String> classes = classesDirectory.classes();
		assertTrue(classes.contains("integrationtestclasses.SomeClass"));
		assertFalse(classes.contains("integrationtestclasses.subpackage.AnotherClass"));
	}

	@Test
	public void shouldAcceptThatThereIsNoTestClassesDirectory() {
		classesDirectory.setBaseDir(unexistingDirectory());
		classesDirectory.classes();
	}

	private File testClassesDirectory() {
		File baseDir = new File(getBasedir());
		File integrationtestdata = new File(baseDir, "target/test-classes");
		return integrationtestdata;
	}

	private File unexistingDirectory() {
		File baseDir = new File(getBasedir());
		File integrationtestdata = new File(baseDir, "does/not/exists");
		return integrationtestdata;
	}

	private String getBasedir() {
		if (basedirPath != null) {
			return basedirPath;
		}

		basedirPath = System.getProperty("basedir");

		if (basedirPath == null) {
			basedirPath = new File("").getAbsolutePath();
		}

		return basedirPath;
	}
}
