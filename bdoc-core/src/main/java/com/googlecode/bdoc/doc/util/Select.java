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

import java.util.List;

/**
 * @author Per Otto Bergum Christensen
 */
public class Select<T> {

	private List<T> list;

	private Select(List<T> list) {
		this.list = list;
	}

	public static <T> Select<T> from(List<T> list) {
		return new Select<T>(list);
	}

	public T equalTo(T object) {
		if (!list.contains(object)) {
			StringBuilder listDescription = new StringBuilder();
			for (T element : list) {
				listDescription.append("[");
				listDescription.append(element);
				listDescription.append("]");
			}
			throw new ItemInListNotFoundException(object,listDescription);
		}
		return list.get(list.indexOf(object));
	}
}