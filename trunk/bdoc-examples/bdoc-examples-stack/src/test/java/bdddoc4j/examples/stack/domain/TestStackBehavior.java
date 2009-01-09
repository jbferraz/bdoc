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

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import bdddoc4j.examples.Ref;
import bdddoc4j.examples.Story;

/**
 * This class was written as a suggestion to how BDoc could be extended, to
 * support a different way of structure the Javacode, when specifing a scenario.
 * It is now used as testdata by
 * bdddoc4j.core.domain.TestSourceClassBehaviourParser.java , as a specification
 * for BDoc on the user story "adcanced scenario specification "
 * 
 * @Author Micael Vesterlund, micael.vesterlund@gmail.com
 */
@Ref(Story.STACK_DESCRIBED_WITH_METHODS)
public class TestStackBehavior {

	private Stack<String> stack;

	private static final String VALUE_1 = "1";
	private static final String VALUE_2 = "2";

	// GIVENS
	private void givenAnEmptyStack() {
		stack = new Stack<String>();
	}

	private void givenAStackWithOnePushedValue(String value) {
		stack = new Stack<String>();
		stack.push(value);
	}

	private void givenAStackWithTwoPushedValues(String value1, String value2) {
		stack = new Stack<String>();
		stack.push(value1);
		stack.push(value2);
	}

	// WHENS
	private void whenNullIsPushed() {
		stack.push(null);
	}

	private String whenPopIsCalled() {
		return stack.pop();
	}

	private String whenPeekIsCalled() {
		return stack.peek();
	}

	// THENS
	private void thenAnExceptionShouldBeThrown() {
	}

	private void thenThatObjectShouldBeReturned(String pushed, String poped) {
		assertThat(pushed, equalTo(poped));
	}

	private void thenTheLastItemPushedShouldBeReturned(String pushed, String poped) {
		assertThat(pushed, equalTo(poped));
	}

	private void thenTheValueShouldRemainInTheStack(int sizeBefore, int sizeAfter) {
		assertThat(sizeAfter, equalTo(sizeBefore));
	}

	private void thenTheValueShouldNotRemainInTheStack(int sizeBefore, int sizeAfter) {
		assertThat(sizeAfter, equalTo(sizeBefore - 1));
	}

	private void thenShouldTheStackBeEmpty() {
		if (!stack.isEmpty()) {
			throw new AssertionError();
		}
	}

	private void thenShouldTheStackBeNotEmpty() {
		if (stack.isEmpty()) {
			throw new AssertionError();
		}
	}

	// TESTS
	@Test(expected = Exception.class)
	public void shouldThrowExceptionUponNullPush() {
		givenAnEmptyStack();
		whenNullIsPushed();
		thenAnExceptionShouldBeThrown();
	}

	@Test(expected = Exception.class)
	public void shouldThrowExceptionUponPopWithoutPush() {
		givenAnEmptyStack();
		whenPopIsCalled();
		thenAnExceptionShouldBeThrown();
	}

	@Test
	public void shouldPopPushedValue() {
		givenAStackWithOnePushedValue(VALUE_1);
		String poped = whenPopIsCalled();
		thenThatObjectShouldBeReturned(VALUE_1, poped);
		thenShouldTheStackBeEmpty();
	}

	@Test
	public void shouldPopLastPushedValueFirst() {
		givenAStackWithTwoPushedValues(VALUE_1, VALUE_2);
		int sizeBefore = stack.size();
		String poped = whenPopIsCalled();
		int sizeAfter = stack.size();
		thenTheLastItemPushedShouldBeReturned(VALUE_2, poped);
		thenTheValueShouldNotRemainInTheStack(sizeBefore, sizeAfter);
		thenShouldTheStackBeNotEmpty();
	}

	@Test
	public void shouldLeaveValueOnStackAfterPeep() {
		givenAStackWithOnePushedValue(VALUE_1);
		int sizeBefore = stack.size();
		String peeked = whenPeekIsCalled();
		int sizeAfter = stack.size();
		thenThatObjectShouldBeReturned(VALUE_1, peeked);
		thenTheValueShouldRemainInTheStack(sizeBefore, sizeAfter);
	}

	@Ignore
	@Test
	public void shouldIgnoreMe() {
		Assert.assertTrue(true);
	}
}
