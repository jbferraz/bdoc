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

import static org.junit.Assert.assertEquals;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Test;

public class TestSynchronizedTextKeys {

	private static final String COM_GOOGLECODE_BDOC_TEXT = "com.googlecode.bdoc.text";
	private ResourceBundle enText = ResourceBundle.getBundle(COM_GOOGLECODE_BDOC_TEXT, Locale.ENGLISH);
	private ResourceBundle noText = ResourceBundle.getBundle(COM_GOOGLECODE_BDOC_TEXT, new Locale("no"));
	private ResourceBundle svText = ResourceBundle.getBundle(COM_GOOGLECODE_BDOC_TEXT, new Locale("sv"));

	@Test
	public void englishTextPropertyFileShouldContainKeyForTocUserstories() {
		assertEquals("User stories", enText.getString("toc.userstories"));
	}

	@Test
	public void norwegianTextPropertyFileShouldContainKeyForTocUserstories() {
		assertEquals("Brukerhistorier", noText.getString("toc.userstories"));
	}

	@Test
	public void swedishTextPropertyFileShouldContainKeyForTocUserstories() {
		assertEquals("Användarberättelse", svText.getString("toc.userstories"));
	}

	@Test
	public void norwegianTextPropertyFileShouldBeInSyncWithTheEnglishVersion() {
		assertResourceBundleEquals(Locale.ENGLISH, new Locale("no"));
	}

	@Test
	public void swedishTextPropertyFileShouldBeInSyncWithTheEnglishVersion() {
		assertResourceBundleEquals(Locale.ENGLISH, new Locale("sv"));
	}

	private void assertResourceBundleEquals(Locale locale1, Locale locale2) {
		checkThatKeysForLocale1IsFoundForLocale2(locale1, locale2);
		checkThatKeysForLocale1IsFoundForLocale2(locale2, locale1);
	}

	private void checkThatKeysForLocale1IsFoundForLocale2(Locale locale1, Locale locale2) throws AssertionError {
		ResourceBundle text1 = ResourceBundle.getBundle(COM_GOOGLECODE_BDOC_TEXT, locale1);
		ResourceBundle text2 = ResourceBundle.getBundle(COM_GOOGLECODE_BDOC_TEXT, locale2);

		Enumeration<String> keys1 = text1.getKeys();

		while (keys1.hasMoreElements()) {
			String key = keys1.nextElement().toString();

			try {
				text2.getString(key);
			} catch (MissingResourceException e) {
				throw new AssertionError("Expected to find key [" + key + "] for locale [" + locale2 + "]");
			}

		}
	}
}
