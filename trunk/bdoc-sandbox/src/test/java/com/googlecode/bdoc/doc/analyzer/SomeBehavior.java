package com.googlecode.bdoc.doc.analyzer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author Micael Vesterlund
 * 
 */
public class SomeBehavior {

	@Test
	public final void shouldFindLocale() {
		Locale locale = Locale.getDefault();
		assertNotNull(locale);
	}

	@Test
	public void shouldAddDepositToBalance() {
		givenAnAccountWithInitialBalance(0);
		whenDepositAreCalledWithAmount(100);
		thenShouldBalanceEqualsTo(100);
	}

	public void noTest() {
	}

	@Test
	@Ignore
	public void thisMethodAreIgnored() {
	}

	@Test
	public void eksemplerPaaBensjonsBeregning() {
		int G = 1;
		assertEquals(3, pensjonsPoengForInntektPaa(4 * G));
		assertEquals(7, pensjonsPoengForInntektPaa(12 * G));
	}

	// =>
	// Eksempler på pensjonsberegning
	// 3 pensjonspoeng for inntekt på 4*G
	// 7 pensjonspoeng for inntekt på 12*G

	private int pensjonsPoengForInntektPaa(int g) {
		if (g == 4) {
			return 3;
		}
		return 7;
	}

	private void thenShouldBalanceEqualsTo(int i) {
	}

	private void whenDepositAreCalledWithAmount(int i) {
	}

	private void givenAnAccountWithInitialBalance(int i) {
	}
}
