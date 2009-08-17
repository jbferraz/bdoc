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

package com.googlecode.bdoc.doc.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestClassBehaviourSorter {

	@Test
	public void shouldSortClassBehaviourListAfterSpecifiedClassOrder() {
		List<ClassBehaviour> classBehaviourList = new ArrayList<ClassBehaviour>();

		classBehaviourList.add(new ClassBehaviour(TestC.class));
		classBehaviourList.add(new ClassBehaviour(TestA.class));
		classBehaviourList.add(new ClassBehaviour(TestB.class));

		ClassBehaviourSorter classBehaviourSorter = new ClassBehaviourSorter(new Class<?>[] { A.class, B.class, C.class });

		List<ClassBehaviour> result = classBehaviourSorter.sort(classBehaviourList);
		assertEquals("A", result.get(0).getClassName());
		assertEquals("B", result.get(1).getClassName());
		assertEquals("C", result.get(2).getClassName());
	}

	public class A {
	}

	public class TestA {
	}

	public class B {
	}

	public class TestB {
	}

	public class C {
	}

	public class TestC {
	}

}
