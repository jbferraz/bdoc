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

package com.googlecode.bdoc.doc.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.Package;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.doc.domain.TestBDoc.TestExampleAnnotatedMethods;
import com.googlecode.bdoc.doc.testdata.ExStory;


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
		userStory.addBehaviour(TestExampleAnnotatedMethods.class, SHOULD_DO_THAT);
		assertTrue(userStory.getClassSpecifications().get(0).getSpecifications().contains(new Specification(SHOULD_DO_THAT)));
	}

	@Test
	public void shouldBeAbleToAddAScenarioAndRetreiveIt() {
		userStory.addBehaviour(TestExampleAnnotatedMethods.class, GIVEN_WHEN_THEN);
		assertTrue(userStory.getScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldBeAbleToAddAStatementAndRetreiveIt() {
		userStory.addBehaviour(TestExampleAnnotatedMethods.class, STATMENT_ABOUT_IT);
		assertTrue(userStory.getClassStatements().get(0).getStatements().contains(new Statement(STATMENT_ABOUT_IT)));
	}

	@Test
	public void givenAUserStoryWhenNoBehaviourIsAddedThenTheListOfPackagesShouldBeEmpty() {
		assertTrue(userStory.getPackages().isEmpty());
	}

	@Test
	public void givenAUserStoryWhenBehaviourIsAddedFromATestClassThenThePackageOfTheTestClassShouldBePresent() {
		userStory.addBehaviour(TestExampleAnnotatedMethods.class, SHOULD_DO_THAT);
		Package packageToFind = Package.forClass(TestExampleAnnotatedMethods.class);
		assertTrue(userStory.getPackages().contains(packageToFind));
	}

	@Test
	public void givenAnEmptyUserStoryWhenASpecificationIsAddedThenEnsureThatClassSpecificationsContainsTheSpecification() {
		userStory.addBehaviour(TestExampleAnnotatedMethods.class, SHOULD_DO_THAT);
		assertTrue(userStory.getClassSpecifications().get(0).getSpecifications().contains(new Specification(SHOULD_DO_THAT)));
	}

}
