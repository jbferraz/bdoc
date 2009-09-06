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
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.TestBDoc.TestExampleAnnotatedScenariosAndSpecifications;
import com.googlecode.bdoc.doc.domain.testdata.packagex.ClassX;
import com.googlecode.bdoc.doc.domain.testdata.packagex.ClassXTest;
import com.googlecode.bdoc.doc.domain.testdata.packagey.ClassY;
import com.googlecode.bdoc.doc.domain.testdata.packagey.ClassYTest;
import com.googlecode.bdoc.doc.testdata.ExStory;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.CREATE_BDOC_FROM_CODE)
public class TestUserStory {

	private static final String STATMENT_ABOUT_IT = "statmentAboutIt";
	private static final String GIVEN_WHEN_THEN = "givenWhenThen";
	private static final String SHOULD_DO_THAT = "shouldDoThat";

	private UserStory userStory;

	@Before
	public void resetGlobalTestData() {
		userStory = new UserStory(ExStory.STORY1);
	}

	@Test
	public void shouldBeAbleToAddASpecificationAndRetreiveIt() {
		userStory.addBehaviour(new TestMethod(TestExampleAnnotatedScenariosAndSpecifications.class, SHOULD_DO_THAT));
		assertTrue(userStory.getClassSpecifications().get(0).getSpecifications().contains(new Specification(SHOULD_DO_THAT)));
	}

	@Test
	public void shouldBeAbleToAddAScenarioAndRetreiveIt() {
		userStory.addBehaviour(new TestMethod(TestExampleAnnotatedScenariosAndSpecifications.class, GIVEN_WHEN_THEN));
		assertTrue(userStory.getScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldBeAbleToAddAStatementAndRetreiveIt() {
		userStory.addBehaviour(new TestMethod(TestExampleAnnotatedScenariosAndSpecifications.class, STATMENT_ABOUT_IT));
		assertTrue(userStory.getClassStatements().get(0).getStatements().contains(new Statement(STATMENT_ABOUT_IT)));
	}

	@Test
	public void givenAUserStoryWhenNoBehaviourIsAddedThenTheListOfPackagesShouldBeEmpty() {
		assertTrue(userStory.getPackages().isEmpty());
	}

	@Test
	public void givenAUserStoryWhenBehaviourIsAddedFromATestClassThenThePackageOfTheTestClassShouldBePresent() {
		userStory.addBehaviour(new TestMethod(TestExampleAnnotatedScenariosAndSpecifications.class, SHOULD_DO_THAT));
		Package packageToFind = Package.forClass(TestExampleAnnotatedScenariosAndSpecifications.class);
		assertTrue(userStory.getPackages().contains(packageToFind));
	}

	@Test
	public void givenAnEmptyUserStoryWhenASpecificationIsAddedThenEnsureThatClassSpecificationsContainsTheSpecification() {
		userStory.addBehaviour(new TestMethod(TestExampleAnnotatedScenariosAndSpecifications.class, SHOULD_DO_THAT));
		assertTrue(userStory.getClassSpecifications().get(0).getSpecifications().contains(new Specification(SHOULD_DO_THAT)));
	}

	@Test
	public void shouldReturnClassBehaviourForAllClassesAddedToTheUserStory() {

		userStory.addBehaviour(new TestMethod(ClassXTest.class, "shouldBeTestX"));
		userStory.addBehaviour(new TestMethod(ClassYTest.class, "shouldBeTestY"));

		List<ClassBehaviour> classBehaviour = userStory.getClassBehaviour();

		assertEquals(2, classBehaviour.size());
		assertTrue(classBehaviour.contains(new ClassBehaviour(ClassXTest.class)));
		assertTrue(classBehaviour.contains(new ClassBehaviour(ClassYTest.class)));
	}

	@Test
	public void shouldSortClassBehaviourAfterSpecifiedOrder() {
		Class<?>[] classBehaviourOrder = { ClassY.class, ClassX.class, LastClass.class};
		
		userStory = new UserStory(ExStory.STORY1, new ClassBehaviourSorter( classBehaviourOrder ) );
		userStory.addBehaviour(new TestMethod(LastClassTest.class, "lastStatement"));
		userStory.addBehaviour(new TestMethod(ClassYTest.class, "shouldBeTestY"));
		userStory.addBehaviour(new TestMethod(ClassXTest.class, "shouldBeTestX"));
		
		assertEquals( "ClassY", userStory.getClassBehaviour().get(0).getClassName() );
		assertEquals( "ClassX", userStory.getClassBehaviour().get(1).getClassName() );
		assertEquals( "LastClass", userStory.getClassBehaviour().get(2).getClassName() );
	}

	public class LastClass {
	}

	public class LastClassTest {

		@Test
		public void lastStatement() {

		}
	}

}
