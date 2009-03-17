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

package com.googlecode.bdoc.doc.dynamic.testdata;

import org.junit.Test;

/**
 * Example should give the following doc:
 * 
 * <code>
 * 
 * Given my account has an initial balance of 200 
 * When I ask to withdraw 20
 * Then I should be given 20 and the new balance should be 180
 * 
 * </code>
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
public class AccountBehaviour {

	@Test
	public void withdraw() {
		givenMyAccountHasAnInitialBalanceOf(200);
		whenIAskToWithdraw(20);
		thenIShouldBeGiven(20);
		andTheNewBalanceShouldBe(180);
	}

	void givenMyAccountHasAnInitialBalanceOf(int initialBalance) {
	}

	void whenIAskToWithdraw(int amount) {
	}

	void thenIShouldBeGiven(int givenAmount) {
	}

	void andTheNewBalanceShouldBe(int newBalance) {
	}

	@Test
	public void create() {
		givenAnAccountNameAndInitalBalance("MyAccount", 200);
	}

	void givenAnAccountNameAndInitalBalance(String accountName, int initialBalance) {
	}

	public void shouldThrowException() {
		throw new RuntimeException("don't crash because of this");
	}

	@Test
	public void shouldJustBeASimpleSpecification() {
	}

	@Test
	public void plainScenario() {
		given();
		when();
		then();
	}

	void given() {
	}

	void when() {
	}

	void then() {
	}

	@Test
	public void scenarioWithArguments() {
		given(1);
		when(2, 3);
		then(4, 5, 6);
	}

	void given(int i) {
	}

	void when(int i, int j) {
	}

	void then(int i, int j, int k) {
	}

	@Test
	public void norwegianScenario() {
		gitt(1);
		naar(2, 3);
		saa(4, 5, 6);
	}

	void gitt(int i) {
	}

	void naar(int i, int j) {
	}

	void saa(int i, int j, int k) {
	}

	@Test
	public void emptyTest() {
	}

	@Test
	public void shouldContainATestTable() {
		assertSum( 1,1,2 );
		assertSum( 2,2,2 );
		assertSum( 2,0,2 );
	}

	void assertSum(int i, int j, int k) {
	}
}
