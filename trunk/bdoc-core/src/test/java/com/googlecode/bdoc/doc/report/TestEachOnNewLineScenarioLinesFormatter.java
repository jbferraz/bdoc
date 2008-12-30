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

package com.googlecode.bdoc.doc.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Scenario.Part;
import com.googlecode.bdoc.doc.report.EachOnNewLineScenarioLinesFormatter;


public class TestEachOnNewLineScenarioLinesFormatter {

	private EachOnNewLineScenarioLinesFormatter formatter = new EachOnNewLineScenarioLinesFormatter();

	@Test
	public void shouldCreateOneLinePerScenarioPart() {
		List<Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("whenX"));
		parts.add(new Scenario.Part("whenY"));
		parts.add(new Scenario.Part("thenA"));
		parts.add(new Scenario.Part("thenB"));
		parts.add(new Scenario.Part("thenC"));
		List<String> lines = formatter.getLines(new Scenario(parts));

		assertEquals(6, lines.size());

		assertTrue(lines.get(0).equals("Given"));
		assertTrue(lines.get(1).equals("When x"));
		assertTrue(lines.get(2).equals("When y"));
		assertTrue(lines.get(3).equals("Then a"));
		assertTrue(lines.get(4).equals("Then b"));
		assertTrue(lines.get(5).equals("Then c"));
	}
}
