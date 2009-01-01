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


import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.diff.domain.ClassBehaviourDiff;
import com.googlecode.bdoc.diff.domain.GeneralBehaviourDiff;
import com.googlecode.bdoc.diff.domain.NarrativeDiff;
import com.googlecode.bdoc.diff.domain.PackageDiff;
import com.googlecode.bdoc.diff.domain.UserStoryDiff;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.report.XmlReport;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BddDocDiffReport {

	public DiffReport execute(String oldXmlVersion, String newXmlVersion) {
		BDoc oldVersion = XmlReport.createBddDoc(oldXmlVersion);
		BDoc newVersion = XmlReport.createBddDoc(newXmlVersion);

		BDocDiff bDocDiff = new BDocDiff(oldVersion, newVersion);

		return execute(bDocDiff);
	}

	public DiffReport execute(BDocDiff bDocDiff) {
		XStream xstream = new XStream(new DomDriver());

		XmlReport.addAlias(xstream);

		xstream.alias("userStoryDiff", UserStoryDiff.class);
		xstream.alias("packageDiff", PackageDiff.class);
		xstream.alias("bddDocDiff", BDocDiff.class);
		xstream.alias("codeBehaviourDiff", GeneralBehaviourDiff.class);
		xstream.alias("generalBehaviourDiff", GeneralBehaviourDiff.class);
		xstream.alias("narrativeDiff", NarrativeDiff.class);
		xstream.alias("classBehaviourDiff", ClassBehaviourDiff.class);

		DiffReport diffReport = new DiffReport(bDocDiff.diffExists(), xstream.toXML(bDocDiff));
		if (diffReport.diffExists()) {
			diffReport.setHtmlReport(new HtmlDiffReport(bDocDiff).html());
		}
		return diffReport;
	}

}
