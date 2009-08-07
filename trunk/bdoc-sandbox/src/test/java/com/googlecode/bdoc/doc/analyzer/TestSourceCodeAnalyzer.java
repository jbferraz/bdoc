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

import org.junit.Test;

import com.googlecode.bdoc.sandbox.BConst;

/**
 * 
 * @author Micael Vesterlund
 * 
 */
public class TestSourceCodeAnalyzer {

	private static File getFile(Class c) {
		return new File(BConst.SRC_TEST_JAVA, c.getName().replace('.', '/') + ".java");
	}

	@Test
	public final void shouldFindTestMethodsAndItsScenarios() {
		List<MethodInfo> methodInfos = SourceCodeAnalyzer.analyze(getFile(SomeBehavior.class));
		assertEquals(2, methodInfos.size());
		assertEquals(0, methodInfos.get(0).getMethodInfos().size());
		assertEquals(3, methodInfos.get(1).getMethodInfos().size());
	}

}
