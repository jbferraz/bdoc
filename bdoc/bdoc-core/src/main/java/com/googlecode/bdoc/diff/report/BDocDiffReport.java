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


import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.diff.domain.ClassBehaviourDiff;
import com.googlecode.bdoc.diff.domain.ModuleBehaviourDiff;
import com.googlecode.bdoc.diff.domain.NarrativeDiff;
import com.googlecode.bdoc.diff.domain.PackageDiff;
import com.googlecode.bdoc.diff.domain.UserStoryDiff;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.report.XmlReport;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * @author Per Otto Bergum Christensen
 */
public class BDocDiffReport {

	public DiffReport execute(String oldXmlVersion, String newXmlVersion,BDocConfig bdocConfig) {
		BDoc oldVersion = XmlReport.createBDoc(oldXmlVersion);
		BDoc newVersion = XmlReport.createBDoc(newXmlVersion);

		BDocDiff bDocDiff = new BDocDiff(oldVersion, newVersion);

		return execute(bDocDiff,bdocConfig);
	}

	public DiffReport execute(BDocDiff bdocDiff,BDocConfig bdocConfig ) {
		XStream xstream = new XStream(new DomDriver());

		XmlReport.addAlias(xstream);

		xstream.alias("userStoryDiff", UserStoryDiff.class);
		xstream.alias("packageDiff", PackageDiff.class);
		xstream.alias("bddDocDiff", BDocDiff.class);
		xstream.alias("codeBehaviourDiff", ModuleBehaviourDiff.class);
		xstream.alias("generalBehaviourDiff", ModuleBehaviourDiff.class);
		xstream.alias("narrativeDiff", NarrativeDiff.class);
		xstream.alias("classBehaviourDiff", ClassBehaviourDiff.class);

		DiffReport diffReport = new DiffReport(bdocDiff.diffExists(), xstream.toXML(bdocDiff));
		if (diffReport.diffExists()) {
			diffReport.setHtmlReport(new HtmlDiffReport(bdocDiff,bdocConfig).html());
		}
		return diffReport;
	}

}
