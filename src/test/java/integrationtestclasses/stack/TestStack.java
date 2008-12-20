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

package integrationtestclasses.stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestStack {

	private Stack<String> stack;

	@Before
	public void setup() {
		stack = new Stack<String>();
	}

	@Test
	public void givenAnEmptyStackWhenOneValueIsPushedThenTheStackShouldNotBeEmpty() {
		stack.push("1");
		assertFalse(stack.isEmpty());
	}
		
	@Test
	public void givenAStackWithOnePushedValueWhenPopIsCalledThenThatObjectShouldBeReturned() {
		stack.push("1");
		assertSame("1", stack.pop());
	}

	@Test
	public void givenAStackWithTwoValuesWhenPopIsCalledThenTheLastItemPushedShouldBeReturned() {
		stack.push("1");
		stack.push("2");
		assertSame("2", stack.pop());
	}

	@Test
	public void givenAStackWithTwoValuesWhenPopIsCalledThenThePreviousItemShouldBeOnTopOfTheStack() {
		stack.push("1");
		stack.push("2");
		stack.pop();
		assertSame("1", stack.peek());
	}
	
	@Test 
	public void givenAStackPushedWithTheSameValueTwiceWhenPopedThenOneValueShouldBeLeftOnTop()
	{
		stack.push("1");
		stack.push("1");
		stack.pop();
		assertSame("1", stack.peek());
		assertEquals( 1, stack.size());
	}
	
	@Test
	public void givenAStackContainingItemsWhenPeekIsCalledThenTheStackShouldProvideTheValueOfTheMostRecentPushedValueAndThatValueShouldRemainInTheStack() {
		stack.push("1");
		stack.push("2");
		assertSame("2", stack.peek());
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void givenAnEmptyStackWhenNullIsPushedThenAnExceptionShouldBeThrown() {
		stack.push(null);
	}

	@Test(expected=IllegalStateException.class)
	public void givenAaEmptyStackWhenPopIsCalledThenAnExceptionShouldBeThrown() {
		stack.pop();
	}
	
	@Test
	public void shouldBeEmptyWhenStackIsNew() {
		assertTrue(stack.isEmpty());
	}
	
}