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

import static bdddoc4j.core.util.Select.from;

import java.util.ArrayList;
import java.util.List;

public class ListDiff<T, E extends Diff> {

	private List<T> newItems;
	private List<T> deletedItems;
	private List<E> updatedItems;

	public ListDiff(List<T> oldList, List<T> newList) {
		newItems = diff(oldList, newList);
		deletedItems = diff(newList, oldList);
		updatedItems = findUpdatedItems(oldList, newList);
	}

	@SuppressWarnings("unchecked")
	private List<E> findUpdatedItems(List<T> oldVersions, List<T> newVersions) {
		List<E> result = new ArrayList<E>();

		for (T newItem : newVersions) {

			if (oldVersions.contains(newItem)) {
				E diffObject = (E) DiffFactory.create(from(oldVersions).equalTo(newItem), newItem);
				if (diffObject.diffExists()) {
					result.add(diffObject);
				}
			}
		}
		return result;
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

	public List<E> getUpdatedItems() {
		return updatedItems;
	}

	public boolean diffExists() {
		return !(newItems.isEmpty() && deletedItems.isEmpty() && updatedItems.isEmpty());
	}
}
