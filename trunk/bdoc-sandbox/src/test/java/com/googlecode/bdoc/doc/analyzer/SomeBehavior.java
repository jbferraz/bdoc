package com.googlecode.bdoc.doc.analyzer;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;

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

	private void thenShouldBalanceEqualsTo(int i) {
	}

	private void whenDepositAreCalledWithAmount(int i) {
	}

	private void givenAnAccountWithInitialBalance(int i) {
	}
}
