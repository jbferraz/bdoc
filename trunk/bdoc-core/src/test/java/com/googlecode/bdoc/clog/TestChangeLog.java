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

package com.googlecode.bdoc.clog;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.doc.testdata.BDocTestHelper;

public class TestChangeLog {

	private File changeLogXmlFile = new File("target/changeLog.xml");

	@Before
	public void resetChangeLogXmlFile() {
		changeLogXmlFile.delete();
	}

	@Test
	public void shouldBeAbleToReadAndWriteChangeLogInstanceFromXmlFile() {
		ChangeLog changeLog = new ChangeLog();
		changeLog.scan(BDocTestHelper.bdocWithOneSpecification());

		changeLog.writeToFile(changeLogXmlFile);
		ChangeLog changeLogfromFile = ChangeLog.fromXmlFile(changeLogXmlFile);
		assertEquals(changeLog.latestBDoc(), changeLogfromFile.latestBDoc());
	}

	@Test
	public void shouldNotProduceAnyDiffForTheFirstBDocScan() {
		ChangeLog changeLog = new ChangeLog();
		changeLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertTrue(changeLog.diffList().isEmpty());
	}

	@Test
	public void shouldNotAddBDocDiffIfNoDiffExists() {
		ChangeLog changeLog = new ChangeLog();
		changeLog.scan(BDocTestHelper.bdocWithOneSpecification());
		changeLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertEquals(0, changeLog.diffList().size());
	}

	@Test
	public void shouldAddBDocDiffWhenDiffExists() {
		ChangeLog changeLog = new ChangeLog();
		changeLog.scan(BDocTestHelper.bdocWithProject());
		changeLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertEquals(1, changeLog.diffList().size());
	}

}
