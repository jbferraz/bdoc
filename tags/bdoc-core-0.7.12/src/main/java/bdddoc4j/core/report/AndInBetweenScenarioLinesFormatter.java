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

import java.util.ArrayList;
import java.util.List;

import bdddoc4j.core.domain.Scenario;
import bdddoc4j.core.domain.Scenario.Part;
import bdddoc4j.core.domain.Scenario.Pattern;
import bdddoc4j.core.util.CamelCaseToSentenceTranslator;

public class AndInBetweenScenarioLinesFormatter implements ScenarioLinesFormatter {

	public List<String> getLines(Scenario scenario) {
		List<String> lines = new ArrayList<String>();
		List<Part> parts = scenario.getParts();

		Pattern pattern = Pattern.find(parts.get(0).camelCaseDescription());

		int index = 0;
		String tempLine = "";
		while (index < parts.size()) {

			String partCamelCaseDescription = parts.get(index).camelCaseDescription();

			if (0 < index) {
				if (lineHasSameKeywordAsThePreviouslyOne(parts, index)) {
					tempLine += (" " + pattern.and() + " " + partCamelCaseDescription);
				} else {
					lines.add(CamelCaseToSentenceTranslator.translate(tempLine, pattern.locale()));
					tempLine = partCamelCaseDescription;
				}
			} else {
				tempLine = partCamelCaseDescription;
			}

			if (index + 1 == parts.size()) {
				lines.add(CamelCaseToSentenceTranslator.translate(tempLine, pattern.locale()));
			}

			index++;
		}

		return lines;
	}

	private boolean lineHasSameKeywordAsThePreviouslyOne(List<Part> parts, int index) {
		return parts.get(index).scenarioKeyword() == parts.get(index - 1).scenarioKeyword();
	}
}
