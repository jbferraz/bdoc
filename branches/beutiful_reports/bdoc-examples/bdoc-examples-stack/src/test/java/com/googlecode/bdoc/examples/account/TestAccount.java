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

package com.googlecode.bdoc.examples.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bdddoc4j.examples.Ref;
import bdddoc4j.examples.Story;

/**
 *  @author Per Otto Bergum Christensen
 */
@Ref(Story.DEPOSIT_AND_WITHDRAW_FUNDS)
public class TestAccount {

	@Test
	public void givenIHave200$InMyAccountWhenIAskToWithdraw20$ThenIShouldBeGiven20$AndMyBalanceShouldBe180$() {
		Account account = new Account(200D);
		double cashGiven = account.withdraw(20D);
		assertEquals(20D, cashGiven,0.01);
		assertEquals(180D, account.balance(),0.01);
	}

	@Test
	public void shouldAddDepositToBalance() {
		Account account = new Account(0D);
		account.deposit(100D);
		assertEquals(100D, account.balance(),.01);
	}

	@Test
	public void shouldWithdrawAmoutFromBalance() {
		Account account = new Account(100D);
		account.withdraw(20D);
		assertEquals(80, account.balance(),.01);

	}

	@Test(expected=IllegalStateException.class)
	public void shouldNotBeOverdrawn() {
		Account account = new Account(20D);
		account.withdraw(21D);
	}

	// public void testBalance() {
	// new Account(0).balance();
	// }
	// public void testWithdraw() {
	// new Account(0).withdraw(0);
	//		
	// }
	// public void testDeposit() {
	// new Account(0).deposit(0);
	// }

//	 @Test
//	 public void testCase1() {
//	
//	 }
//	 @Test
//	 public void testCase2() {
//	
//	 }
//	 @Test
//	 public void testCase3() {
//	
//	 }

}
