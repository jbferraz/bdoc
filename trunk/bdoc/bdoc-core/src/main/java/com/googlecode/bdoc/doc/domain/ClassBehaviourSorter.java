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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClassBehaviourSorter {

	private List<String> classNameOrder = new ArrayList<String>();

	public ClassBehaviourSorter(Class<?>... classOrder) {
		for (Class<?> class1 : classOrder) {
			classNameOrder.add(class1.getName());
		}
	}

	public List<ClassBehaviour> sort(List<ClassBehaviour> classBehaviourList) {

		Collections.sort(classBehaviourList, new Comparator<ClassBehaviour>() {

			public int compare(ClassBehaviour cb1, ClassBehaviour cb2) {
				for (String clazz : classNameOrder) {
					if (clazz.endsWith(cb1.getClassName())) {
						return -1;
					}
					if (clazz.endsWith(cb2.getClassName())) {
						return 1;
					}
				}
				return 0;
			}

		});

		return classBehaviourList;
	}
}
