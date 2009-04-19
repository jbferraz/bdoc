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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.GeneralBehaviourDiff;
import com.googlecode.bdoc.doc.domain.GeneralBehaviour;
import com.googlecode.bdoc.doc.domain.Package;


/**
 *  @author Per Otto Bergum Christensen
 */
@Ref(Story.DIFF_OF_BDOCS)
public class TestGeneralBehaviourDiff {

	private static final String SHOULD_JUST_BEHAVE = "shouldJustBehave";
	private static final String SHOULD_BEHAVE = "shouldBehave";
	private static final String GIVEN_TWO_WHEN_THEN = "givenTwoWhenThen";
	private static final String GIVEN_WHEN_THEN = "givenWhenThen";
	private final GeneralBehaviour oldVersion = new GeneralBehaviour();
	private final GeneralBehaviour newVersion = new GeneralBehaviour();
	{
		newVersion.addBehaviour(TestAsTestdata.class, SHOULD_BEHAVE);
	}

	private GeneralBehaviour emptyGeneralBehaviour = new GeneralBehaviour();
	private GeneralBehaviour generalBehaviourWithBehaviour = new GeneralBehaviour();
	{
		generalBehaviourWithBehaviour.addBehaviour(TestAsTestdata.class, SHOULD_BEHAVE);
		generalBehaviourWithBehaviour.addBehaviour(TestAsTestdata.class, GIVEN_WHEN_THEN);
	}

	private GeneralBehaviour generalBehaviourWithDeletedAndNewBehaviour = new GeneralBehaviour();
	{
		generalBehaviourWithDeletedAndNewBehaviour.addBehaviour(TestAsTestdata.class, SHOULD_JUST_BEHAVE);
		generalBehaviourWithDeletedAndNewBehaviour.addBehaviour(TestAsTestdata.class, "givenJustWhenThen");
	}

	private GeneralBehaviour generalBehaviourWithBehaviourUpdated = new GeneralBehaviour();
	{
		generalBehaviourWithBehaviourUpdated.addBehaviour(TestAsTestdata.class, SHOULD_BEHAVE);
		generalBehaviourWithBehaviourUpdated.addBehaviour(TestAsTestdata.class, "shouldBehaveNewOne");
		generalBehaviourWithBehaviourUpdated.addBehaviour(TestAsTestdata.class, GIVEN_WHEN_THEN);
		generalBehaviourWithBehaviourUpdated.addBehaviour(TestAsTestdata.class, GIVEN_TWO_WHEN_THEN);
	}

	private GeneralBehaviour generalBehaviourWithUpdatedBehaviour = new GeneralBehaviour();
	{
		generalBehaviourWithUpdatedBehaviour.addBehaviour(TestAsTestdata.class, "shouldBehaveLikeThatAsWell");
	}

	@Test
	public void shouldTellIfThereAreNewPackages() {
		GeneralBehaviourDiff diff = new GeneralBehaviourDiff(emptyGeneralBehaviour, generalBehaviourWithBehaviour);
		assertTrue(diff.hasNewPackages());
		assertEquals(Package.forClass(TestAsTestdata.class), diff.getNewPackages().get(0));
	}

	@Test
	public void shouldTellIfThereAreDeletedPackages() {
		GeneralBehaviourDiff diff = new GeneralBehaviourDiff(generalBehaviourWithBehaviour, emptyGeneralBehaviour);
		assertTrue(diff.hasDeletedPackages());
		assertEquals(Package.forClass(TestAsTestdata.class), diff.getDeletedPackages().get(0));
	}

	@Test
	public void shouldTellIfThereAreUpdatedPackages() {
		GeneralBehaviourDiff diff = new GeneralBehaviourDiff(generalBehaviourWithBehaviour, generalBehaviourWithUpdatedBehaviour);
		assertTrue(diff.hasUpdatedPackages());
	}

	@Test
	public void shouldReportDiffForChangedGeneralBehaviour() {
		assertTrue(new GeneralBehaviourDiff(oldVersion, newVersion).diffExists());
	}

	@Test
	public void shouldNotReportDiffForUnchangedGeneralBehaviour() {
		assertFalse(new GeneralBehaviourDiff(newVersion, newVersion).diffExists());
	}

	public class TestAsTestdata {
	}
}
