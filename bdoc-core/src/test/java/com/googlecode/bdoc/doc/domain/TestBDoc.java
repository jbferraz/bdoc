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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.testdata.MyTestTablesBehaviour;
import com.googlecode.bdoc.doc.domain.testdata.TestDomainBehaviour;
import com.googlecode.bdoc.doc.dynamic.RuntimeBehaviourFactory;
import com.googlecode.bdoc.doc.testdata.ExReference;
import com.googlecode.bdoc.doc.testdata.ExReference2;
import com.googlecode.bdoc.doc.testdata.ExStory;
import com.googlecode.bdoc.doc.testdata.TestClassWithExStory2Reference;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.CREATE_BDOC_FROM_CODE)
public class TestBDoc {

	private BDoc bdoc;

	@Before
	public void resetBddDoc() {
		bdoc = new BDoc(org.junit.Test.class, ExReference.class, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(TestExampleAnnotatedScenariosAndSpecifications.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestExampleAnnotatedClass.class), BConst.SRC_TEST_JAVA);
	}

	@Test
	public void shouldRunWithAStoryRefAnnotation() {
		bdoc = new BDoc(org.junit.Test.class, null, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(TestExampleAnnotatedScenariosAndSpecifications.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestExampleAnnotatedClass.class), BConst.SRC_TEST_JAVA);
	}

	@Test
	public void shouldCreateTheDistinctNumberOfUserStories() {
		bdoc.addBehaviourFrom(new TestClass(TestExampleAnnotatedClass.class), BConst.SRC_TEST_JAVA);
		assertEquals(3, bdoc.getUserstories().size());
	}

	@Test
	public void shouldCreateUserStoriesReferencedByTests() {
		assertTrue(bdoc.getUserstories().contains(new UserStory(ExStory.STORY1)));
	}

	@Test
	public void shouldExtractBehaviourFromMetodsAnnotatedAsAJunitTest() {
		ClassBehaviour classBehaviour = bdoc.userStoryFor(ExStory.STORY3).classBehaviourFor(TestExampleAnnotatedClass.class);
		assertEquals(0, classBehaviour.getSpecifications().size());
		assertEquals(1, classBehaviour.getScenarios().size());
	}

	@Test
	public void shouldExtractBehaviourFromMetodsAnnotatedWithAnAnnotationClassCalledTest() {
		BDoc bdoc = new BDoc(null, null, null);
		bdoc.addBehaviourFrom(new TestClass(TestTestsAnnotatedWithTest.class), BConst.SRC_TEST_JAVA);
		Specification specification = bdoc.getGeneralBehaviour().getPackages().get(0).getClassSpecifications().get(0).getSpecifications()
				.get(0);

		assertEquals(new Specification("shouldBePickedUpByBDoc"), specification);
	}

	@Test
	public void shouldExtractBehaviourFromMetodsStartingWithTheWordTest() {
		bdoc = new BDoc(org.junit.Test.class, ExReference.class, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(TestExampleJunit3.class), BConst.SRC_TEST_JAVA);

		assertTrue(bdoc.getGeneralBehaviour().getPackages().get(0).getClassSpecifications().get(0).getSpecifications().contains(
				new Specification("shouldShowThatJUnit3IsSupported")));

		List<Scenario> scenarios = bdoc.getGeneralBehaviour().getPackages().get(0).getScenarios();
		assertTrue(scenarios.contains(new Scenario("givenAJunit3TestWhenBddDocIsRunThenEnsureTheJUnit3TestIsExtracted")));
	}

	@Test
	public void givenATestclassWhenATestmethodDescribingBehaviourIsMarkedAsAScenarioThenEnsureTheScenarioIsCreated() {
		UserStory story1 = bdoc.userStoryFor(ExStory.STORY1);
		assertTrue(story1.getScenarios().contains(new Scenario("givenWhenThen")));
	}

	@Test
	public void givenATestclassWhenATestmethodDescribingBehaviourIsMarkedAsASpecificationThenEnsureTheSpecificationIsCreated() {
		ClassBehaviour behaviour = bdoc.userStoryFor(ExStory.STORY1).classBehaviourFor(TestExampleAnnotatedScenariosAndSpecifications.class);
		assertTrue(behaviour.getSpecifications().contains(new Specification("shouldBehaveLikeThat")));
	}

	@Test
	public void givenATestmethodMarkedAsBehaviourWhenTheTestmethodIsAnnotatedWithAReferenceToAUserstoryThenEnsureTheCreatedBehaviourIsAddedToThatUserstory() {
		ClassBehaviour behaviour = bdoc.userStoryFor(ExStory.STORY2).classBehaviourFor(TestExampleAnnotatedScenariosAndSpecifications.class);
		assertTrue(behaviour.getSpecifications().contains(new Specification("shouldBehaveLikeThisIfThat")));
	}

	@Test
	public void givenATestclassAnnotatedWithAReferenceToAStoryWhenATestmethodDescribingBehaviourIsNotAnnotatedWithAReferenceToAStoryThenEnsureTheCreatedBehaviourIsAddedToTheUserstoryForTheTestclass() {
		UserStory story3 = bdoc.userStoryFor(ExStory.STORY3);
		assertTrue(story3.getScenarios().contains(new Scenario("givenAScenarioForStory3WhenThen")));
	}

	@Test
	public void shouldIgnoreClassesThatAreNotTests() {
		bdoc = new BDoc(org.junit.Test.class, Ref.class, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(Ref.class), BConst.SRC_TEST_JAVA);
		assertEquals(0, bdoc.getUserstories().size());
	}

	@Test
	public void shouldHandleStoryReferencesThatDoesNotImplementUserStoryDescriptionDirectly() {
		bdoc = new BDoc(org.junit.Test.class, ExReference2.class, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(TestClassWithExStory2Reference.class), BConst.SRC_TEST_JAVA);
		assertEquals("Test story", bdoc.getUserstories().get(0).getTitle());
	}

	@Test
	public void shouldBeAbleToGetAnAddedUserStory() {
		assertNotNull(bdoc.userStoryFor(ExStory.STORY1));
	}

	@Test
	public void givenATestClassWithNoReferenceToAStoryWhenBdddocIsGeneratedThenTheSpecifiedBehaviourShouldNotBeAddedToAnyStories() {
		bdoc = new BDoc(org.junit.Test.class, ExReference.class, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(TestExampleNoStories.class), BConst.SRC_TEST_JAVA);
		assertEquals(0, bdoc.getUserstories().size());
	}

	@Test
	public void givenATestClassWithNoReferenceToAStoryWhenBdddocIsGeneratedThenTheSpecifiedBehaviourShouldBeAddedToGeneralBehaviour() {
		bdoc = new BDoc(org.junit.Test.class, ExReference.class, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(TestExampleNoStories.class), BConst.SRC_TEST_JAVA);

		assertTrue(bdoc.getGeneralBehaviour().getPackages().get(0).getClassSpecifications().get(0).getSpecifications().contains(
				new Specification("shouldVerifyTheImportantStuff")));
		assertTrue(bdoc.getGeneralBehaviour().getPackages().get(0).getScenarios().contains(
				new Scenario("givenSomethingWhenAnActionThenVerifyResult")));

	}

	@Test
	public void shouldFindPackageOfAddedTestClassWithBehaviour() {
		assertTrue(bdoc.userStoryFor(ExStory.STORY1).getPackages().contains(Package.forClass(TestExampleAnnotatedClass.class)));
	}

	@Test
	public void shouldIgnoreMethodsAnnotatedWithIgnore() {
		bdoc = new BDoc(org.junit.Test.class, ExReference.class, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(TestExampleIgnoreMethods.class), BConst.SRC_TEST_JAVA);
		GeneralBehaviour behaviour = bdoc.getGeneralBehaviour();
		List<Package> packages = behaviour.getPackages();
		Package package1 = packages.get(0);
		List<ClassBehaviour> classBehaviours = package1.getClassBehaviour();
		ClassBehaviour classBehaviour = classBehaviours.get(0);
		assertEquals(0, classBehaviour.getScenarios().size());
		assertEquals(1, classBehaviour.getSpecifications().size());
		assertEquals(0, classBehaviour.getStatements().size());
	}

	@Test
	public void shouldIgnoreClassAnnotatedWithIgnore() {
		bdoc = new BDoc(org.junit.Test.class, ExReference.class, org.junit.Ignore.class);
		bdoc.addBehaviourFrom(new TestClass(TestExampleIgnoreClass.class), BConst.SRC_TEST_JAVA);
		GeneralBehaviour behaviour = bdoc.getGeneralBehaviour();
		List<Package> packages = behaviour.getPackages();
		assertEquals(0, packages.size());
	}

	@Test
	public void listOfSpecificationsShouldIncludeThoseRelatedToGeneralBehaviour() {
		bdoc = new BDoc();
		bdoc.addBehaviourFrom(new TestClass(TestExampleNoStories.class), BConst.SRC_TEST_JAVA);
		assertFalse(bdoc.specifications().isEmpty());
	}

	@Test
	public void listOfSpecificationsShouldIncludeThoseRelatedToUserStories() {
		bdoc = new BDoc();
		bdoc.addBehaviourFrom(new TestClass(TestExampleWithStory.class), BConst.SRC_TEST_JAVA);
		assertFalse(bdoc.specifications().isEmpty());
	}

	@Test
	public void listOfScenariosShouldIncludeThoseRelatedToGeneralBehaviour() {
		bdoc = new BDoc();
		bdoc.addBehaviourFrom(new TestClass(TestExampleNoStories.class), BConst.SRC_TEST_JAVA);
		assertFalse(bdoc.scenarios().isEmpty());
	}

	@Test
	public void listOfScenariosShouldIncludeThoseRelatedToUserStories() {
		bdoc = new BDoc();
		bdoc.addBehaviourFrom(new TestClass(TestExampleWithStory.class), BConst.SRC_TEST_JAVA);
		assertFalse(bdoc.scenarios().isEmpty());
	}

	@Test
	public void shouldBeAbleToUseRuntimeBehaviourFactoryToCreateScenarioInMethodBlock() {
		BDoc bdocWithScenario = new BDoc();
		bdocWithScenario.addBehaviourFrom(new TestClass(TestDomainBehaviour.class), new RuntimeBehaviourFactory(BConst.SRC_TEST_JAVA));
		assertFalse(bdocWithScenario.scenarios().isEmpty());
	}

	@Test
	public void shouldIncludeTestTablesWhenTheRuntimeBehaviourFactoryIsPluggedIn() {
		bdoc = new BDoc();
		bdoc.addBehaviourFrom(new TestClass(MyTestTablesBehaviour.class), new RuntimeBehaviourFactory(BConst.SRC_TEST_JAVA));
		ClassBehaviour classBehaviour = bdoc.classBehaviourInGeneralBehaviour(MyTestTablesBehaviour.class);
		assertFalse( classBehaviour.getTestTables().isEmpty() );
	}
	
	

	/**
	 * Testdata
	 */
	public class TestTestsAnnotatedWithTest {
		@com.googlecode.bdoc.doc.domain.testdata.Test
		public void shouldBePickedUpByBDoc() {
		}
	}

	/**
	 * Testdata
	 */
	public class TestExampleNoStories {

		@Test
		public void shouldVerifyTheImportantStuff() {
		}

		@Test
		public void givenSomethingWhenAnActionThenVerifyResult() {
		}
	}

	/**
	 * Testdata
	 */
	@ExReference(ExStory.STORY3)
	public class TestExampleWithStory {

		@Test
		public void shouldVerifyTheImportantStuff() {
		}

		@Test
		public void givenSomethingWhenAnActionThenVerifyResult() {
		}
	}

	/**
	 * Testdata
	 */
	public class TestExampleJunit3 {
		public void testShouldShowThatJUnit3IsSupported() {
		}

		public void testGivenAJunit3TestWhenBddDocIsRunThenEnsureTheJUnit3TestIsExtracted() {
		}

		/**
		 * Just a short public methods to check for trouble
		 */
		public void x() {
		}

	}

	/**
	 * Testdata
	 */
	@ExReference(ExStory.STORY3)
	public class TestExampleAnnotatedClass {

		public void shouldNotBeExtracted() {
		}

		@Test
		public void givenAScenarioForStory3WhenThen() {
		}
	}

	/**
	 * Testdata
	 */
	public class TestExampleAnnotatedScenariosAndSpecifications {

		@Test
		@ExReference(ExStory.STORY1)
		public void givenWhenThen() {
		}

		@Test
		public void givenWhenThenWithoutRefToScenario() {
		}

		@Test
		@ExReference(ExStory.STORY1)
		public void shouldBehaveLikeThat() {
		}

		@Test
		@ExReference(ExStory.STORY2)
		public void shouldBehaveLikeThisIfThat() {
		}

		@Test
		public void shouldNotBeAnnotatedWithAStory() {
		}

	}

	/**
	 * Testdata
	 */
	public class TestExampleIgnoreMethods {

		@Test
		public void shouldBeAnnotatedWithTest() {
		}

		@Ignore
		public void shouldBeAnnotatedWithIgnore() {
		}

		@Test
		@Ignore
		public void shouldBeAnnotatedWithTestAndIgnore() {
		}
	}

	/**
	 * Testdata
	 */
	@Ignore
	public class TestExampleIgnoreClass {

		@Test
		public void shouldBeAnnotatedWithTest() {
		}
	}
}
