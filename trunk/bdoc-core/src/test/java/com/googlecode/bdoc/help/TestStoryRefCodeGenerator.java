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

package com.googlecode.bdoc.help;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.help.StoryRefCodeGenerator;
import com.googlecode.bdoc.help.StoryRefCodeGenerator.Result;


@Ref(Story.STORY_REFERENCE_CODE_GENERATOR)
public class TestStoryRefCodeGenerator {

	private static final File testRootDirectory = new File("target/tmp");

	private static final String javaPackage = "root.sub";
	private static final File javaDir = new File(testRootDirectory + "/root/sub");

	private static final File JAVA_REF_FILE = new File(javaDir, "Ref.java");

	private static final File JAVA_STORY_FILE = new File(javaDir, "Story.java");

	private StoryRefCodeGenerator storyRefCodeGenerator;

	public TestStoryRefCodeGenerator() throws IOException {
		FileUtils.forceMkdir(testRootDirectory);

		storyRefCodeGenerator = new StoryRefCodeGenerator();
		storyRefCodeGenerator.setJavaPackage(javaPackage);
		storyRefCodeGenerator.setTestDirectory(testRootDirectory);
	}

	@Before
	public void removeOldTestFilesAndExecuteStoryRefCodeGenerator() throws IOException {
		FileUtils.deleteDirectory(testRootDirectory);
		storyRefCodeGenerator.execute();
	}

	@Test
	public void shouldGenerateTheDirectoryForThePackageOfTheGeneratedJavaFiles() {
		assertTrue(javaDir.isDirectory());
	}

	@Test
	public void shouldGenerateARefJavaAnnotationSourceFileInTheTestDirectoryWithTheGivenJavaPackage() {
		assertTrue(JAVA_REF_FILE.isFile());
	}

	@Test
	public void shouldGenerateAStoryEnumSoureFileInTheTestDirectoryWithTheGivenJavaPackage() {
		assertTrue(JAVA_STORY_FILE.isFile());
	}

	@Test
	public void shouldReportThatAStoryJavaFileExistsBeforeWritingANew() throws IOException {
		FileUtils.deleteQuietly(JAVA_REF_FILE);
		assertEquals(Result.STORY_JAVA_EXISTS, storyRefCodeGenerator.execute());
	}

	@Test
	public void shouldGiveErrorIfARefJavaFileExistsBeforeWritingANew() throws IOException {
		FileUtils.deleteQuietly(JAVA_STORY_FILE);
		assertEquals(Result.REF_JAVA_EXISTS, storyRefCodeGenerator.execute());
	}

	@Test
	public void shouldGenerateStoryJavaFileWithTheGivenPackage() throws IOException {
		String refJavaAsString = FileUtils.readFileToString(JAVA_STORY_FILE);
		assertTrue(refJavaAsString.contains("package " + javaPackage));
	}

	@Test
	public void shouldGenerateRefJavaFileWithTheGivenPackage() throws IOException {
		String refJavaAsString = FileUtils.readFileToString(JAVA_REF_FILE);
		assertTrue(refJavaAsString.contains("package " + javaPackage));
	}
}
