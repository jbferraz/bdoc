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

package integrationtestclasses.bankaccount;

/**
 *  @author Per Otto Bergum Christensen
 */
public class BankAccount {
	private int balance;

	public BankAccount(int initialBalance) {
		this.balance = initialBalance;
	}

	public int withdraw(int amount) {
		if (amount < 0) {
			throw new IllegalStateException("amount cannot be negative");
		}
		int newBalance = balance - amount;
		if (newBalance < 0) {
			throw new IllegalStateException("Account cannot be overdrawn");
		}
		balance = newBalance;
		return amount;
	}

	public int balance() {
		return balance;
	}

	public void deposit(int amount) {
		if (amount < 0) {
			throw new IllegalStateException("amount cannot be negative");
		}
		balance = balance + amount;
	}

}
