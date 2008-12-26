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

import static bdddoc4j.core.report.ReportTestHelper.scenarioPart;
import static bdddoc4j.testutil.HtmlAssert.assertXPathContains;
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.report.ReportTestHelper;
import bdddoc4j.diff.domain.BddDocDiff;
import bdddoc4j.diff.domain.BddUserStoryDiffDocTestdataHelper;
import bdddoc4j.diff.domain.NarrativeDiff;
import bdddoc4j.diff.domain.StringDiff;
import bdddoc4j.diff.domain.TimeDiff;

/**
 * Tests the generation of the html diff report.
 * 
 * Tip: This testclass generates both the html version and the xml version of
 * the model, take a look at both to get a view on the model.
 * 
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.HTML_DIFF_REPORT)
public class TestHtmlUserstoryDiffReport {

	BddDocDiff bddDocDiff = BddUserStoryDiffDocTestdataHelper.getBddDocDiffForUpdatedStory();
	{
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = (Calendar) time1.clone();
		time2.add(Calendar.MINUTE, 30);
		time2.add(Calendar.HOUR, 2);
		time2.add(Calendar.DAY_OF_WEEK, 15);

		bddDocDiff.setDocTimeDiff(new TimeDiff(time1, time2));
	}
	private String html;

	public TestHtmlUserstoryDiffReport() throws IOException {
		final String xml = new BddDocDiffReport().execute(bddDocDiff).xml();
		html = new HtmlDiffReport(bddDocDiff).html();

		writeStringToFile(new File("target/" + getClass().getName() + ".xml"), xml);
		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);
	}

	@Test
	public void shouldPresentDocTimeDiff() {
		TimeDiff docTimeDiff = bddDocDiff.getDocTimeDiff();
		assertXPathContains(String.valueOf(docTimeDiff.getDays()), "//div[@id='docTimeDiff']", html);
		assertXPathContains(String.valueOf(docTimeDiff.getHours()), "//div[@id='docTimeDiff']", html);
		assertXPathContains(String.valueOf(docTimeDiff.getMinutes()), "//div[@id='docTimeDiff']", html);
	}

	@Test
	public void shouldPresentProjectInfo() {
		assertXPathContains(bddDocDiff.getProjectDiff().getNameDiff().getNewValue(), "//div[@id='projectInfo']", html);
		assertXPathContains(bddDocDiff.getProjectDiff().getVersionDiff().getNewValue(), "//div[@id='projectInfo']", html);
	}

	@Test
	public void shouldPresentUpdatedProjectInfo() {
		assertXPathContains(bddDocDiff.getProjectDiff().getNameDiff().getOldValue(), "//ul[@id='projectDiff']", html);
		assertXPathContains(bddDocDiff.getProjectDiff().getNameDiff().getNewValue(), "//ul[@id='projectDiff']", html);
		assertXPathContains(bddDocDiff.getProjectDiff().getVersionDiff().getOldValue(), "//ul[@id='projectDiff']", html);
		assertXPathContains(bddDocDiff.getProjectDiff().getVersionDiff().getNewValue(), "//ul[@id='projectDiff']", html);
	}

	@Test
	public void shouldPresentNewStories() {
		assertXPathContains(bddDocDiff.getNewStories().get(0).getTitle(), "//div[@id='newStories']", html);
	}

	@Test
	public void shouldPresentUpdatedStories() {
		assertXPathContains(bddDocDiff.getUpdatedStories().get(0).getTitle(), "//div[@id='updatedStories']", html);
	}

	@Test
	public void shouldPresentDeletedStories() {
		assertXPathContains(bddDocDiff.getDeletedStories().get(0).getTitle(), "//div[@id='deletedStories']", html);
	}

	@Test
	public void shouldPresentNewScenariosAssociatedWithAUserStory() {
		assertXPathContains(scenarioPart(0, bddDocDiff.getUpdatedStories().get(0).getNewScenarios().get(0)), "//li[@class='newScenarios']",
				html);
	}

	@Test
	public void shouldPresentDeletedScenariosAssociatedWithAUserStory() {
		assertXPathContains(scenarioPart(0, bddDocDiff.getUpdatedStories().get(0).getDeletedScenarios().get(0)),
				"//li[@class='deletedScenarios']", html);
	}

	@Test
	public void shouldPresentNewClassSpecificationsAssociatedWithAUserStory() {
		assertXPathContains(ReportTestHelper.sentence(bddDocDiff.getUpdatedStories().get(0).getNewClassSpecifications().get(0)
				.getSpecifications().get(0)), "//li[@class='newSpecifications']", html);
	}

	@Test
	public void shouldPresentDeletedClassSpecificationsAssociatedWithAUserStory() {
		assertXPathContains(ReportTestHelper.sentence(bddDocDiff.getUpdatedStories().get(0).getDeletedClassSpecifications().get(0)
				.getSpecifications().get(0)), "//li[@class='deletedSpecifications']", html);
	}

	@Test
	public void shouldPresentNewClassStatementsAssociatedWithAUserStory() {
		assertXPathContains(bddDocDiff.getUpdatedStories().get(0).getNewClassStatements().get(0).getStatements().get(0).getSentence(),
				"//li[@class='newStatements']", html);
	}

	@Test
	public void shouldPresentDeletedClassStatementsAssociatedWithAUserStory() {
		assertXPathContains(bddDocDiff.getUpdatedStories().get(0).getDeletedClassStatements().get(0).getStatements().get(0).getSentence(),
				"//li[@class='deletedStatements']", html);
	}

	@Test
	public void shouldPresentUpdatedStoryTitle() {

		StringDiff titleDiff = bddDocDiff.getUpdatedStories().get(1).getTitleDiff();

		assertXPathContains(titleDiff.getOldValue(), "//li[@class='titleDiff']", html);
		assertXPathContains(titleDiff.getNewValue(), "//li[@class='titleDiff']", html);
	}

	@Test
	public void shouldPresentUpdatedStoryNarrative() {
		NarrativeDiff narrativeDiff = bddDocDiff.getUpdatedStories().get(1).getNarrativeDiff();

		assertXPathContains(narrativeDiff.getOldVersion().getRole(), "//li[@class='narrativeDiff']", html);
		assertXPathContains(narrativeDiff.getOldVersion().getAction(), "//li[@class='narrativeDiff']", html);
		assertXPathContains(narrativeDiff.getOldVersion().getBenefit(), "//li[@class='narrativeDiff']", html);

		assertXPathContains(narrativeDiff.getNewVersion().getRole(), "//li[@class='narrativeDiff']", html);
		assertXPathContains(narrativeDiff.getNewVersion().getAction(), "//li[@class='narrativeDiff']", html);
		assertXPathContains(narrativeDiff.getNewVersion().getBenefit(), "//li[@class='narrativeDiff']", html);
	}
}
