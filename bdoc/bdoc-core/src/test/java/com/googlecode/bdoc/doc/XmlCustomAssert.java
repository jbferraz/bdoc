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

package com.googlecode.bdoc.doc;

import org.apache.commons.lang.Validate;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;

import com.googlecode.bdoc.doc.util.SysProp;

/**
 *  @author Per Otto Bergum Christensen
 */
public class XmlCustomAssert {

	private XmlCustomAssert() {

	}
	
	public static void assertXPathContains(String expected, String xpath, String xml) {
		Validate.notNull(expected, "expected");
		Validate.notNull(xpath, "xpath");
		Validate.notNull(xml, "xml");
		
		String result = null;
		try {
			result = XMLUnit.newXpathEngine().evaluate(xpath, XMLUnit.buildControlDocument(xml));
			if (result.contains(expected)) {
				return;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		throw new AssertionError("XPath [" + xpath + "] gives [" + result + "] which was expected to contain [" + expected + "]");
	}	

	public static void assertXpathEvaluatesTo(String expectedValue, String xpath, String xml) {
		try {
			XMLAssert.assertXpathEvaluatesTo(expectedValue, xpath, xml);
		} catch (AssertionError e) {
			throw new AssertionError(e.getMessage() + " for xpath \"" + xpath + "\" and XML " + SysProp.NL + xml);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
