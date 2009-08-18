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

package com.googlecode.bdoc.report;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestBDocReport {

	File baseDir = new File("./target");
	File reportDirectory = new File(baseDir, "bdoc");

	public TestBDocReport() throws IOException {
		if (reportDirectory.exists()) {
			FileUtils.forceDelete(reportDirectory);
		}
		BDocReport bdocReport = new BDocReport();
		bdocReport.writeTo(baseDir);
	}

	@Test
	public void shouldCreateBDocDirectoryInTargetDirectory() {
		assertTrue(reportDirectory.exists());
	}

	@Test
	public void shouldCreateIndexHtmlInBDocReportDirectory() {
		assertTrue(new File(reportDirectory, "index.html").exists());
	}

	@Test
	public void shouldCreateUserStoriesTocFrameInBdocReportDirectory() {
		assertTrue(new File(reportDirectory, "user_stories_toc_frame.html").exists());
	}
}
