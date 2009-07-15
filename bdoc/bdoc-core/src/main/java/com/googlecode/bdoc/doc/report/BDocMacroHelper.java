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

import java.util.List;
import java.util.ResourceBundle;

import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TableColumn;
import com.googlecode.bdoc.utils.CamelCaseToSentenceTranslator;

/**
 * @author Per Otto Bergum Christensen
 */
public class BDocMacroHelper {

	private ScenarioLinesFormatter formatter;

	public BDocMacroHelper() {
		this.formatter = new AndInBetweenScenarioLinesFormatter();
	}

	public BDocMacroHelper(ScenarioLinesFormatter formatter) {
		this.formatter = formatter;
	}

	ResourceBundle bundle;
	{
		bundle = ResourceBundle.getBundle("com.googlecode.bdoc.text");
	}

	public String text(String key) {
		return bundle.getString(key);
	}

	public String format(Statement statement) {
		return CamelCaseToSentenceTranslator.translate(statement.getSentence());
	}

	public String formatHeaderColumn(TableColumn column) {
		return CamelCaseToSentenceTranslator.translate(String.valueOf(column.getValue()));
	}

	public String scenarioLines(Scenario scenario) {
		List<String> lines = formatter.getLines(scenario);
		StringBuilder scenarioHtmlSnippet = new StringBuilder();
		for (String line : lines) {
			scenarioHtmlSnippet.append("<li>");
			scenarioHtmlSnippet.append(line);
			scenarioHtmlSnippet.append("</li>");
		}
		return scenarioHtmlSnippet.toString();
	}

	public String format(TableColumn tableColumn) {
		return tableColumn.getValue();
	}

	public String format(String string) {
		return CamelCaseToSentenceTranslator.translate(string);
	}
}
