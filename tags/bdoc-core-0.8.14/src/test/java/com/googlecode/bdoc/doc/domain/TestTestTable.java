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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.doc.util.testdata.MyObject;

public class TestTestTable {

	@Test
	public void shouldAddRowsFromACollection() {
		TestTable testTable = new TestTable("test");

		Collection<Object> collection = new ArrayList<Object>();
		collection.addAll(asList("one", "two", "three"));
		testTable.addCollectionToRows(collection);
		assertEquals(1, testTable.getRows().get(0).getColumns().size());
		assertEquals("one", testTable.getRows().get(0).getColumns().get(0).getValue());
		assertEquals("two", testTable.getRows().get(1).getColumns().get(0).getValue());
		assertEquals("three", testTable.getRows().get(2).getColumns().get(0).getValue());
	}

	@Test
	public void shouldPopulateATestTableFromACollectionOfObjects() {
		List<MyObject> list = new ArrayList<MyObject>();
		list.add(new MyObject("2", "3"));
		list.add(new MyObject("4", "5"));
		TestTable testTable = TestTable.createTestTableFromObjectList("test", list);

		assertEquals(2, testTable.getRows().get(0).getColumns().size());

		assertEquals("2", testTable.getRows().get(0).getColumns().get(0).getValue());
		assertEquals("3", testTable.getRows().get(0).getColumns().get(1).getValue());
		assertEquals("4", testTable.getRows().get(1).getColumns().get(0).getValue());
		assertEquals("5", testTable.getRows().get(1).getColumns().get(1).getValue());
	}

	@Test
	public void shouldPopulateATestTableFromWithHeaderInfoFromTheACollectionOfObjects() {
		List<MyObject> list = new ArrayList<MyObject>();
		list.add(new MyObject("2", "3"));
		TestTable testTable = TestTable.createTestTableFromObjectList("test", list);
		assertEquals("ValueOne", testTable.getHeaderColumns().get(0).getValue());
		assertEquals("ValueTwo", testTable.getHeaderColumns().get(1).getValue());
	}

	@Test
	public void shouldNotUseGettersWithArgumentsWhilePopulatingTestTable() {
		List<MyObjectWithGettersWithArgs> list = new ArrayList<MyObjectWithGettersWithArgs>();
		list.add(new MyObjectWithGettersWithArgs("2"));
		TestTable testTable = TestTable.createTestTableFromObjectList("test", list);
		assertEquals(1, testTable.getHeaderColumns().size());
		assertEquals(1, testTable.getRows().get(0).getColumns().size());
	}

	public class MyObject {
		private String valueOne;
		private String valueTwo;

		public MyObject(String valueOne, String valueTwo) {
			super();
			this.valueOne = valueOne;
			this.valueTwo = valueTwo;
		}

		public String getValueOne() {
			return valueOne;
		}

		public String getValueTwo() {
			return valueTwo;
		}
	}

	public class MyObjectWithGettersWithArgs {
		private String value;

		public MyObjectWithGettersWithArgs(String value) {
			super();
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getValueConcatenatedWith(String str) {
			return value + str;
		}

	}
}
