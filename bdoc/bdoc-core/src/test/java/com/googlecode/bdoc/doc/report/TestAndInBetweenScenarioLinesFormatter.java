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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Scenario.Part;

/**
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
public class TestAndInBetweenScenarioLinesFormatter {

	private AndInBetweenScenarioLinesFormatter formatter = new AndInBetweenScenarioLinesFormatter();

	@Test
	public void shouldCreateOneLinePerKeyworfEvenIfNotStartsWithGiven() {

		List<String> lines = formatter.getLines(new Scenario("whenThen"));

		assertTrue(lines.get(0).startsWith("When"));
		assertTrue(lines.get(1).startsWith("Then"));
	}

	@Test
	public void shouldTransformASimpleGivenWhenThenToThreeLinesWhereEachLineStartsWithUppercase() {

		List<String> lines = formatter.getLines(new Scenario("givenWhenThen"));

		assertTrue(lines.get(0).startsWith("Given"));
		assertTrue(lines.get(1).startsWith("When"));
		assertTrue(lines.get(2).startsWith("Then"));
	}

	@Test
	public void shouldTranslateTheLowerLetterIToUpcaseI() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Part("givenIHave"));

		List<String> lines = formatter.getLines(new Scenario(parts));

		assertEquals("Given I have", lines.get(0));
	}

	@Test
	public void shouldCreateOneLinePerKeyword() {
		List<Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("whenX"));
		parts.add(new Scenario.Part("whenY"));
		parts.add(new Scenario.Part("thenA"));
		parts.add(new Scenario.Part("thenB"));
		parts.add(new Scenario.Part("thenC"));
		List<String> lines = formatter.getLines(new Scenario(parts));

		assertEquals(3, lines.size());

		assertTrue(lines.get(0).startsWith("Given"));
		assertTrue(lines.get(1).startsWith("When"));
		assertTrue(lines.get(2).startsWith("Then"));
	}

	@Test
	public void shouldAddAndBetween2xGiven() {
		List<Part> parts = new ArrayList<Part>();
		parts.add(new Scenario.Part("givenA"));
		parts.add(new Scenario.Part("givenB"));
		parts.add(new Scenario.Part("when"));
		parts.add(new Scenario.Part("then"));

		assertEquals("Given a and given b", formatter.getLines(new Scenario(parts)).get(0));
	}

	@Test
	public void shouldAddAndBetween2xWhen() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("whenC"));
		parts.add(new Scenario.Part("whenD"));
		parts.add(new Scenario.Part("then"));

		assertEquals("When c and when d", formatter.getLines(new Scenario(parts)).get(1));
	}

	@Test
	public void shouldAddAndBetween2xThen() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("when"));
		parts.add(new Scenario.Part("thenA"));
		parts.add(new Scenario.Part("thenB"));

		assertEquals("Then a and then b", formatter.getLines(new Scenario(parts)).get(2));
	}

	@Test
	public void shouldSupportMultipleGivenWhenThen() {
		List<Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given1"));
		parts.add(new Scenario.Part("given1a"));
		parts.add(new Scenario.Part("when1"));
		parts.add(new Scenario.Part("then1"));
		parts.add(new Scenario.Part("then1a"));

		parts.add(new Scenario.Part("given2"));
		parts.add(new Scenario.Part("given2a"));
		parts.add(new Scenario.Part("when2"));
		parts.add(new Scenario.Part("then2"));
		parts.add(new Scenario.Part("then2a"));
		List<String> lines = formatter.getLines(new Scenario(parts));

		assertEquals(6, lines.size());

		assertTrue(lines.get(0).startsWith("Given"));
		assertTrue(lines.get(1).startsWith("When"));
		assertTrue(lines.get(2).startsWith("Then"));

		assertTrue(lines.get(3).startsWith("Given"));
		assertTrue(lines.get(4).startsWith("When"));
		assertTrue(lines.get(5).startsWith("Then"));
	}
}
