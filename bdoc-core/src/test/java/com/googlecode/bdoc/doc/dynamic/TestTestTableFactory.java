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

package com.googlecode.bdoc.doc.dynamic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.TableColumn;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.dynamic.testdata.TestConvertUtilsBehaviour;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.TEST_TABLES)
public class TestTestTableFactory {

	TestTableFactory testTableFactory = new TestTableFactory();

	@Test
	public void shouldCreateATestTableForWhenTheTestMethodDoesMethodCalls() {
		assertNotNull(testTableFactory.createTestTable(TestConvertUtilsBehaviour.class, "shouldConvertFromPrimitivToPrimitiv"));
	}

	@Test
	public void shouldNotCreateATesttableWhenNoMethodCallsAreMadeByTheTestMethod() {
		assertNull(testTableFactory.createTestTable(TestConvertUtilsBehaviour.class, "shouldConvertFromLongToString"));
	}

	@Test
	public void theTestTableShouldBeDescribedWithTheMethodCallName() {
		TestTable testTable = testTableFactory.createTestTable(TestConvertUtilsBehaviour.class, "shouldConvertFromPrimitivToPrimitiv");
		assertEquals("assertPrimitivToPrimitivConversion", testTable.getCamelCaseDescription());
	}

	@Test
	public void theTestTableShouldContainOneRowForEachMethodCall() {
		TestTable testTable = testTableFactory.createTestTable(TestConvertUtilsBehaviour.class, "shouldConvertFromPrimitivToPrimitiv");
		assertEquals(3, testTable.getRows().size());
	}

	@Test
	public void theTestTableShouldHaveOneColumnForEachArgumentInAMethodCall() {
		TestTable testTable = testTableFactory.createTestTable(TestConvertUtilsBehaviour.class, "shouldConvertFromPrimitivToPrimitiv");
		assertEquals(3, testTable.getRows().get(0).getColumns().size());
	}

	@Test
	public void theTestTableColumnShouldContainValueFromTheMethodArgument() {
		TestTable testTable = testTableFactory.createTestTable(TestConvertUtilsBehaviour.class, "shouldConvertFromPrimitivToPrimitiv");
		List<TableColumn> firstRowColumns = testTable.getRows().get(0).getColumns();

		assertEquals(Boolean.FALSE, firstRowColumns.get(0).getValue());
		assertEquals(Integer.class, firstRowColumns.get(1).getValue());
		assertEquals(0, firstRowColumns.get(2).getValue());
	}

	@Test
	@Ignore
	public void theTestTableHeaderColumnShouldMatchArgumentNamesFromTheMethodInTheJavaSource() {
		TestTable testTable = testTableFactory.createTestTable(TestConvertUtilsBehaviour.class, "shouldConvertFromPrimitivToPrimitiv");

		List<TableColumn> headerColumns = testTable.getHeaderColumns();
		assertEquals("sourceValue", headerColumns.get(0));
		assertEquals("targetClass", headerColumns.get(1));
		assertEquals("expectedValue", headerColumns.get(2));
	}

}
