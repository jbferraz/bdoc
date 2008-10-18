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

import bdddoc4j.core.domain.BDoc;
import bdddoc4j.core.report.XmlReport;
import bdddoc4j.diff.domain.BddDocDiff;
import bdddoc4j.diff.domain.ClassBehaviourDiff;
import bdddoc4j.diff.domain.GeneralBehaviourDiff;
import bdddoc4j.diff.domain.NarrativeDiff;
import bdddoc4j.diff.domain.PackageDiff;
import bdddoc4j.diff.domain.UserStoryDiff;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BddDocDiffReport {

	public DiffReport execute(String oldXmlVersion, String newXmlVersion) {
		BDoc oldVersion = XmlReport.createBddDoc(oldXmlVersion);
		BDoc newVersion = XmlReport.createBddDoc(newXmlVersion);

		BddDocDiff bddDocDiff = new BddDocDiff(oldVersion, newVersion);

		return execute(bddDocDiff);
	}

	public DiffReport execute(BddDocDiff bddDocDiff) {
		XStream xstream = new XStream(new DomDriver());

		XmlReport.addAlias(xstream);

		xstream.alias("userStoryDiff", UserStoryDiff.class);
		xstream.alias("packageDiff", PackageDiff.class);
		xstream.alias("bddDocDiff", BddDocDiff.class);
		xstream.alias("codeBehaviourDiff", GeneralBehaviourDiff.class);
		xstream.alias("generalBehaviourDiff", GeneralBehaviourDiff.class);
		xstream.alias("narrativeDiff", NarrativeDiff.class);
		xstream.alias("classBehaviourDiff", ClassBehaviourDiff.class);

		DiffReport diffReport = new DiffReport(bddDocDiff.diffExists(), xstream.toXML(bddDocDiff));
		if (diffReport.diffExists()) {
			diffReport.setHtmlReport(new HtmlDiffReport(bddDocDiff).html());
		}
		return diffReport;
	}

}
