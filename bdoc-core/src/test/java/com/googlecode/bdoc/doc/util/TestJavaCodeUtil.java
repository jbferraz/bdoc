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

package com.googlecode.bdoc.doc.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.doc.domain.Scenario;

public class TestJavaCodeUtil {

	private final static String JAVA_BLOCK_CODE_SNIPPET = "{ public void shouldVerify() { givenA(); } }";

	private final static String JAVA_METHOD_CONTENT_GIVEN_WHEN_THEN = "givenA(); whenB(); thenC(); ";

	private final static String JAVA_METHOD_CONTENT_GIVEN_GIVEN_WHEN_WHEN_THEN_THEN = "givenA(); givenAA(); whenB(); whenBB(); thenC(); thenCC(); ";
	private final static String JAVA_METHOD_CONTENT_GIVEN_WHEN_THEN_GIVEN_WHEN_THEN_ = "givenA(); whenB(); thenC(); givenAA(); whenBB(); thenCC(); ";

	@Test
	public void shouldReturnTheBlockAfterASpecifiedToken() {
		assertEquals("givenA();", JavaCodeUtil.javaBlockAfter(JAVA_BLOCK_CODE_SNIPPET, "shouldVerify"));
	}

	@Test
	public void shouldExtractMethodsStartingWithGivenWhenThen() {
		List<Scenario.Part> behaviour = JavaCodeUtil.getGivenWhenThenMethods(JAVA_METHOD_CONTENT_GIVEN_WHEN_THEN);
		assertEquals(new Scenario.Part("givenA"), behaviour.get(0));
		assertEquals(new Scenario.Part("whenB"), behaviour.get(1));
		assertEquals(new Scenario.Part("thenC"), behaviour.get(2));
	}

	@Test
	public void shouldExtractMethodsStartingWith2xGiven2xWhenAnd2xThen() {
		List<Scenario.Part> behaviour = JavaCodeUtil.getGivenWhenThenMethods(JAVA_METHOD_CONTENT_GIVEN_GIVEN_WHEN_WHEN_THEN_THEN);
		assertEquals(new Scenario.Part("givenA"), behaviour.get(0));
		assertEquals(new Scenario.Part("givenAA"), behaviour.get(1));
		assertEquals(new Scenario.Part("whenB"), behaviour.get(2));
		assertEquals(new Scenario.Part("whenBB"), behaviour.get(3));
		assertEquals(new Scenario.Part("thenC"), behaviour.get(4));
		assertEquals(new Scenario.Part("thenCC"), behaviour.get(5));
	}

	@Test
	public void shouldExtractMethodsStartingWith2xGivenWhenThen() {
		List<Scenario.Part> behaviour = JavaCodeUtil.getGivenWhenThenMethods(JAVA_METHOD_CONTENT_GIVEN_WHEN_THEN_GIVEN_WHEN_THEN_);
		assertEquals(new Scenario.Part("givenA"), behaviour.get(0));
		assertEquals(new Scenario.Part("givenAA"), behaviour.get(3));
		assertEquals(new Scenario.Part("whenB"), behaviour.get(1));
		assertEquals(new Scenario.Part("whenBB"), behaviour.get(4));
		assertEquals(new Scenario.Part("thenC"), behaviour.get(2));
		assertEquals(new Scenario.Part("thenCC"), behaviour.get(5));
	}

}
