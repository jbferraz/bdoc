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

package com.googlecode.bdoc.doc.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.doc.domain.Scenario.Pattern;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestScenario {

	@Test
	public void shouldSplitACamelCaseSentenceOnTheFormGivenWhenThenToOneUnformattedScenarioPartPerKeyword() {
		List<Scenario.Part> scenarioParts = new Scenario("givenStateAWhenActionBThenEnsureC").getParts();
		assertEquals(new Scenario.Part("givenStateA"), scenarioParts.get(0));
		assertEquals(new Scenario.Part("WhenActionB"), scenarioParts.get(1));
		assertEquals(new Scenario.Part("ThenEnsureC"), scenarioParts.get(2));
	}

	@Test
	public void shouldAcceptSentenceAsAScenarioInNorwegianAndEnglishAndSwedish() {
		new Scenario("givenWhenThen");
		new Scenario("gittNaarSaa");
		new Scenario("givetNaarSaa");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotAcceptASentenceAsAScenarioIfItDoesNotTheMatchTheScenarioPattern() {
		new Scenario("asdf");
	}

	@Test
	public void shouldTranslateCamelCaseSentenceToGivenWhenThen() {
		Scenario scenario = new Scenario("givenWhenThen");
		assertEquals("given", scenario.getParts().get(0).camelCaseDescription());
		assertEquals("When", scenario.getParts().get(1).camelCaseDescription());
		assertEquals("Then", scenario.getParts().get(2).camelCaseDescription());
	}

	@Test
	public void shouldGiveTheStartIndexForScenarioKeywordGivenInNorwegianAndEnglish() {
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345givenA ", 0));
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345gittA ", 0));
	}

	@Test
	public void shouldGiveTheStartIndexForScenarioKeywordwhenInNorwegianAndEnglish() {
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345whenA ", 1));
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345naarA ", 1));
	}

	@Test
	public void shouldGiveTheStartIndexForScenarioKeywordThenInNorwegianAndEnglish() {
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345thenA ", 2));
		assertEquals(6, Scenario.Pattern.indexOfScenarioKeyword("012345saaA ", 2));
	}

	@Test
	public void shouldConstructAScenarioFromGivenWhenThenAsSeperateParts() {
		List<Scenario.Part> parts = new ArrayList<Scenario.Part>();
		parts.add(new Scenario.Part("given"));
		parts.add(new Scenario.Part("When"));
		parts.add(new Scenario.Part("Then"));

		Scenario scenario = new Scenario(parts);
		assertEquals(new Scenario("givenWhenThen"), scenario);
		assertEquals(parts, scenario.getParts());
	}

	@Test
	public void shouldSupportScenariosInEnglishAndNorwegianAndSwedish() {
		assertNotNull(Pattern.valueOf("EN"));
		assertNotNull(Pattern.valueOf("NO"));
		assertNotNull(Pattern.valueOf("SV"));
	}
	
	@Test
	public void shouldFindTheCorrespondingLocaleGivenTheScenarioText() {
		assertEquals( Pattern.EN, Pattern.find("givenBlaBla.. .") );
		assertEquals( Pattern.NO, Pattern.find("gittBlaBla.. .") );
		assertEquals( Pattern.SV, Pattern.find("givetBlaBla.. .") );
	}
}
