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

package integrationtestclasses.bankaccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class BankAccountBehavior {

	private BankAccount account;

	@Before
	public void setUp() {
		account = null;
	}

	@Test
	public void balanceOfANewlyCreatedAccountMustBeInitialBalance() {
		givenAnAccountWithInitialBalance(0);
		thenShouldBalanceEqualsTo(0);
	}

	@Test
	public void shouldAddDepositToBalance() {
		givenAnAccountWithInitialBalance(0);
		whenDepositAreCalledWithAmount(100);
		thenShouldBalanceEqualsTo(100);

		givenAnAccountWithInitialBalance(100);
		whenDepositAreCalledWithAmount(100);
		thenShouldBalanceEqualsTo(200);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldNotAcceptNegativeDepositeAmount() {
		givenAnAccountWithInitialBalance(0);
		whenDepositAreCalledWithAmount(-100);
		thenShouldAnExceptionBeThrown();
	}

	@Test
	public void shouldWithdrawAmountFromBalance() {
		givenAnAccountWithInitialBalance(100);
		whenWithdrawAreCalledWithAmount(20);
		thenShouldBalanceEqualsTo(80);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldNotAcceptNegativeWithdrawAmount() {
		givenAnAccountWithInitialBalance(0);
		whenWithdrawAreCalledWithAmount(-100);
		thenShouldAnExceptionBeThrown();
	}

	@Test(expected = IllegalStateException.class)
	public void shouldNotBeOverdrawn() {
		givenAnAccountWithInitialBalance(20);
		whenWithdrawAreCalledWithAmount(21);
		thenShouldAnExceptionBeThrown();
	}

	@Test
	public void shouldNotAffectBalanceIfAttemptToWithdrawOverBalance() {
		givenAnAccountWithInitialBalance(20);
		try {
			whenWithdrawAreCalledWithAmount(21);
			thenShouldAnExceptionBeThrown();
		} catch (IllegalStateException e) {
			thenShouldBalanceEqualsTo(20);
		}
	}

	@Test
	public void shouldNotAffectBalanceIfUncoveredWithdrawal() {
		givenAnAccountWithInitialBalance(0);
		try {
			whenDepositAreCalledWithAmount(-100);
			thenShouldAnExceptionBeThrown();
		} catch (IllegalStateException e) {
			thenShouldBalanceEqualsTo(0);
		}
	}

	private void thenShouldAnExceptionBeThrown() {
		fail("method hasn't caused an exception when it should");
	}

	private void whenWithdrawAreCalledWithAmount(int amount) {
		account.withdraw(amount);
	}

	private void thenShouldBalanceEqualsTo(int balance) {
		assertEquals(balance, account.balance());
	}

	private void whenDepositAreCalledWithAmount(int amount) {
		account.deposit(amount);
	}

	private void givenAnAccountWithInitialBalance(int initialBalance) {
		account = new BankAccount(initialBalance);
	}
}
