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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.Scenario;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestBDocMacroHelperBehaviour {

	@Test
	public void defaultTextLanguageShouldBeEnglish() {
		assertEquals("New user stories", new BDocMacroHelper(new BDocConfig()).text("new.user.stories"));
	}

	@Test
	public void norwegianLocaleShouldBeSupported() {
		BDocConfig bdocConfig = new BDocConfig();
		bdocConfig.setLocale(new Locale("NO"));
		assertEquals("Nye brukerhistorier", new BDocMacroHelper(bdocConfig).text("new.user.stories"));
	}

	@Test
	public void swedishLocaleShouldBeSupported() {
		BDocConfig bdocConfig = new BDocConfig();
		bdocConfig.setLocale(new Locale("SV"));
		assertEquals("Nya användarberättelser", new BDocMacroHelper(bdocConfig).text("new.user.stories"));
	}

	@Test
	public void shouldTransformNorwegianTwoLetterCodeToNorwegianSpecialCharacterWhenFormattingScenarios() {
		String scenarioLines = new BDocMacroHelper(new BDocConfig()).scenarioLines(new Scenario("gittAtOevreGrenseErNaarSaa"));
		assertFalse(scenarioLines.contains("oe"));
		assertTrue(scenarioLines.contains("�"));
	}
}
