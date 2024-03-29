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

package com.googlecode.bdoc.diff.domain;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.diff.domain.added.TestClassRepresentsTestsThatAreAdded2;
import com.googlecode.bdoc.diff.domain.testpackageremoved.TestClassRepresentsTestsThatAreRemoved;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.ClassBehaviourSorter;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.testdata.ExReference;
import com.googlecode.bdoc.doc.testdata.ExStory;

/**
 * @author Per Otto Bergum Christensen
 */
public class BDocUserStoryDiffDocTestdataHelper {

	private final BDoc emptyBddDoc = createBDoc();
	{
		emptyBddDoc.setProject(new ProjectInfo("emptyproject", "version1"));
	}
	private final BDoc bddDocWithGeneralBehaviourAndAStory = new BDoc(ExReference.class,new ClassBehaviourSorter());
	private final BDoc bddDocWithUpdatedStory = new BDoc(ExReference.class, new ClassBehaviourSorter());

	{
		bddDocWithGeneralBehaviourAndAStory.setProject(new ProjectInfo("test-project", "version1"));
		bddDocWithGeneralBehaviourAndAStory.addBehaviourFrom(new TestClass(TestWithGeneralBehaviorAndRefToStory1.class),
				BConst.SRC_TEST_JAVA);
		bddDocWithGeneralBehaviourAndAStory.addBehaviourFrom(new TestClass(TestWithReferenceToOldStoryDescription.class),
				BConst.SRC_TEST_JAVA);
		bddDocWithGeneralBehaviourAndAStory.addBehaviourFrom(new TestClass(TestWithReferenceToStoryThatShouldBeDeleted.class),
				BConst.SRC_TEST_JAVA);
		bddDocWithGeneralBehaviourAndAStory.addBehaviourFrom(new TestClass(TestClassRepresentsTestsThatAreRemoved.class),
				BConst.SRC_TEST_JAVA);

		bddDocWithUpdatedStory.setProject(new ProjectInfo("test-test-project", "version2"));
		bddDocWithUpdatedStory.addBehaviourFrom(new TestClass(TestWithGeneralBehaviorAndRefToStory1.class), BConst.SRC_TEST_JAVA);
		bddDocWithUpdatedStory.addBehaviourFrom(new TestClass(TestAnotherWithRefToStory1.class), BConst.SRC_TEST_JAVA);
		bddDocWithUpdatedStory.addBehaviourFrom(new TestClass(TestAnotherWithRefToStory2.class), BConst.SRC_TEST_JAVA);

		bddDocWithUpdatedStory.addBehaviourFrom(new TestClass(TestWithReferenceToNewStoryDescription.class), BConst.SRC_TEST_JAVA);
		bddDocWithUpdatedStory.addBehaviourFrom(new TestClass(TestClassRepresentsTestsThatAreAdded2.class), BConst.SRC_TEST_JAVA);

		ClassBehaviour classBehaviour = bddDocWithUpdatedStory.userStoryFor(ExStory.STORY1).classBehaviourFor(
				TestWithGeneralBehaviorAndRefToStory1.class);

		classBehaviour.getSpecifications().add(new Specification("shouldBeAddedInNextVersion"));
		classBehaviour.getScenarios().add(new Scenario("given1234WhenAddedInTestThenThisScenarioShouldBeAddedInNextVersion"));

		classBehaviour.getScenarios().remove(new Scenario("givenSomeScenarioWhenItIsDeletedThenEnsureItIsGone"));
		classBehaviour.getSpecifications().remove(new Specification("shouldBeDeletedInNextVersion"));
		classBehaviour.getStatements().remove(new Statement("statementThatWillBeDeletedInNextVersion"));
	}

	private BDocUserStoryDiffDocTestdataHelper() {
	}

	private static BDocUserStoryDiffDocTestdataHelper instance() {
		return new BDocUserStoryDiffDocTestdataHelper();
	}

	public class TestWithReferenceToOldStoryDescription {
		@Test
		@ExReference(ExStory.STORY_ID_10_OLD_VERSION)
		public void shouldWorkAsTestdataForStoryId10_OldVersion() {
		}

		@Test
		@ExReference(ExStory.STORY_ID_10_OLD_VERSION)
		public void givenTestdataXYForStoryId10oldVersionWhen33Then55() {
		}
	}

	public class TestWithReferenceToNewStoryDescription {
		@Test
		@ExReference(ExStory.STORY_ID_10_NEW_VERSION)
		public void shouldWorkAsTestdataForStory10_NewVersion() {
		}

		@Test
		@ExReference(ExStory.STORY_ID_10_NEW_VERSION)
		public void givenTestdataForStory10_NewVersionWhenThen() {
		}
	}

	public class TestWithGeneralBehaviorAndRefToStory1 {

		@Test
		public void shouldIllustrateGeneralBehaviour() {
		}

		@Test
		@ExReference(ExStory.STORY1)
		public void shouldBeDeletedInNextVersion() {
		}

		@Test
		@ExReference(ExStory.STORY1)
		public void statementThatWillBeDeletedInNextVersion() {
		}

		@Test
		@ExReference(ExStory.STORY1)
		public void givenSomeScenarioWhenItIsDeletedThenEnsureItIsGone() {
		}

		@Test
		@ExReference(ExStory.STORY1)
		public void givenWhenThen() {
		}
	}

	public class TestAnotherWithRefToStory1 {

		@Test
		@ExReference(ExStory.STORY1)
		public void givenWhenThen2() {
		}

		@Test
		@ExReference(ExStory.STORY1)
		public void givenWhenThen3() {
		}

		@Test
		@ExReference(ExStory.STORY1)
		public void shouldBehave() {
		}

		@Test
		@ExReference(ExStory.STORY1)
		public void shouldBehaveTwice() {
		}
	}

	public class TestAnotherWithRefToStory2 {

		@Test
		@ExReference(ExStory.STORY2)
		public void givenSomeCriteriaWhenSomeActionThen() {
		}

		@Test
		@ExReference(ExStory.STORY2)
		public void givenValue0WhenActionSaveThenEnsure() {
		}
	}

	public class TestWithOnlyGeneralBehaviourContent {
		@Test
		public void shouldBehave() {
		}
	}

	@ExReference(ExStory.STORY_TO_BE_DELETED)
	public class TestWithReferenceToStoryThatShouldBeDeleted {
		@Test
		public void shouldBehave() {
		}
	}

	public class TestClassWithOneSpecification {

		@Test
		public void shouldBeTheOnlyTestInTheTestClass() {
		}
	}

	public static BDoc getEmptyBddDoc() {
		return instance().emptyBddDoc;
	}

	public static BDoc getBDocWithOneSpecification() {
		BDoc bdoc = createBDoc();
		bdoc.setProject(new ProjectInfo("emptyproject", "version1"));
		bdoc.addBehaviourFrom(new TestClass(TestClassWithOneSpecification.class), BConst.SRC_TEST_JAVA);
		return bdoc;
	}

	public static BDoc getBddDocWithGeneralBehaviourAndAStory() {
		return instance().bddDocWithGeneralBehaviourAndAStory;
	}

	public static BDoc getBddDocWithUpdatedStory() {
		return instance().bddDocWithUpdatedStory;
	}

	public static BDocDiff getBddDocDiffForUpdatedStory() {
		return new BDocDiff(instance().bddDocWithGeneralBehaviourAndAStory, instance().bddDocWithUpdatedStory);
	}

	public static BDoc createBDoc() {
		return new BDoc(ExReference.class, new ClassBehaviourSorter());
	}
}
