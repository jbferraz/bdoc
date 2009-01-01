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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
	
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;
import com.googlecode.bdoc.doc.testdata.RefClass;

@Ref(Story.CHANGE_LOG)
@RefClass(ChangeLog.class)
public class TestChangeLogBehaviour {

	private ChangeLog givenAnNewChangeLog() {
		return new ChangeLog();
	}

	private BDoc whenABDocWithOneSpecificationIsScannedByTheChangeLog(ChangeLog changeLog) {
		BDoc bdoc = BDocTestHelper.bdocWithOneSpecification();
		changeLog.scan(bdoc);
		return bdoc;
	}

	private void thenEnsureTheLatestBdocInTheChangeLogContainsTheBDoc(ChangeLog changeLog, BDoc bdoc) {
		assertSame(bdoc, changeLog.latestBDoc());
	}

	private void thenEnsureTheLatestDiffInTheChangeLogContainsTheSpecification(ChangeLog changeLog, Specification specification) {
		BDocDiff diff = changeLog.latestDiff();
		assertEquals(specification, diff.getGeneralBehaviourDiff().getNewPackages().get(0).getClassSpecifications().get(0)
				.getSpecifications().get(0));
	}

	@Test
	public void shouldAddABDocDiffForAChange() {
		ChangeLog changeLog = givenAnNewChangeLog();
		BDoc bdoc = whenABDocWithOneSpecificationIsScannedByTheChangeLog(changeLog);
		thenEnsureTheLatestBdocInTheChangeLogContainsTheBDoc(changeLog, bdoc);
		Specification specification = bdoc.getGeneralBehaviour().getPackages().get(0).getClassSpecifications().get(0).getSpecifications()
				.get(0);
		thenEnsureTheLatestDiffInTheChangeLogContainsTheSpecification(changeLog, specification);
	}

}
