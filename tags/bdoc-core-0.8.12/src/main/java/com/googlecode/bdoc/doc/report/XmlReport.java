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

package com.googlecode.bdoc.doc.report;

import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.ModuleBehaviour;
import com.googlecode.bdoc.doc.domain.Package;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TableColumn;
import com.googlecode.bdoc.doc.domain.TableRow;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author Per Otto Bergum Christensen
 */
public class XmlReport {

	private BDoc bddDoc;

	public XmlReport(BDoc bddDoc) {
		this.bddDoc = bddDoc;
	}

	public static BDoc cloneBDoc(BDoc bdoc) {
		BDoc createBDoc = XmlReport.createBDoc(new XmlReport(bdoc).xml());
		createBDoc.setDefaultTestAnnotations();
		return createBDoc;
	}

	public String xml() {
		return xStream().toXML(bddDoc);
	}

	private static XStream xStream() {
		XStream xstream = new XStream(new DomDriver());

		addAlias(xstream);
		return xstream;
	}

	public static void addAlias(XStream xstream) {
		xstream.alias("bddDoc", BDoc.class);
		xstream.alias("userStory", UserStory.class);
		xstream.alias("scenario", Scenario.class);
		xstream.alias("classBehaviour", ClassBehaviour.class);
		xstream.alias("codeBehaviour", ModuleBehaviour.class);
		xstream.alias("package", Package.class);
		xstream.alias("specification", Specification.class);
		xstream.alias("statement", Statement.class);
		xstream.alias("part", Scenario.Part.class);
		xstream.alias("testTable", TestTable.class);
		xstream.alias("row", TableRow.class);
		xstream.alias("column", TableColumn.class);
		xstream.addImplicitCollection(Package.class, "classBehaviourList");
	}

	public static BDoc createBDoc(String xml) {
		try {
			return (BDoc) xStream().fromXML(xml);
		} catch (ConversionException e) {
			throw new BDocRequiresResetException(e);
		}
	}
}
