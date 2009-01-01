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

package com.googlecode.bdoc.diff.report;

import static com.googlecode.bdoc.doc.report.ReportTestHelper.scenarioPart;
import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.diff.domain.BddGeneralBehaviourDiffDocTestdataHelper;
import com.googlecode.bdoc.diff.domain.GeneralBehaviourDiff;
import com.googlecode.bdoc.diff.domain.TimeDiff;
import com.googlecode.bdoc.diff.report.BddDocDiffReport;
import com.googlecode.bdoc.diff.report.HtmlDiffReport;
import com.googlecode.bdoc.doc.report.ReportTestHelper;

/**
 * Tests the generation of the html diff report.
 * 
 * Tip: This testclass generates both the html version and the xml version of
 * the model, take a look at both to get a view on the model.
 * 
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.HTML_DIFF_REPORT)
public class TestHtmlGeneralBehaviourDiffReport {

	BDocDiff bDocDiff = BddGeneralBehaviourDiffDocTestdataHelper.getBddDocDiffForUpdatedGeneralBehaviour();
	{
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = (Calendar) time1.clone();
		time2.add(Calendar.MINUTE, 30);
		time2.add(Calendar.HOUR, 2);
		time2.add(Calendar.DAY_OF_WEEK, 15);

		bDocDiff.setDocTimeDiff(new TimeDiff(time1, time2));
	}
	private GeneralBehaviourDiff generalBehaviourDiff = bDocDiff.getGeneralBehaviourDiff();

	private String html;

	public TestHtmlGeneralBehaviourDiffReport() throws IOException {
		final String xml = new BddDocDiffReport().execute(bDocDiff).xml();
		html = new HtmlDiffReport(bDocDiff).html();

		writeStringToFile(new File("target/" + getClass().getName() + ".xml"), xml);
		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);
	}

	@Test
	public void shouldPresentNewPackages() {
		assertXPathContains(generalBehaviourDiff.getNewPackages().get(0).getName(), "//div[@id='newPackages']", html);
	}

	@Test
	public void shouldPresentScenariosInNewPackages() {
		assertXPathContains(scenarioPart(0, generalBehaviourDiff.getNewPackages().get(0).getClassBehaviour().get(0).getScenarios().get(0)),
				"//div[@id='newPackages']", html);
	}

	@Test
	public void shouldPresentSpecificationsInNewPackages() {

		assertXPathContains(ReportTestHelper.sentence(generalBehaviourDiff.getNewPackages().get(0).getClassBehaviour().get(0)
				.getSpecifications().get(0)), "//div[@id='newPackages']", html);
	}

	@Test
	public void shouldPresentDeletedPackages() {
		assertXPathContains(generalBehaviourDiff.getDeletedPackages().get(0).getName(), "//div[@id='deletedPackages']", html);
	}

	@Test
	public void shouldPresentSpecificationsInDeletedPackages() {
		assertXPathContains(ReportTestHelper.sentence(generalBehaviourDiff.getDeletedPackages().get(0).getClassBehaviour().get(0)
				.getSpecifications().get(0)), "//div[@id='deletedPackages']", html);
	}

	@Test
	public void shouldPresentScenariosInDeletedPackages() {
		assertXPathContains(scenarioPart(0, generalBehaviourDiff.getDeletedPackages().get(0).getClassBehaviour().get(0).getScenarios().get(
				0)), "//div[@id='deletedPackages']", html);
	}

	@Test
	public void shouldPresentNewScenariosInUpdatedPackages() {
		assertXPathContains(scenarioPart(0, generalBehaviourDiff.getPackageDiff().get(0).getNewScenarios().get(0)),
				"//div[@id='updatedPackages']", html);
	}

	@Test
	public void shouldPresentDeletedScenariosInUpdatedPackages() {
		assertXPathContains(scenarioPart(0, generalBehaviourDiff.getPackageDiff().get(0).getDeletedScenarios().get(0)),
				"//div[@id='updatedPackages']", html);
	}

	@Test
	public void shouldPresentNewSpecificationsInUpdatedPackages() {
		assertXPathContains(ReportTestHelper.sentence(generalBehaviourDiff.getPackageDiff().get(0).getNewClassSpecifications().get(0)
				.getSpecifications().get(0)), "//div[@id='updatedPackages']", html);
	}

	@Test
	public void shouldPresentDeletedSpecificationsInUpdatedPackages() {
		assertXPathContains(ReportTestHelper.sentence(generalBehaviourDiff.getPackageDiff().get(0).getDeletedClassSpecifications().get(0)
				.getSpecifications().get(0)), "//div[@id='updatedPackages']", html);
	}

}
