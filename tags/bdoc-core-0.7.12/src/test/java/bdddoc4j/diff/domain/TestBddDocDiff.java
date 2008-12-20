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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.domain.BDoc;
import bdddoc4j.core.domain.Project;
import bdddoc4j.core.domain.TestClass;
import bdddoc4j.core.testdata.BddDocTestHelper;
import bdddoc4j.core.testdata.ExReference;
import bdddoc4j.core.testdata.ExStory;
import bdddoc4j.diff.domain.BddUserStoryDiffDocTestdataHelper.TestWithOnlyGeneralBehaviourContent;

@Ref(Story.DIFF_OF_BDOCS)
public class TestBddDocDiff {

	private final BDoc emptyBddDoc = BddUserStoryDiffDocTestdataHelper.getEmptyBddDoc();
	private final BDoc bddDocWithGeneralBehaviourAndAStory = BddUserStoryDiffDocTestdataHelper.getBddDocWithGeneralBehaviourAndAStory();
	private final BDoc bddDocWithUpdatedStory = BddUserStoryDiffDocTestdataHelper.getBddDocWithUpdatedStory();

	@Test
	public void shouldIdentifyNewStories() {
		assertFalse(new BddDocDiff(emptyBddDoc, bddDocWithGeneralBehaviourAndAStory).getNewStories().isEmpty());
	}

	@Test
	public void shouldIdentifyDeletedStories() {
		assertFalse(new BddDocDiff(bddDocWithGeneralBehaviourAndAStory, emptyBddDoc).getDeletedStories().isEmpty());
	}

	@Test
	public void shouldIdentifyUpdatedStories() {
		BddDocDiff diff = new BddDocDiff(bddDocWithGeneralBehaviourAndAStory, bddDocWithUpdatedStory);
		assertEquals(ExStory.STORY1.getId(), diff.getUpdatedStories().get(0).getUserStoryId());
	}

	@Test
	public void shouldNotListStoriesAsUpdatedWhereThereIsNoDiff() {
		BddDocDiff diff = new BddDocDiff(bddDocWithGeneralBehaviourAndAStory, bddDocWithGeneralBehaviourAndAStory);		
		assertTrue(diff.getUpdatedStories().isEmpty());
	}

	@Test
	public void shouldReportDiffForChangedGeneralBehaviour() {
		BDoc bddDocV1 = new BDoc(org.junit.Test.class, ExReference.class);
		bddDocV1.setProject(new Project("name", "1"));

		BDoc bddDocV2 = new BDoc(org.junit.Test.class, ExReference.class);
		bddDocV2.setProject(new Project("name", "1"));

		bddDocV2.addBehaviourFrom(new TestClass(TestWithOnlyGeneralBehaviourContent.class), BddDocTestHelper.SRC_TEST_JAVA);

		assertTrue(new BddDocDiff(bddDocV1, bddDocV2).getGeneralBehaviourDiff().diffExists());
	}

	@Test
	public void shouldNotReportDiffForUnchangedGeneralBehaviour() {
		BddDocDiff bddDocDiff = new BddDocDiff(bddDocWithGeneralBehaviourAndAStory, bddDocWithGeneralBehaviourAndAStory);
		assertFalse(bddDocDiff.getGeneralBehaviourDiff().diffExists());
	}

	@Test
	public void shouldReportDiffForProjectInfo() {
		assertTrue(new BddDocDiff(bddDocWithGeneralBehaviourAndAStory, bddDocWithUpdatedStory).getProjectDiff().diffExists());
	}

}
