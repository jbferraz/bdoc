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

package com.googlecode.bdoc.examples.account;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bdddoc4j.examples.Ref;
import bdddoc4j.examples.Story;

@Ref(Story.DEPOSIT_AND_WITHDRAW_FUNDS)
public class TestAccountBehavior {

	private Account account;

	@Before
	public void setUp() {
		account = null;
	}

	@Test
	public void shouldAddDepositToBalance() {
		givenAnAccountWithInitialBalanceEqualsTo0();
		when100AreDepositToAccount();
		thenShouldBalanceBeEqualTo100();
	}

	@Test
	public void advancedScenarios() {
		givenAnAccountWithInitialBalanceEqualsTo(0);
		whenDepositAreCalledWith(100);
		thenShouldBalanceBeEqualTo(100);

		givenAnAccountWithInitialBalanceEqualsTo(1);
		whenDepositAreCalledWith(101);
		thenShouldBalanceBeEqualTo(102);
	}

	private void thenShouldBalanceBeEqualTo(int balance) {
		assertEquals(balance, account.balance());
	}

	private void whenDepositAreCalledWith(int amount) {
		account.deposit(amount);
	}

	private void givenAnAccountWithInitialBalanceEqualsTo(int initialBalance) {
		account = new Account(initialBalance);
	}

	private void givenAnAccountWithInitialBalanceEqualsTo0() {
		givenAnAccountWithInitialBalanceEqualsTo(0);
	}

	private void when100AreDepositToAccount() {
		whenDepositAreCalledWith(100);
	}

	private void thenShouldBalanceBeEqualTo100() {
		thenShouldBalanceBeEqualTo(100);
	}

}
