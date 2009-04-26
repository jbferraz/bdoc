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

import java.util.ArrayList;
import java.util.List;

import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Scenario.Part;
import com.googlecode.bdoc.doc.domain.Scenario.Pattern;
import com.googlecode.bdoc.utils.CamelCaseToSentenceTranslator;

/**
 * @author Micael Vesterlund 
 */
public class EachOnNewLineScenarioLinesFormatter implements ScenarioLinesFormatter {

	public List<String> getLines(Scenario scenario) {
		List<String> lines = new ArrayList<String>();
		List<Part> parts = scenario.getParts();

		Pattern pattern = Pattern.find(parts.get(0).camelCaseDescription());

		for (Part part : parts) {

			String partCamelCaseDescription = part.camelCaseDescription();

			lines.add(CamelCaseToSentenceTranslator.translate(partCamelCaseDescription, pattern.locale()));
		}

		return lines;
	}
}
