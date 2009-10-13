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

package com.googlecode.bdoc.difflog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.DIFF_LOG)
public class TestDiffLog {

	private File changeLogXmlFile = new File("target/changeLog.xml");

	@Before
	public void resetXmlFile() {
		changeLogXmlFile.delete();
	}

	@Test
	public void shouldBeAbleToReadAndWriteChangeLogInstanceFromXmlFile() {
		DiffLog diffLog = new DiffLog();
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());

		diffLog.writeToFile(changeLogXmlFile);
		DiffLog changeLogfromFile = DiffLog.fromXmlFile(changeLogXmlFile);
		assertEquals(diffLog.latestBDoc(), changeLogfromFile.latestBDoc());
	}

	@Test
	public void shouldNotProduceAnyDiffForTheFirstBDocScan() {
		DiffLog diffLog = new DiffLog();
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertTrue(diffLog.getDiffList().isEmpty());
	}

	@Test
	public void shouldNotAddBDocDiffIfNoDiffExists() {
		DiffLog diffLog = new DiffLog();
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertEquals(0, diffLog.getDiffList().size());
	}

	@Test
	public void shouldAddBDocDiffWhenDiffExists() {
		DiffLog diffLog = new DiffLog();
		diffLog.scan(BDocTestHelper.bdocWithProject());
		diffLog.scan(BDocTestHelper.bdocWithOneSpecification());
		assertEquals(1, diffLog.getDiffList().size());
	}

	@Test
	public void shouldSortDiffListWithLatestChangeFirst() {
		DiffLog diffLog = new DiffLog();

		diffLog.scan(new BDoc().addBehaviourFrom(new TestClass(TestWithSpecification.class), BConst.SRC_TEST_JAVA));
		diffLog.scan(new BDoc().addBehaviourFrom(new TestClass(TestWithStatement.class), BConst.SRC_TEST_JAVA));
		diffLog.scan(new BDoc().addBehaviourFrom(new TestClass(TestWithScenario.class), BConst.SRC_TEST_JAVA));

		assertFalse(diffLog.getDiffList().get(0).getModuleBehaviourDiff().getPackageDiff().get(0).getNewScenarios().isEmpty());
		assertFalse(diffLog.getDiffList().get(1).getModuleBehaviourDiff().getPackageDiff().get(0).getNewClassStatements().isEmpty());
	}

	@Test
	public void shouldOnlyKeepASpecifiedNumberOfChangeLogItems() {
		DiffLog diffLog = new DiffLog();
		diffLog.setNumberOfChangeLogItemsToKeep(2);

		diffLog.scan(new BDoc().addBehaviourFrom(new TestClass(TestWithSpecification.class), BConst.SRC_TEST_JAVA));
		diffLog.scan(new BDoc().addBehaviourFrom(new TestClass(TestWithStatement.class), BConst.SRC_TEST_JAVA));
		diffLog.scan(new BDoc().addBehaviourFrom(new TestClass(TestWithScenario.class), BConst.SRC_TEST_JAVA));
		diffLog.scan(new BDoc().addBehaviourFrom(new TestClass(TestWithYetAnotherSpecification.class), BConst.SRC_TEST_JAVA));

		assertEquals(2, diffLog.getDiffList().size());
		assertFalse("last diff should be kept", diffLog.getDiffList().get(0).getModuleBehaviourDiff().getPackageDiff().get(0)
				.getNewClassSpecifications().isEmpty());
	}
	
	@Test
	public void defaultNumberOfChangeLogItemsShouldBe100() {
		DiffLog diffLog = new DiffLog();
		assertEquals( 100,  diffLog.getNumberOfChangeLogItemsToKeep() );		
	}
	
	@Test
	public void ifTheNumberOfChangeLogItemsToKeepIsSetToZeroBecauseOfSerializationTheDefaultValueWillBeUsed() {
		DiffLog diffLog = new DiffLog();
		diffLog.setNumberOfChangeLogItemsToKeep(0);
		assertEquals( 100,  diffLog.getNumberOfChangeLogItemsToKeep() );		
		
	}

	// --- testdata
	class TestWithSpecification {
		@Test
		public void shouldBeASpecifiaction() {

		}
	}

	class TestWithYetAnotherSpecification {
		@Test
		public void shouldBeYetAnotherSpecifiaction() {

		}
	}

	class TestWithStatement {
		@Test
		public void isAStatement() {

		}
	}

	class TestWithScenario {
		@Test
		public void givenWhenThen() {

		}
	}

}
