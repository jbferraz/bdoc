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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.UserStoryDiff;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.doc.domain.UserStoryDescription;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.DIFF_OF_BDOCS)
public class TestUserStoryDiff {

	private static final String ANOTHER_STATEMENT = "anotherStatement";
	private static final String THE_STATEMENT = "theStatement";
	private static final String GIVEN_WHEN_THEN = "givenWhenThen";
	private static final String GIVEN_JUST_WHEN_THEN = "givenJustWhenThen";
	private static final String SHOULD_JUST_BEHAVE = "shouldJustBehave";
	private static final String SHOULD_BEHAVE = "shouldBehave";
	private static final String GIVEN_TWO_WHEN_THEN = "givenTwoWhenThen";

	private final UserStory emptyUserStory = new UserStory(TestStory.STORY1_OLD_VERSION);
	private UserStory updatedUserStory = new UserStory(TestStory.STORY1_NEW_VERSION);
	{
		updatedUserStory.addBehaviour(new TestMethod(TestAsTestdata.class, SHOULD_BEHAVE));
		updatedUserStory.addBehaviour(new TestMethod(TestAsTestdata.class, GIVEN_WHEN_THEN));
		updatedUserStory.addBehaviour(new TestMethod(TestAsTestdata.class, THE_STATEMENT));
	}

	private UserStory userStoryWithDeletedAndNewBehaviour = new UserStory(TestStory.STORY1_OLD_VERSION);
	{
		userStoryWithDeletedAndNewBehaviour.addBehaviour(new TestMethod(TestAsTestdata.class, SHOULD_JUST_BEHAVE));
		userStoryWithDeletedAndNewBehaviour.addBehaviour(new TestMethod(TestAsTestdata.class, ANOTHER_STATEMENT));
		userStoryWithDeletedAndNewBehaviour.addBehaviour(new TestMethod(TestAsTestdata.class, GIVEN_JUST_WHEN_THEN));
	}

	private UserStory userStoryWithEvenMoreUpdates = new UserStory(TestStory.STORY1_OLD_VERSION);
	{
		userStoryWithEvenMoreUpdates.addBehaviour(new TestMethod(TestAsTestdata.class, SHOULD_BEHAVE));
		userStoryWithEvenMoreUpdates.addBehaviour(new TestMethod(TestAsTestdata.class, "shouldBehaveNewOne"));
		userStoryWithEvenMoreUpdates.addBehaviour(new TestMethod(TestAsTestdata.class, GIVEN_WHEN_THEN));
		userStoryWithEvenMoreUpdates.addBehaviour(new TestMethod(TestAsTestdata.class, GIVEN_TWO_WHEN_THEN));
	}

	private UserStory userStoryWithUpdatedBehaviour = new UserStory(TestStory.STORY1_OLD_VERSION);
	{
		userStoryWithUpdatedBehaviour.addBehaviour(new TestMethod(TestAsTestdata.class, "shouldBehaveLikeThatAsWell"));
	}

	UserStoryDiff diff = new UserStoryDiff(emptyUserStory, updatedUserStory);

	@Test
	public void shouldReportDiffForChangedNarrative() {
		assertTrue(diff.hasNarrativeDiff());
	}

	@Test
	public void shouldReportDiffForChangedTitle() {
		assertTrue(diff.hasTitleDiff());
	}

	@Test
	public void shouldNotReportDiffForUnchangedTitle() {
		UserStoryDiff nodiff = new UserStoryDiff(emptyUserStory, emptyUserStory);
		assertFalse(nodiff.getTitleDiff().diffExists());
	}

	@Test
	public void shouldTellIfThereAreNewScenarios() {
		assertTrue(diff.hasNewScenarios());
	}

	@Test
	public void shouldReturnScenariosAsNewWhenTheyArePartOfANewClassBehaviour() {
		UserStoryDiff userStoryDiff = new UserStoryDiff(emptyUserStory, updatedUserStory);
		assertTrue(userStoryDiff.hasNewScenarios());
		assertTrue(userStoryDiff.getNewScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldReturnScenariosNewWhenTheyAreNewInAnExistingClassBehaviour() {
		UserStoryDiff userStoryDiff = new UserStoryDiff(updatedUserStory, userStoryWithDeletedAndNewBehaviour);
		assertTrue(userStoryDiff.hasNewScenarios());
		assertTrue(userStoryDiff.getNewScenarios().contains(new Scenario(GIVEN_JUST_WHEN_THEN)));
	}

	@Test
	public void shouldReturnScenariosAsDeletedWhenFoundInDeletedPackages() {
		UserStoryDiff userStoryDiff = new UserStoryDiff(updatedUserStory, emptyUserStory);
		assertTrue(userStoryDiff.hasDeletedScenarios());
		assertTrue(userStoryDiff.getDeletedScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldReturnScenariosAsDeletedWhenReturnedAsDeletedFromThePackageDiff() {
		UserStoryDiff userStoryDiff = new UserStoryDiff(updatedUserStory, userStoryWithDeletedAndNewBehaviour);
		assertTrue(userStoryDiff.getDeletedScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldReturnSpecificationsAsNewWhenFoundInNewPackages() {
		assertTrue(new UserStoryDiff(emptyUserStory, updatedUserStory).getNewClassSpecifications().get(0).getSpecifications()
				.contains(new Specification(SHOULD_BEHAVE)));
	}

	@Test
	public void shouldReturnClassSpecificationsAsNewWhenReturnedAsNewFromThePackageDiff() {
		assertTrue(new UserStoryDiff(updatedUserStory, userStoryWithDeletedAndNewBehaviour).getNewClassSpecifications().get(0)
				.getSpecifications().contains(new Specification(SHOULD_JUST_BEHAVE)));
	}

	@Test
	public void shouldReturnClassSpecificationsAsDeletedWhenFoundInDeletedPackages() {
		assertTrue(new UserStoryDiff(updatedUserStory, emptyUserStory).getDeletedClassSpecifications().get(0).getSpecifications()
				.contains(new Specification(SHOULD_BEHAVE)));
	}

	@Test
	public void shouldReturnClassSpecificationsAsDeletedWhenReturnedAsDeletedFromThePackageDiff() {
		assertTrue(new UserStoryDiff(updatedUserStory, userStoryWithDeletedAndNewBehaviour).getDeletedClassSpecifications().get(0)
				.getSpecifications().contains(new Specification(SHOULD_BEHAVE)));
	}

	@Test
	public void shouldReturnStatementsAsNewWhenFoundInNewPackages() {
		assertTrue(new UserStoryDiff(emptyUserStory, updatedUserStory).getNewClassStatements().get(0).getStatements().contains(
				new Statement(THE_STATEMENT)));
	}

	@Test
	public void shouldReturnStatementsAsNewWhenReturnedAsNewFromThePackageDiff() {
		assertTrue(new UserStoryDiff(updatedUserStory, userStoryWithDeletedAndNewBehaviour).getNewClassStatements().get(0)
				.getStatements().contains(new Statement(ANOTHER_STATEMENT)));
	}

	@Test
	public void shouldReturnClassStatementsAsDeletedWhenFoundInDeletedPackages() {
		UserStoryDiff userStoryDiff = new UserStoryDiff(updatedUserStory, emptyUserStory);
		assertTrue(userStoryDiff.hasDeletedClassStatements());
		assertTrue(userStoryDiff.getDeletedClassStatements().get(0).getStatements().contains(new Statement(THE_STATEMENT)));
	}

	@Test
	public void shouldReturnClassStatementsAsDeletedWhenReturnedAsDeletedFromThePackageDiff() {
		assertTrue(new UserStoryDiff(updatedUserStory, userStoryWithDeletedAndNewBehaviour).getDeletedClassStatements().get(0)
				.getStatements().contains(new Statement(THE_STATEMENT)));
	}

	public class TestAsTestdata {
		
		@Test
		public void shouldBehave() {
		}

		@Test
		public void givenWhenThen() {
		}

		@Test
		public void theStatement() {
		}
		
		@Test
		public void shouldJustBehave() {
		}
		
		@Test
		public void anotherStatement() {
		}
		
		@Test
		public void givenJustWhenThen() {
		}
		
		@Test
		public void shouldBehaveNewOne() {
		}
		
		@Test
		public void givenTwoWhenThen() {}
		
		@Test
		public void shouldBehaveLikeThatAsWell() {}
	}

	public enum TestStory implements UserStoryDescription {

		STORY1_OLD_VERSION(1, "As a", "I want", "so that"), STORY1_NEW_VERSION(1, "As a user", "I want", "so that");

		private Integer id;
		private Narrative narrative;

		TestStory(Integer id, String role, String action, String benefit) {
			this.id = id;
			this.narrative = new Narrative(role, action, benefit);
		}

		public Integer getId() {
			return id;
		}

		public String getTitle() {
			return name();
		}

		public Narrative getNarrative() {
			return narrative;
		}
	}

}
