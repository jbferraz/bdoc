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

import bdddoc4j.core.domain.BddDoc;
import bdddoc4j.core.domain.ClassBehaviour;
import bdddoc4j.core.domain.GeneralBehaviour;
import bdddoc4j.core.domain.Package;
import bdddoc4j.core.domain.Scenario;
import bdddoc4j.core.domain.Specification;
import bdddoc4j.core.domain.Statement;
import bdddoc4j.core.domain.UserStory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlReport {

	private BddDoc bddDoc;

	public XmlReport(BddDoc bddDoc) {
		this.bddDoc = bddDoc;
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
		xstream.alias("bddDoc", BddDoc.class);
		xstream.alias("userStory", UserStory.class);
		xstream.alias("scenario", Scenario.class);
		xstream.alias("classBehaviour", ClassBehaviour.class);
		xstream.alias("codeBehaviour", GeneralBehaviour.class);
		xstream.alias("package", Package.class);
		xstream.alias("specification", Specification.class);
		xstream.alias("statement", Statement.class);
		xstream.alias("line", String.class);
		xstream.addImplicitCollection(Package.class, "classBehaviourList");
		xstream.addImplicitCollection(Scenario.class, "line");
	}

	public static BddDoc createBddDoc(String xml) {
		return (BddDoc) xStream().fromXML(xml);
	}
}
