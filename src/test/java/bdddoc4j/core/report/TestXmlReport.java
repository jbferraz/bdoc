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

package bdddoc4j.core.report;

import static bdddoc4j.core.XmlCustomAssert.assertXpathEvaluatesTo;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.domain.BDoc;
import bdddoc4j.core.testdata.BddDocTestHelper;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.XML_REPORT)
public class TestXmlReport {

	private String xml;
	private BDoc bddDoc;

	@Before
	public void setupTestdata() throws IOException {
		bddDoc = BddDocTestHelper.bddDocWithTwoStoriesThreeScenariosFourSpecificationsAndGeneralBehaviour();
		xml = new XmlReport(bddDoc).xml();
		FileUtils.writeStringToFile(new File("target/" + getClass().getName() + ".xml"), xml);
	}

	@Test
	public void shouldContainNameOfTheApplication() throws Exception {
		assertXpathEvaluatesTo(bddDoc.getProject().getName(), "*/project/name", xml);
	}

	@Test
	public void shouldContainStories() {
		assertXpathEvaluatesTo("2", "count(*/userStories/*)", xml);
	}

	@Test
	public void shouldContainStoriesWithScenarios() {
		assertXpathEvaluatesTo("3", "count(//userStories//scenarios/scenario)", xml);
	}

	@Test
	public void shouldContainStoriesWithSpecifications() {
		assertXpathEvaluatesTo("7", "count(//userStories//specifications/specification)", xml);
	}

	@Test
	public void shouldContainGeneralBehaviourWithSpecifications() {
		assertXpathEvaluatesTo("2", "count(//generalBehaviour//specifications/specification)", xml);
	}

	@Test
	public void shouldContainGeneralBehaviourWithScenarios() {
		assertXpathEvaluatesTo("2", "count(//generalBehaviour//scenarios/scenario)", xml);
	}

	@Test
	public void shouldTransformBddDocAsXmlToBddDoc() {
		assertEquals(bddDoc, XmlReport.createBddDoc(xml));
	}

}
