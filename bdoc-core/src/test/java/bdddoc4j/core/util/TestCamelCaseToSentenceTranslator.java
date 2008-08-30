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

package bdddoc4j.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bdddoc4j.core.util.CamelCaseToSentenceTranslator;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestCamelCaseToSentenceTranslator {

	@Test
	public void shouldAddASpaceInFrontOfANumber() {
		assertEquals("Should be 200$", CamelCaseToSentenceTranslator.translate("shouldBe200$"));
		assertEquals("Should be 300$", CamelCaseToSentenceTranslator.translate("shouldBe300$"));
	}

	@Test
	public void shouldNotAddASpaceInBetweenNumbers() {
		assertEquals("Should be 555$", CamelCaseToSentenceTranslator.translate("shouldBe555$"));
	}

	@Test
	public void shouldAddASpaceInAfterNumbers() {
		assertEquals("Should be 799 horses", CamelCaseToSentenceTranslator.translate("shouldBe799Horses"));
		assertEquals("Should be 799 horses and 10 pigs", CamelCaseToSentenceTranslator.translate("shouldBe799HorsesAnd10Pigs"));
	}

	@Test
	public void shouldChangeTheFirstCharacterToUpperCase() {
		String result = CamelCaseToSentenceTranslator.translate("a");
		assertEquals('A', result.charAt(0));
	}

	@Test
	public void shouldInsertSpaceBeforeEachCharInUppercase() {
		String result1 = CamelCaseToSentenceTranslator.translate("aB");
		assertEquals(3, result1.length());
		assertEquals(' ', result1.charAt(1));
	}

	@Test
	public void shouldChangeEachCharInUppercaseToLowerCase() {
		String result = CamelCaseToSentenceTranslator.translate("aB");
		assertEquals('b', result.charAt(2));
	}

	@Test
	public void givenACamelCasedDescriptionWhenTranslatedItShouldFormASentenceWithSpaceBetweenTheWords() {
		String sentence = CamelCaseToSentenceTranslator.translate("givenASentenceWhenTranslatedEnsureItIsCorrect");
		assertEquals("Given a sentence when translated ensure it is correct", sentence);
	}

	@Test
	public void shouldLeaveTheFirstCharUnmodifiedIfItIsInUpperCase() {
		assertEquals("Given", CamelCaseToSentenceTranslator.translate("Given"));
	}
}
