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

package com.googlecode.bdoc.doc.util.apt;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.BDocException;

/**
 * @author Per Otto Bergum Chrisensen
 */
public class TestMethodMetadataFactory {

	@Test
	public void shouldRetreiveMethodMetadataForASpecificMethod() {
		MethodMetadata methodMetadata = new MethodMetadataFactory(source(TargetClass.class)).get("tableExample");
		assertEquals("msg", methodMetadata.getArgumentMetadata().get(0).getName());
	}

	@Test
	public void shouldRetreiveMethodVariableNameForMethodWithVarArg() {
		MethodMetadata methodMetadata = new MethodMetadataFactory(source(TargetClass.class)).get("varArgExample");
		assertEquals("args", methodMetadata.getArgumentMetadata().get(0).getName());
	}

	@Test(expected = BDocException.class)
	public void shouldThrowBDocExeptionIfMethodIsNotFound() {
		new MethodMetadataFactory(source(TargetClass.class)).get("noneExistent");
	}

	@Test
	public void shouldNotCreateACompiledJavaClass() {
		File javaSourceFile = source(TargetClass.class);
		new MethodMetadataFactory(javaSourceFile);
		String targetClassNameWithoutPackage = TargetClass.class.getName().replace(TargetClass.class.getPackage().getName() + ".", "");
		
		assertFalse(new File(javaSourceFile.getParent(), targetClassNameWithoutPackage + ".class").exists());
	}

	public static File source(Class<?> c) {
		return MethodMetadataFactory.source(c, BConst.SRC_TEST_JAVA);
	}

}
