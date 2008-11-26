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
}
