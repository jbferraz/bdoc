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
public class SomeBehavior extends ScenarioSupport<SomeBehavior> {

	@Test
	public final void shouldFindLocale() {
		Locale locale = Locale.getDefault();
		assertNotNull(locale);
	}

	@Test
	public void shouldAddDepositToBalance() {
		given.anAccountWithInitialBalance(0);
		when.depositAreCalledWithAmount(100);
		then.balanceShouldEqualsTo(100);
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

	@Test
	public void shouldPopLastPushedValueFirst() {
		given.aNewlyCreatedStack();
		and.pushedIsCalledWith("foo");
		and.pushedIsCalledWith("bar");
		when.popIsCalled();
		then.popedValueIs("bar");
		and.sizeOfStackHasDecreasedWith(1);
		and.stackIsNotEmpty();
	}

	@Test
	public void twoScenarios() {
		given.anAccountWithInitialBalance(0);
		when.depositAreCalledWithAmount(100);
		then.balanceShouldEqualsTo(100);

		given.anAccountWithInitialBalance(1);
		when.depositAreCalledWithAmount(100);
		then.balanceShouldEqualsTo(101);
	}

	// =>
	// Eksempler på pensjonsberegning
	// 3 pensjonspoeng for inntekt på 4*G
	// 7 pensjonspoeng for inntekt på 12*G

	private void stackIsNotEmpty() {
		// TODO Auto-generated method stub

	}

	private void sizeOfStackHasDecreasedWith(int i) {
		// TODO Auto-generated method stub

	}

	private void popedValueIs(String string) {
		// TODO Auto-generated method stub

	}

	private void popIsCalled() {
		// TODO Auto-generated method stub

	}

	private void pushedIsCalledWith(String string) {
		// TODO Auto-generated method stub

	}

	private void aNewlyCreatedStack() {
		// TODO Auto-generated method stub

	}

	private int pensjonsPoengForInntektPaa(int g) {
		if (g == 4) {
			return 3;
		}
		return 7;
	}

	private void balanceShouldEqualsTo(int i) {
	}

	private void depositAreCalledWithAmount(int i) {
	}

	private void anAccountWithInitialBalance(int i) {
	}
}
