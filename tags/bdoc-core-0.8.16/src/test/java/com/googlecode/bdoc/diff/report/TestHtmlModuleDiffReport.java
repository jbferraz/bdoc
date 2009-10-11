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

import static com.googlecode.bdoc.testutil.HtmlAssert.assertXPathContains;
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.diff.domain.TimeDiff;
import com.googlecode.bdoc.diff.report.moduletestdata.deletedpackage.DeletedPackageTest;
import com.googlecode.bdoc.diff.report.moduletestdata.newpackage.NewPackageTest;
import com.googlecode.bdoc.diff.report.moduletestdata.updatedpackage.UpdatedPackageTest;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TestClass;

/**
 * Tests the generation of the html diff report.
 * 
 * Tip: This testclass generates both the html version and the xml version of
 * the model, take a look at both to get a view on the model.
 * 
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.HTML_DIFF_REPORT)
public class TestHtmlModuleDiffReport {

	private final BDoc oldBddDoc = new BDoc();
	private final BDoc newBddDoc = new BDoc();
	{
		ProjectInfo projectInfo = new ProjectInfo("test-project", "version1");
		oldBddDoc.setProject(projectInfo);
		oldBddDoc.addBehaviourFrom(new TestClass(DeletedPackageTest.class), BConst.SRC_TEST_JAVA);
		oldBddDoc.addBehaviourFrom(new TestClass(UpdatedPackageTest.class), BConst.SRC_TEST_JAVA);
		ClassBehaviour classWithDeletedBehaviour = oldBddDoc.classBehaviourInModuleBehaviour(UpdatedPackageTest.class);
		classWithDeletedBehaviour.addScenario(new Scenario("givenADeletedScenarioForATestClassWhenThen"));
		classWithDeletedBehaviour.addSpecification(new Specification("shouldBeDeleted"));
		classWithDeletedBehaviour.addStatement( new Statement( "statementWillBeDeleted" ));

		newBddDoc.setProject(projectInfo);
		newBddDoc.addBehaviourFrom(new TestClass(NewPackageTest.class), BConst.SRC_TEST_JAVA);
		newBddDoc.addBehaviourFrom(new TestClass(UpdatedPackageTest.class), BConst.SRC_TEST_JAVA);

		ClassBehaviour classWithNewBehaviour = newBddDoc.classBehaviourInModuleBehaviour(UpdatedPackageTest.class);
		classWithNewBehaviour.addScenario(new Scenario("givenANewScenarioForATestClassWhenThen"));
		classWithNewBehaviour.addSpecification(new Specification("shouldBeNew"));

	}
	BDocDiff bDocDiff = new BDocDiff(oldBddDoc, newBddDoc);
	{
		Calendar time1 = Calendar.getInstance();
		Calendar time2 = (Calendar) time1.clone();
		time2.add(Calendar.MINUTE, 30);
		time2.add(Calendar.HOUR, 2);
		time2.add(Calendar.DAY_OF_WEEK, 15);

		bDocDiff.setDocTimeDiff(new TimeDiff(time1, time2));
	}
	private String html;

	public TestHtmlModuleDiffReport() throws IOException {
		final String xml = new BDocDiffReport().execute(bDocDiff,new BDocConfig()).xml();
		html = new HtmlDiffReport(bDocDiff,new BDocConfig()).html();

		writeStringToFile(new File("target/" + getClass().getName() + ".xml"), xml);
		writeStringToFile(new File("target/" + getClass().getName() + ".html"), html);
	}

	@Test
	public void shouldPresentNewPackages() {
		assertXPathContains(NewPackageTest.class.getPackage().getName(), "//div[@class='newPackages']", html);
	}

	@Test
	public void shouldPresentScenariosInNewPackages() {
		assertXPathContains("Given a new package", "//div[@class='newPackages']", html);
	}

	@Test
	public void shouldPresentSpecificationsInNewPackages() {
		assertXPathContains("Should be in a new package", "//div[@class='newPackages']", html);
	}

	@Test
	public void shouldPresentDeletedPackages() {
		assertXPathContains(DeletedPackageTest.class.getPackage().getName(), "//div[@class='deletedPackages']", html);
	}

	@Test
	public void shouldPresentSpecificationsInDeletedPackages() {
		assertXPathContains("Should be in a deleted package", "//div[@class='deletedPackages']", html);
	}

	@Test
	public void shouldPresentScenariosInDeletedPackages() {
		assertXPathContains("Given a deleted package", "//div[@class='deletedPackages']", html);
	}

	@Test
	public void shouldPresentNewScenariosInUpdatedPackages() {
		assertXPathContains("Given a new scenario for a test class", "//div[@class='updatedPackages']", html);
	}

	@Test
	public void shouldPresentDeletedScenariosInUpdatedPackages() {
		assertXPathContains("Given a deleted scenario for a test class", "//div[@class='updatedPackages']", html);
	}

	@Test
	public void shouldPresentNewSpecificationsInUpdatedPackages() {
		assertXPathContains("Should be new", "//div[@class='updatedPackages']", html);
	}

	@Test
	public void shouldPresentDeletedSpecificationsInUpdatedPackages() {
		assertXPathContains("Should be deleted", "//div[@class='updatedPackages']", html);
	}

	@Test
	public void shouldPresentDeletedStatementsInUpdatedPackages() {
		assertXPathContains("Statement will be deleted", "//div[@class='updatedPackages']", html);
	}
}
