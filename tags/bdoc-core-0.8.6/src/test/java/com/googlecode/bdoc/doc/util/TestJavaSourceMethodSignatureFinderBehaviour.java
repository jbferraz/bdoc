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

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;

@Ref(Story.TEST_TABLES)
public class TestJavaSourceMethodSignatureFinderBehaviour {

	@Test
	public void shouldFindMethodDeclaration() {
		exampleOnMethodSignatureFound("One argument", "void oneArgMethod( String value ) { }", "oneArgMethod",
				"oneArgMethod( String value )");

		exampleOnMethodSignatureFound("Two arguments", "void twoArgMethod( String text, Integer value ) { }", "twoArgMethod",
				"twoArgMethod( String text, Integer value )");

		exampleOnMethodSignatureFound("Spaces after signature", "method( char value )    { }", "method", "method( char value )");

		exampleOnMethodSignatureFound("Argument names in upper case", "method( char myValue )    { }", "method", "method( char myValue )");

		exampleOnMethodSignatureFound("Argument names with underscore", "method( char my_value )    { }", "method",
				"method( char my_value )");

		exampleOnMethodSignatureFound("Argument names with digits", "method( char myValue0123456789 )    { }", "method",
				"method( char myValue0123456789 )");

		exampleOnMethodSignatureFound("Method calls before declaration",
				"public void test() { method('a'); method('b'); } method( char value ){ }", "method", "method( char value )");

		exampleOnMethodSignatureFound("Method calls with variables", "public void test() { method(a); method(b); } method( char value ){ }",
				"method", "method( char value )");

		exampleOnMethodSignatureFound("Signature with newline", "method( char my_value \n, int iVal ){ }", "method",
				"method( char my_value \n, int iVal )");

		exampleOnMethodSignatureFound("Signature with tab", "method( char my_value \t, int iVal ){ }", "method",
				"method( char my_value \t, int iVal )");

		exampleOnMethodSignatureFound("Signature with generics", "method(Class<?> my_value){ }", "method", "method(Class<?> my_value)");

	}

	@Test(expected = IllegalStateException.class)
	public void shouldNotFindMethodDeclarationWithZeroArguments() {
		JavaSourceMethodSignatureFinder.getSignature("zeroArgumentMethod", "void zeroArgumentMethod(){}");
	}

	void exampleOnMethodSignatureFound(String testComment, String partialJavaSource, String methodName, String expectedSignature) {
		assertEquals(testComment, expectedSignature, JavaSourceMethodSignatureFinder.getSignature(methodName, partialJavaSource));
	}

}
