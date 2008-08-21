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

package bdddoc4j.core.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.junit.Test;

import bdddoc4j.core.util.ClassUtil;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestClassUtil {

	private static String basedirPath;

	@Test
	public void shouldVerifyThatTestdataDirectoryIsPresent() {
		assertTrue(testClassesDirectory().exists());
	}

	@Test
	public void shouldFindAllJavaClassesInADirectory() {
		List<String> classes = new ClassUtil(testClassesDirectory()).find();
		assertThatListContains(classes, "integrationtestclasses.SomeClass");
		assertThatListContains(classes, "integrationtestclasses.subpackage.AnotherClass");
		assertThatListContains(classes, "integrationtestclasses.subpackage.SomeOtherClass");
	}

	private void assertThatListContains(List<String> classes, String string) {

		if (!classes.contains(string)) {
			throw new AssertionFailedError("<" + string + "> not found in <" + classes + ">");
		}
	}

	private File testClassesDirectory() {
		File baseDir = new File(getBasedir());
		File integrationtestdata = new File(baseDir, "target/test-classes");
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
