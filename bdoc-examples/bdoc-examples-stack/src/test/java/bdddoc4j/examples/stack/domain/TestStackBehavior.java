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

package bdddoc4j.examples.stack.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import bdddoc4j.examples.Ref;
import bdddoc4j.examples.Story;

/**
 * This class was written as a suggestion to how BDoc could be extended, to
 * support a different way of structure the Javacode, when specifying a
 * scenario. It is now used as testdata by
 * bdddoc4j.core.domain.TestSourceClassBehaviourParser.java , as a specification
 * for BDoc on the user story "advanced scenario specification "
 * 
 * @Author Micael Vesterlund, micael.vesterlund@gmail.com
 */
@Ref(Story.STACK_DESCRIBED_WITH_METHODS)
public class TestStackBehavior {

	private Stack<String> stack;

	private static final String VALUE_1 = "1";
	private static final String VALUE_2 = "2";

	private String poped;
	private String lastPushed;
	private String peeked;
	private int sizeBefore;
	private int sizeAfter;

	@Test
	public void aNewlyCreatedStackMustBeEmpty() {
		givenAnEmptyStack();
		thenEnsureThatTheStackAreEmpty();
	}

	@Test
	public void shouldNotBeEmptyAfterPush() {
		givenAnEmptyStack();
		whenPushedIsCalled(VALUE_1);
		thenEnsureThatTheStackAreNotEmpty();
	}

	@Test(expected = Exception.class)
	public void shouldThrowExceptionUponNullPush() {
		givenAnEmptyStack();
		whenPushedIsCalledWithNull();
		thenEnsureThatAnExceptionAreThrown();
	}

	@Test(expected = Exception.class)
	public void shouldThrowExceptionUponPopWithoutPush() {
		givenAnEmptyStack();
		whenPopIsCalled();
		thenEnsureThatAnExceptionAreThrown();
	}

	@Test
	public void shouldPopPushedValue() {
		givenAnEmptyStack();
		whenPushedIsCalled(VALUE_1);
		whenPopIsCalled();
		thenEnsureThatLastItemPushedAreReturnedFromPop();
		thenEnsureThatTheStackAreEmpty();
	}

	@Test
	public void shouldPopLastPushedValueFirst() {
		givenAnEmptyStack();
		whenPushedIsCalled(VALUE_1);
		whenPushedIsCalled(VALUE_2);
		whenPopIsCalled();
		thenEnsureThatLastItemPushedAreReturnedFromPop();
		thenEnsureThatTheValueNotRemainsInTheStack();
		thenEnsureThatTheStackAreNotEmpty();
	}

	@Test
	public void shouldLeaveValueOnStackAfterPeep() {
		givenAnEmptyStack();
		whenPushedIsCalled(VALUE_1);
		whenPeekIsCalled();
		thenEnsureThatLastItemPushedAreReturnedFromPeek();
		thenEnsureThatTheValueRemainsInTheStack();
	}

	@Before
	public void setUp() {
		poped = null;
		lastPushed = null;
		peeked = null;
		sizeBefore = 0;
		sizeAfter = 0;
	}

	// GIVENS
	private void givenAnEmptyStack() {
		stack = new Stack<String>();
	}

	// WHENS
	private void whenPushedIsCalled(String item) {
		sizeBefore = stack.size();
		stack.push(item);
		sizeAfter = stack.size();
		lastPushed = item;
	}

	private void whenPushedIsCalledWithNull() {
		whenPushedIsCalled(null);
	}

	private void whenPopIsCalled() {
		sizeBefore = stack.size();
		poped = stack.pop();
		sizeAfter = stack.size();
	}

	private void whenPeekIsCalled() {
		sizeBefore = stack.size();
		peeked = stack.peek();
		sizeAfter = stack.size();
	}

	// THENS
	private void thenEnsureThatAnExceptionAreThrown() {
		fail("method hasn't caused an exception when it should");
	}

	private void thenEnsureThatLastItemPushedAreReturnedFromPop() {
		assertThat(lastPushed, equalTo(poped));
	}

	private void thenEnsureThatLastItemPushedAreReturnedFromPeek() {
		assertThat(lastPushed, equalTo(peeked));
	}

	private void thenEnsureThatTheValueRemainsInTheStack() {
		assertThat(sizeAfter, equalTo(sizeBefore));
	}

	private void thenEnsureThatTheValueNotRemainsInTheStack() {
		assertThat(sizeAfter, equalTo(sizeBefore - 1));
	}

	private void thenEnsureThatTheStackAreEmpty() {
		if (!stack.isEmpty()) {
			throw new AssertionError();
		}
	}

	private void thenEnsureThatTheStackAreNotEmpty() {
		if (stack.isEmpty()) {
			throw new AssertionError();
		}
	}

	@Ignore
	@Test
	public void shouldIgnoreMe() {
		Assert.assertTrue(true);
	}
}
