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

package bdddoc4j.diff.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds the new and delete items in two given lists.
 * 
 * @author Per Otto Bergum Christensen
 */
public class SimpleDiff<T> implements Diff {

	private List<T> newItems;
	private List<T> deletedItems;

	public SimpleDiff(List<T> oldList, List<T> newList) {
		newItems = diff(oldList, newList);
		deletedItems = diff(newList, oldList);
	}

	/**
	 * Returns elements found in list2, not found in list1
	 * 
	 * @param <T>
	 *            of element in list1 and list2
	 * @param list1
	 *            to compare
	 * @param list2
	 *            to compare
	 * @return elements in list2, not found in list1 as an unmodifiable list
	 */
	private List<T> diff(List<T> list1, List<T> list2) {
		List<T> result = new ArrayList<T>();
		for (T item : list2) {
			if (!list1.contains(item)) {
				result.add(item);
			}
		}
		return result;
	}

	public List<T> getNewItems() {
		return newItems;
	}

	public List<T> getDeletedItems() {
		return deletedItems;
	}

	public boolean diffExists() {
		return !(newItems.isEmpty() && deletedItems.isEmpty());
	}
}
