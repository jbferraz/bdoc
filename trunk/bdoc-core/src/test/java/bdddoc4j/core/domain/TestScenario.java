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

package bdddoc4j.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestScenario {

	@Test
	public void shouldAcceptSentenceAsAScenarioInBothNorwegianAndEnglish() {
		new Scenario("givenWhenThen");
		new Scenario("gittNaarSaa");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAcceptASentenceAsAScenarioIfItDoesNotTheMatchTheScenarioPattern() {
		new Scenario("asdf");
	}

	@Test
	public void shouldTranslateCamelCaseSentenceToGivenWhenThen() {
		Scenario scenario = new Scenario("givenWhenThen");
		assertEquals("Given", scenario.getLines().get(0));
		assertEquals("When", scenario.getLines().get(1));
		assertEquals("Then", scenario.getLines().get(2));
	}

	@Test
	public void shouldGiveTheStartIndexForScenarioKeywordGivenInNorwegianAndEnglish() {
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword(
				"012345givenA ", 0));
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345gittA ",
				0));
	}

	@Test
	public void shouldGiveTheStartIndexForScenarioKeywordwhenInNorwegianAndEnglish() {
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345whenA ",
				1));
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345naarA ",
				1));
	}

	@Test
	public void shouldGiveTheStartIndexForScenarioKeywordThenInNorwegianAndEnglish() {
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345thenA ",
				2));
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345saaA ",
				2));
	}

	@Test
	public void shouldConstructAScenarioFromGivenWhenThenAsSeperateParts() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("when"));
		parts.add(new Scenario.Part("then"));

		assertEquals(new Scenario("givenWhenThen"), new Scenario(parts));
	}

	@Test
	public void shouldAddAndBetween2xGiven() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("givenA"));
		parts.add(new Scenario.Part("givenB"));
		parts.add(new Scenario.Part("when"));
		parts.add(new Scenario.Part("then"));

		assertEquals(new Scenario("givenAAndGivenBWhenThen").getLines().get(0),
				new Scenario(parts).getLines().get(0));

	}

	@Test
	public void shouldAddAndBetween2xWhen() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("whenC"));
		parts.add(new Scenario.Part("whenD"));
		parts.add(new Scenario.Part("then"));

		assertEquals(new Scenario("givenWhenCAndWhenDThen").getLines().get(1),
				new Scenario(parts).getLines().get(1));
	}

	@Test
	public void shouldAddAndBetween2xThen() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("when"));
		parts.add(new Scenario.Part("thenA"));
		parts.add(new Scenario.Part("thenB"));

		assertEquals(new Scenario("givenWhenThenAAndThenB").getLines().get(2),
				new Scenario(parts).getLines().get(2));
	}

	@Test
	public void shouldTranslateTheLowerLetterIToUpcaseI() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("givenIHave"));

		assertEquals("Given I have", new Scenario(parts).getLines().get(0));
	}

	@Test
	public void shouldCreateOneLinePerKeyword() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("whenX"));
		parts.add(new Scenario.Part("whenY"));
		parts.add(new Scenario.Part("thenA"));
		parts.add(new Scenario.Part("thenB"));
		parts.add(new Scenario.Part("thenC"));
		List<String> lines = new Scenario(parts).getLines();

		assertEquals(3, lines.size());

		assertTrue(lines.get(0).startsWith("Given"));
		assertTrue(lines.get(1).startsWith("When"));
		assertTrue(lines.get(2).startsWith("Then"));
	}
}
