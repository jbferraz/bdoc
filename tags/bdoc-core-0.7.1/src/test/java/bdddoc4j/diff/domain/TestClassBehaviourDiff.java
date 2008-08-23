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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.domain.ClassBehaviour;
import bdddoc4j.core.domain.Scenario;
import bdddoc4j.core.domain.Specification;
import bdddoc4j.core.domain.Statement;

@Ref(Story.DIFF_OF_BDDDOCS)
public class TestClassBehaviourDiff {

	private static final String GIVE_A_STATEMENT = "giveAStatement";

	private static final String SHOULD_BEHAVE_LIKE_THAT = "shouldBehaveLikeThat";

	private static final String GIVEN_WHEN_THEN = "givenWhenThen";

	private final ClassBehaviour emptyClassBehaviour = new ClassBehaviour(TestAsTestdata.class);

	private final ClassBehaviour nonEmptyClassBehaviour = new ClassBehaviour(TestAsTestdata.class);
	{
		nonEmptyClassBehaviour.addBehaviour(GIVEN_WHEN_THEN);
		nonEmptyClassBehaviour.addBehaviour(SHOULD_BEHAVE_LIKE_THAT);
		nonEmptyClassBehaviour.addBehaviour(GIVE_A_STATEMENT);
	}

	@Test
	public void shouldReturnNewScenarios() {
		ClassBehaviourDiff diff = new ClassBehaviourDiff(emptyClassBehaviour, nonEmptyClassBehaviour);
		assertTrue(diff.getNewScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldReturnDeletedScenarios() {
		ClassBehaviourDiff diff = new ClassBehaviourDiff(nonEmptyClassBehaviour, emptyClassBehaviour);
		assertTrue(diff.getDeletedScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldReturnNewSpecifications() {
		ClassBehaviourDiff diff = new ClassBehaviourDiff(emptyClassBehaviour, nonEmptyClassBehaviour);
		assertTrue(diff.getNewSpecifications().contains(new Specification(SHOULD_BEHAVE_LIKE_THAT)));
	}

	@Test
	public void shouldReturnDeletedSpecifications() {
		ClassBehaviourDiff diff = new ClassBehaviourDiff(nonEmptyClassBehaviour, emptyClassBehaviour);
		assertTrue(diff.getDeletedSpecifications().contains(new Specification(SHOULD_BEHAVE_LIKE_THAT)));
	}

	@Test
	public void shouldReturnNewStatements() {
		ClassBehaviourDiff diff = new ClassBehaviourDiff(emptyClassBehaviour, nonEmptyClassBehaviour);
		assertTrue(diff.getNewStatements().contains(new Statement(GIVE_A_STATEMENT)));
	}

	@Test
	public void shouldReturnDeletedStatements() {
		ClassBehaviourDiff diff = new ClassBehaviourDiff(nonEmptyClassBehaviour, emptyClassBehaviour);
		assertTrue(diff.getDeletedStatements().contains(new Statement(GIVE_A_STATEMENT)));
	}

	public class TestAsTestdata {
	}
}
