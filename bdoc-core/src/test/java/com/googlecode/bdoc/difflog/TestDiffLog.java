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

package com.googlecode.bdoc.difflog;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.difflog.DiffLog;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;

/**
 *  @author Per Otto Bergum Christensen
 */
public class TestDiffLog {

	private File changeLogXmlFile = new File("target/changeLog.xml");

	@Before
	public void resetXmlFile() {
		changeLogXmlFile.delete();
	}

	@Test
	public void shouldBeAbleToReadAndWriteChangeLogInstanceFromXmlFile() {
		DiffLog diffLog = new DiffLog();
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());

		diffLog.writeToFile(changeLogXmlFile);
		DiffLog changeLogfromFile = DiffLog.fromXmlFile(changeLogXmlFile);
		assertEquals(diffLog.latestBDoc(), changeLogfromFile.latestBDoc());
	}

	@Test
	public void shouldNotProduceAnyDiffForTheFirstBDocScan() {
		DiffLog diffLog = new DiffLog();
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertTrue(diffLog.getDiffList().isEmpty());
	}

	@Test
	public void shouldNotAddBDocDiffIfNoDiffExists() {
		DiffLog diffLog = new DiffLog();
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertEquals(0, diffLog.getDiffList().size());
	}

	@Test
	public void shouldAddBDocDiffWhenDiffExists() {
		DiffLog diffLog = new DiffLog();
		diffLog.scan(BDocTestHelper.bdocWithProject());
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertEquals(1, diffLog.getDiffList().size());
	}

}
