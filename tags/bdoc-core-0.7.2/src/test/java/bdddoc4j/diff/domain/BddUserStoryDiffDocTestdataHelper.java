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

import org.junit.Test;

import bdddoc4j.core.domain.BddDoc;
import bdddoc4j.core.domain.ClassBehaviour;
import bdddoc4j.core.domain.Project;
import bdddoc4j.core.domain.Scenario;
import bdddoc4j.core.domain.Specification;
import bdddoc4j.core.domain.Statement;
import bdddoc4j.core.testdata.ExReference;
import bdddoc4j.core.testdata.ExStory;
import bdddoc4j.diff.domain.added.TestClassRepresentsTestsThatAreAdded2;
import bdddoc4j.diff.domain.testpackageremoved.TestClassRepresentsTestsThatAreRemoved;

public class BddUserStoryDiffDocTestdataHelper {

	private final BddDoc emptyBddDoc = new BddDoc(org.junit.Test.class, ExReference.class);
	{
		emptyBddDoc.setProject(new Project("emptyproject", "version1"));
	}
	private final BddDoc bddDocWithGeneralBehaviourAndAStory = new BddDoc(org.junit.Test.class, ExReference.class);
	private final BddDoc bddDocWithUpdatedStory = new BddDoc(org.junit.Test.class, ExReference.class);

	{
		bddDocWithGeneralBehaviourAndAStory.setProject(new Project("test-project", "version1"));
		bddDocWithGeneralBehaviourAndAStory.addBehaviourFrom(TestWithGeneralBehaviorAndRefToStory1.class);
		bddDocWithGeneralBehaviourAndAStory.addBehaviourFrom(TestWithReferenceToOldStoryDescription.class);
		bddDocWithGeneralBehaviourAndAStory.addBehaviourFrom(TestWithReferenceToStoryThatShouldBeDeleted.class);
		bddDocWithGeneralBehaviourAndAStory.addBehaviourFrom(TestClassRepresentsTestsThatAreRemoved.class);

		bddDocWithUpdatedStory.setProject(new Project("test-test-project", "version2"));
		bddDocWithUpdatedStory.addBehaviourFrom(TestWithGeneralBehaviorAndRefToStory1.class);
		bddDocWithUpdatedStory.addBehaviourFrom(TestAnotherWithRefToStory1.class);
		bddDocWithUpdatedStory.addBehaviourFrom(TestAnotherWithRefToStory2.class);

		bddDocWithUpdatedStory.addBehaviourFrom(TestWithReferenceToNewStoryDescription.class);
		bddDocWithUpdatedStory.addBehaviourFrom(TestClassRepresentsTestsThatAreAdded2.class);

		ClassBehaviour classBehaviour = bddDocWithUpdatedStory.userStoryFor(ExStory.STORY1).classBehaviourFor(
				TestWithGeneralBehaviorAndRefToStory1.class);

		classBehaviour.getSpecifications().add(new Specification("shouldBeAddedInNextVersion"));
		classBehaviour.getScenarios().add(new Scenario("given1234WhenAddedInTestThenThisScenarioShouldBeAddedInNextVersion"));

		classBehaviour.getScenarios().remove(new Scenario("givenSomeScenarioWhenItIsDeletedThenEnsureItIsGone"));
		classBehaviour.getSpecifications().remove(new Specification("shouldBeDeletedInNextVersion"));
		classBehaviour.getStatements().remove(new Statement("statementThatWillBeDeletedInNextVersion"));
	}

	private BddUserStoryDiffDocTestdataHelper() {
	}

	private static BddUserStoryDiffDocTestdataHelper instance() {
		return new BddUserStoryDiffDocTestdataHelper();
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

	public class TestWithOnlyGeneralBehaviour {
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

	public static BddDoc getEmptyBddDoc() {
		return instance().emptyBddDoc;
	}

	public static BddDoc getBddDocWithGeneralBehaviourAndAStory() {
		return instance().bddDocWithGeneralBehaviourAndAStory;
	}

	public static BddDoc getBddDocWithUpdatedStory() {
		return instance().bddDocWithUpdatedStory;
	}

	public static BddDocDiff getBddDocDiffForUpdatedStory() {
		return new BddDocDiff(instance().bddDocWithGeneralBehaviourAndAStory, instance().bddDocWithUpdatedStory);
	}
}
