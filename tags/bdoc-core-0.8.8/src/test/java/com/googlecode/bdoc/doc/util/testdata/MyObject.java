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

package com.googlecode.bdoc.doc.util.testdata;

import java.util.Date;

public class MyObject {

	public void testMethodWithOneArgument() {
		methodWithOneArgument("test1");
		methodWithOneArgument("test2");
		methodWithOneArgument("test3");
	}

	public void methodWithOneArgument(String arg1) {
	}

	public void testMethodWithTwoArguments() {
		methodWithTwoArguments(1, 3D);
		methodWithTwoArguments(2, 4D);
	}

	public void methodWithTwoArguments(Integer arg1, Double arg2) {
	}

	public void methodWithSignatureThatSpansTwoLines(byte myByte, boolean myBoolean, Long myLong, Integer myInteger, String myString,
			Date myDate, Character myCharacter, char myChar) {
	}

	void eksempelPaaTilleggspensjonsberegning(double sluttPoengtall, int poengFoer92, 
			int poentEtter91, double forventetTilleggspensjon) {
	}

}