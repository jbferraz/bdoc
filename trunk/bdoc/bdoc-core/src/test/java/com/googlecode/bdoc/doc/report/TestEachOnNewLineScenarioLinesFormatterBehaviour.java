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

import static org.apache.commons.io.FileUtils.writeStringToFile;
import static org.junit.Assert.assertFalse;
import integrationtestclasses.stack.StackBehavior;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestEachOnNewLineScenarioLinesFormatterBehaviour {

	@Test
	public void shouldBePossibleToChangeFormattingForAdvancedScenarioSpecification() throws IOException {
		BDoc bdoc = BDocTestHelper.bdocWithProject();
		bdoc.addBehaviourFrom(new TestClass(StackBehavior.class), BConst.SRC_TEST_JAVA);

		String htmlAndInBetween = new ModuleBehaviourReport(bdoc, bdocConfig(new AndInBetweenScenarioLinesFormatter())).html();
		String htmlEachOnNewLine = new ModuleBehaviourReport(bdoc, bdocConfig(new EachOnNewLineScenarioLinesFormatter())).html();

		writeStringToFile(new File("target/" + getClass().getName() + ".htmlAndInBetween.html"), htmlAndInBetween);
		writeStringToFile(new File("target/" + getClass().getName() + ".htmlEachOnNewLine.html"), htmlEachOnNewLine);

		assertFalse(htmlAndInBetween.equals(htmlEachOnNewLine));
	}

	private BDocConfig bdocConfig(ScenarioLinesFormatter scenarioLinesFormatter) {
		BDocConfig bdocConfig = new BDocConfig();
		bdocConfig.setScenarioLinesFormatter(scenarioLinesFormatter);
		return bdocConfig;
	}
}
