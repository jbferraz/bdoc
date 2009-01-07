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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.maven.reporting.MavenReportException;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.clog.ChangeLog;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.Project;

public class TestChangeLogMojo {

	private static final String TARGET = "target";

	private Mockery context = new Mockery();

	private BDocScanner bdocScanner;
	private BDoc bdoc;

	private ChangeLogMojo changeLogMojo = new ChangeLogMojo();

	public TestChangeLogMojo() {
		changeLogMojo.setChangeLogDirectoryPath(TARGET);
	}

	@Before
	public void resetTestdata() {
		changeLogMojo.getBDocChangeLogFile().delete();
		bdocScanner = context.mock(BDocScanner.class);

		bdoc = new BDoc();
		bdoc.setProject(new Project("name", "version"));

		context.checking(new Expectations() {
			{
				one(bdocScanner).scan();
				will(returnValue(bdoc));
			}
		});

		changeLogMojo.setBDocScanner(bdocScanner);
	}

	@Test
	public void shouldCreateANewChangeLogXmlWhenOneDoesNotExist() throws MavenReportException {
		changeLogMojo.executeReport(null);
		assertTrue(changeLogMojo.getBDocChangeLogFile().exists());
	}

	@Test
	public void shouldUpdateTheLatestBDoc() throws MavenReportException, IOException {
		changeLogMojo.executeReport(null);
		ChangeLog updatedChangeLog = ChangeLog.fromXmlFile(changeLogMojo.getBDocChangeLogFile());
		assertNotNull(updatedChangeLog.latestBDoc());
		assertEquals(bdoc.getProject(), updatedChangeLog.latestBDoc().getProject());
	}
}
