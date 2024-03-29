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

package com.googlecode.bdoc.doc.analyzer;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.bdoc.sandbox.BConst;

/**
 * 
 * @author Micael Vesterlund
 * 
 */
public class TestSourceCodeAnalyzer {

	private static File file;

	@BeforeClass
	public static void beforeClass() {
		file = getFile(SomeBehavior.class);
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
	}

	private static File getFile(Class<?> c) {
		return new File(BConst.SRC_TEST_JAVA, c.getName().replace('.', '/') + ".java");
	}

	@Test
	public void shouldFindNameOfClassUnderTest() {
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
		assertEquals("SomeBehavior", classInfo.getName());
	}

	@Test
	public final void shouldOnlyFindMethodsMarkedWithTest() {
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
		List<MethodInfo> methodInfos = classInfo.getMethods();

		assertEquals(6, methodInfos.size());
		assertEquals("shouldFindLocale", methodInfos.get(0).getName());
		assertEquals("shouldAddDepositToBalance", methodInfos.get(1).getName());
		assertEquals("thisMethodAreIgnored", methodInfos.get(2).getName());
		assertEquals("eksemplerPaaBensjonsBeregning", methodInfos.get(3).getName());
		assertEquals("shouldPopLastPushedValueFirst", methodInfos.get(4).getName());
	}

	@Test
	public final void shouldTellIfMethodAreMarkedWithIgnore() {
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
		List<MethodInfo> methodInfos = classInfo.getMethods();

		assertEquals(false, methodInfos.get(0).isIgnored());
		assertEquals(false, methodInfos.get(1).isIgnored());
		assertEquals(true, methodInfos.get(2).isIgnored());
		assertEquals(false, methodInfos.get(3).isIgnored());
	}

	@Test
	public final void shouldFindScenarios() {
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
		List<MethodInfo> methodInfos = classInfo.getMethods();

		assertEquals(0, methodInfos.get(0).getScenarios().size());
		assertEquals(1, methodInfos.get(1).getScenarios().size());
		assertEquals(0, methodInfos.get(2).getScenarios().size());
		assertEquals(0, methodInfos.get(3).getScenarios().size());
	}

	@Test
	public final void shouldFindScenariosParts() {
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
		List<MethodInfo> methodInfos = classInfo.getMethods();

		assertEquals(1, methodInfos.get(1).getScenarios().get(0).getGivens().length);
		assertEquals(1, methodInfos.get(1).getScenarios().get(0).getWhens().length);
		assertEquals(1, methodInfos.get(1).getScenarios().get(0).getThens().length);
	}

	@Test
	public final void shouldFindScenariosPartsAndHandleAnds() {
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
		List<MethodInfo> methodInfos = classInfo.getMethods();

		assertEquals(3, methodInfos.get(4).getScenarios().get(0).getGivens().length);
		assertEquals(1, methodInfos.get(4).getScenarios().get(0).getWhens().length);
		assertEquals(3, methodInfos.get(4).getScenarios().get(0).getThens().length);
	}

	@Test
	public final void shouldFindAllScenariosInMethod() {
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
		List<MethodInfo> methodInfos = classInfo.getMethods();

		assertEquals(2, methodInfos.get(5).getScenarios().size());

		assertEquals(1, methodInfos.get(5).getScenarios().get(0).getGivens().length);
		assertEquals(1, methodInfos.get(5).getScenarios().get(0).getWhens().length);
		assertEquals(1, methodInfos.get(5).getScenarios().get(0).getThens().length);

		assertEquals(1, methodInfos.get(5).getScenarios().get(1).getGivens().length);
		assertEquals(1, methodInfos.get(5).getScenarios().get(1).getWhens().length);
		assertEquals(1, methodInfos.get(5).getScenarios().get(1).getThens().length);
	}

	@Test
	public final void shouldFindParameters() {
		ClassInfo classInfo = SourceCodeAnalyzer.analyze(file);
		List<MethodInfo> methodInfos = classInfo.getMethods();
		String given = methodInfos.get(5).getScenarios().get(0).getGivens()[0];
		assertEquals("givenAnAccountWithInitialBalance0", given);
		given = methodInfos.get(4).getScenarios().get(0).getGivens()[1];
		assertEquals("andPushedIsCalledWithFoo", given);
	}
}
