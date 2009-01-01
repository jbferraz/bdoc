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

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;
import com.googlecode.bdoc.doc.testdata.RefClass;

@Ref(Story.CHANGE_LOG)
@RefClass(ChangeLog.class)
public class TestChangeLogBehaviour {

	private ChangeLog givenAnNewChangeLog() {
		return new ChangeLog();
	}

	@Test
	public void shouldAddABDocDiffForAChange() {
		ChangeLog changeLog = givenAnNewChangeLog();
		whenABDocWithOneSpecificationIsScannedByTheChangeLog(changeLog);
		thenEnsureTheChangeLogContainsTheBDoc();
		thenEnsureTheLatestDiffInTheChangeLogContainsTheSpecification();
	}

	private void thenEnsureTheChangeLogContainsTheBDoc() {
		// TODO Auto-generated method stub

	}

	private void thenEnsureTheLatestDiffInTheChangeLogContainsTheSpecification() {
		// TODO Auto-generated method stub

	}

	private void whenABDocWithOneSpecificationIsScannedByTheChangeLog(ChangeLog changeLog) {
		BDoc bdoc = BDocTestHelper.bdocWithOneSpecification();
	}

}
