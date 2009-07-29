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

package com.googlecode.bdoc;

import java.util.Locale;

import org.apache.commons.lang.Validate;

import com.googlecode.bdoc.doc.report.AndInBetweenScenarioLinesFormatter;
import com.googlecode.bdoc.doc.report.ScenarioLinesFormatter;

/**
 * Configuration for BDoc.
 * 
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
public class BDocConfig {

	private Locale locale = Locale.ENGLISH;
	private ScenarioLinesFormatter scenarioLinesFormatter = new AndInBetweenScenarioLinesFormatter();

	
	public BDocConfig() {
	}

	public BDocConfig(Locale locale, ScenarioLinesFormatter scenarioLinesFormatter) {
		Validate.notNull(locale, "locale");
		Validate.notNull(scenarioLinesFormatter);
		this.locale = locale;
		this.scenarioLinesFormatter = scenarioLinesFormatter;
	}


	public ScenarioLinesFormatter getScenarioLinesFormatter() {
		return scenarioLinesFormatter;
	}

	public void setScenarioLinesFormatter(ScenarioLinesFormatter scenarioLinesFormatter) {
		this.scenarioLinesFormatter = scenarioLinesFormatter;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
