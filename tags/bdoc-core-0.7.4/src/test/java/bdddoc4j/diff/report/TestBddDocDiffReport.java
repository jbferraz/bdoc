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

package bdddoc4j.diff.report;

import static bdddoc4j.core.CustomAssert.assertXpathEvaluatesTo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.report.XmlReport;
import bdddoc4j.diff.domain.BddUserStoryDiffDocTestdataHelper;

@Ref(Story.DIFF_OF_BDOCS)
public class TestBddDocDiffReport {

	@Test
	public void givenTwoVersionsOfABddDocWhenABddDocDiffReportIsExecutedThenEnsureTheDiffIsFound() throws IOException {
		BddDocDiffReport bddDocDiffReport = new BddDocDiffReport();

		final String version1 = new XmlReport(BddUserStoryDiffDocTestdataHelper.getBddDocWithGeneralBehaviourAndAStory()).xml();
		final String version2 = new XmlReport(BddUserStoryDiffDocTestdataHelper.getBddDocWithUpdatedStory()).xml();

		final DiffReport diffReport = bddDocDiffReport.execute(version1, version2);

		FileUtils.writeStringToFile(new File("target/" + getClass().getName() + ".xml"), diffReport.xml());

		assertXpathEvaluatesTo("2", "count(*/userStories/updatedItems/userStoryDiff)", diffReport.xml());
	}
}