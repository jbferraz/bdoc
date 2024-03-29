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

package com.googlecode.bdoc.utils;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
public class TestCamelCaseToSentenceTranslator {

	@Test
	public void shouldAddASpaceInFrontOfAMinusSign() {
		assertEquals("When deposit are called with amount -100", CamelCaseToSentenceTranslator
				.translate("whenDepositAreCalledWithAmount-100"));
	}

	@Test
	public void shouldNotAddSpaceAroundDecimalSeparator() {
		assertEquals("When deposit are called with amount 99.9", CamelCaseToSentenceTranslator
				.translate("whenDepositAreCalledWithAmount99.9"));
	}

	@Test
	public void shouldTranselateTheEnglishIToUperCase() {
		assertEquals("Given that I have this and I have that", CamelCaseToSentenceTranslator.translate("givenThatIHaveThisAndIHaveThat",
				Locale.ENGLISH));
	}

	@Test
	public void shouldNotTranselateTheEnglishIToUperCaseForNorwegian() {
		assertEquals("Gitt at jeg har 20 kroner i banken", CamelCaseToSentenceTranslator.translate("gittAtJegHar20KronerIBanken",
				new Locale("no")));
	}

	@Test
	public void shouldNotTranselateTheEnglishIToUperCaseForSwedish() {
		assertEquals("Givet att jag har 20 kroner i banken", CamelCaseToSentenceTranslator.translate("givetAttJagHar20KronerIBanken",
				new Locale("sv")));
	}

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

	@Test
	public void shouldReplaceUnderscoreWithSpace() {
		String sentence = CamelCaseToSentenceTranslator.translate("given_a_sentence");
		assertEquals("Given a sentence", sentence);
	}

	@Test
	public void shouldNotAddSpaceInFrontOfCapitalLetterIfUnderscoreIsUsedInFront() {
		String sentence = CamelCaseToSentenceTranslator.translate("given_A_Sentence");
		assertEquals("Given a sentence", sentence);
	}
	
	@Test
	public void shouldTransformNorwegianTwoLetterCodeToNorwegianSpecialCharacter() {
		exampleOnFormattingOfNorwegianText("naarASaaB", "Når a så b");
		exampleOnFormattingOfNorwegianText("skalVaere", "Skal være");
		exampleOnFormattingOfNorwegianText("skalAngiOevreGrense", "Skal angi øvre grense");
	}
	
	@Test
	public void shouldNotTransformNorwegianTwoLetterCodeToNorwegianSpecialCharacterWhenTwoLetterCodeIsANaturalPartOfTheWord() {
		exampleOnFormattingOfNorwegianText("poeng", "Poeng");
		exampleOnFormattingOfNorwegianText("samboer", "Samboer");
	}
	

	void exampleOnFormattingOfNorwegianText(String camelCase, String expectedText) {
		assertEquals(expectedText, CamelCaseToSentenceTranslator.translate(camelCase));
	}
	
}
