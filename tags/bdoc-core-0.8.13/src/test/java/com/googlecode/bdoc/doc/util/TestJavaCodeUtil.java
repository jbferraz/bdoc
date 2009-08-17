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

package com.googlecode.bdoc.doc.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.util.testdata.MyObject;

/**
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
public class TestJavaCodeUtil {

	private final static String JAVA_BLOCK_WITH_TRY_CATCH_CODE_SNIPPET2 = "public void testShouldBePossibleToBlaha() throws RemoteException { givenAn(idea); givenAn(null); givenAn(); try { whenBlaha(); } catch (ValidationException e) { thenBlaha(e); } }";

	private final static String JAVA_BLOCK_CODE_SNIPPET = "{ public void shouldVerify() { givenA(); } }";

	private final static String JAVA_BLOCK_WITH_TRY_CATCH_CODE_SNIPPET = "{ public void shouldVerify() { try {givenA();} catch {thenInsideCatch();} thenAfterCatch();} }";

	private final static String JAVA_METHOD_CONTENT_GIVEN_WHEN_THEN = "givenA(); whenB(); thenC(); ";
	private final static String JAVA_METHOD_CONTENT_WITH_DATA = "given(AnArgument); ";

	private final static String JAVA_METHOD_CONTENT_GIVEN_GIVEN_WHEN_WHEN_THEN_THEN = "givenA(); givenAA(); whenB(); whenBB(); thenC(); thenCC(); ";
	private final static String JAVA_METHOD_CONTENT_GIVEN_WHEN_THEN_GIVEN_WHEN_THEN_ = "givenA(); whenB(); thenC(); givenAA(); whenBB(); thenCC(); ";

	@Test
	public void shouldIncludeParameterWithOnlyFirstLetterInUpperCase() {
		List<Scenario.Part> behaviour = JavaCodeUtil.getGivenWhenThenMethods(JAVA_METHOD_CONTENT_WITH_DATA);
		assertEquals(new Scenario.Part("givenAnArgument"), behaviour.get(0));
	}

	@Test
	public void shouldReturnTheBlockAfterASpecifiedToken() {
		assertEquals("{ givenA();", JavaCodeUtil.javaBlockAfter(JAVA_BLOCK_CODE_SNIPPET, "shouldVerify"));
		assertEquals("{ try {givenA();} catch {thenInsideCatch();} thenAfterCatch();", JavaCodeUtil.javaBlockAfter(
				JAVA_BLOCK_WITH_TRY_CATCH_CODE_SNIPPET, "shouldVerify"));
	}

	@Test
	public void shouldExtractMethodsStartingWithGivenWhenThenWithTryCatchBlocks() {
		String javaBlockAfter = JavaCodeUtil.javaBlockAfter(JAVA_BLOCK_WITH_TRY_CATCH_CODE_SNIPPET, "shouldVerify");
		List<Scenario.Part> behaviour = JavaCodeUtil.getGivenWhenThenMethods(javaBlockAfter);
		assertEquals(new Scenario.Part("givenA"), behaviour.get(0));
		assertEquals(new Scenario.Part("thenInsideCatch"), behaviour.get(1));
		assertEquals(new Scenario.Part("thenAfterCatch"), behaviour.get(2));

		javaBlockAfter = JavaCodeUtil.javaBlockAfter(JAVA_BLOCK_WITH_TRY_CATCH_CODE_SNIPPET2, "testShouldBePossibleToBlaha");
		behaviour = JavaCodeUtil.getGivenWhenThenMethods(javaBlockAfter);
		assertEquals(new Scenario.Part("givenAnIdea"), behaviour.get(0));
		assertEquals(new Scenario.Part("whenBlaha"), behaviour.get(3));
		assertEquals(new Scenario.Part("thenBlahaE"), behaviour.get(4));
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

	@Test
	public void shouldListArgumentNamesForAMethodWithOneArgument() {
		List<String> argNames = JavaCodeUtil.getArgumentNames(new TestClass(MyObject.class), "methodWithOneArgument", BConst.SRC_TEST_JAVA);
		assertEquals("arg1", argNames.get(0));
	}

	@Test
	public void shouldListArgumentNamesForAMethodWithTwoArguments() {
		List<String> argNames = JavaCodeUtil
				.getArgumentNames(new TestClass(MyObject.class), "methodWithTwoArguments", BConst.SRC_TEST_JAVA);
		assertEquals("arg1", argNames.get(0));
		assertEquals("arg2", argNames.get(1));
	}

	@Test
	public void shouldListArgumentNamesForAMethodWithASignatureThatSpansTwoLines() {
		List<String> argNames = JavaCodeUtil.getArgumentNames(new TestClass(MyObject.class), "methodWithSignatureThatSpansTwoLines",
				BConst.SRC_TEST_JAVA);

		List<String> expectedArgumentNames = Arrays.asList(new String[] { "myByte", "myBoolean", "myLong", "myInteger", "myString",
				"myDate", "myCharacter", "myChar" });

		assertEquals(expectedArgumentNames, argNames);
	}
}
