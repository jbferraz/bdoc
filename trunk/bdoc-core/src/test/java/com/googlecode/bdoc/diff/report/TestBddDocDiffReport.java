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

package com.googlecode.bdoc.diff.report;

import static com.googlecode.bdoc.doc.XmlCustomAssert.assertXpathEvaluatesTo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.BDocUserStoryDiffDocTestdataHelper;
import com.googlecode.bdoc.diff.report.BddDocDiffReport;
import com.googlecode.bdoc.diff.report.DiffReport;
import com.googlecode.bdoc.doc.report.XmlReport;

/**
 *  @author Per Otto Bergum Christensen
 */
@Ref(Story.DIFF_OF_BDOCS)
public class TestBddDocDiffReport {

	@Test
	public void givenTwoVersionsOfABddDocWhenABddDocDiffReportIsExecutedThenEnsureTheDiffIsFound() throws IOException {
		BddDocDiffReport bddDocDiffReport = new BddDocDiffReport();

		final String version1 = new XmlReport(BDocUserStoryDiffDocTestdataHelper.getBddDocWithGeneralBehaviourAndAStory()).xml();
		final String version2 = new XmlReport(BDocUserStoryDiffDocTestdataHelper.getBddDocWithUpdatedStory()).xml();

		final DiffReport diffReport = bddDocDiffReport.execute(version1, version2);

		FileUtils.writeStringToFile(new File("target/" + getClass().getName() + ".xml"), diffReport.xml());

		assertXpathEvaluatesTo("2", "count(*/userStories/updatedItems/userStoryDiff)", diffReport.xml());
	}
}
