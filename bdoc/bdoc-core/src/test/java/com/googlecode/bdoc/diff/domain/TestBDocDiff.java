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

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.BDocUserStoryDiffDocTestdataHelper.TestWithOnlyGeneralBehaviourContent;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviourSorter;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.testdata.ExReference;
import com.googlecode.bdoc.doc.testdata.ExStory;

/**
 *  @author Per Otto Bergum Christensen
 */
@Ref(Story.DIFF_OF_BDOCS)
public class TestBDocDiff {

	private final BDoc emptyBddDoc = BDocUserStoryDiffDocTestdataHelper.getEmptyBddDoc();
	private final BDoc bddDocWithGeneralBehaviourAndAStory = BDocUserStoryDiffDocTestdataHelper.getBddDocWithGeneralBehaviourAndAStory();
	private final BDoc bddDocWithUpdatedStory = BDocUserStoryDiffDocTestdataHelper.getBddDocWithUpdatedStory();

	@Test
	public void shouldIdentifyNewStories() {
		assertFalse(new BDocDiff(emptyBddDoc, bddDocWithGeneralBehaviourAndAStory).getNewStories().isEmpty());
	}

	@Test
	public void shouldIdentifyDeletedStories() {
		assertFalse(new BDocDiff(bddDocWithGeneralBehaviourAndAStory, emptyBddDoc).getDeletedStories().isEmpty());
	}

	@Test
	public void shouldIdentifyUpdatedStories() {
		BDocDiff diff = new BDocDiff(bddDocWithGeneralBehaviourAndAStory, bddDocWithUpdatedStory);
		assertEquals(ExStory.STORY1.getId(), diff.getUpdatedStories().get(0).getUserStoryId());
	}

	@Test
	public void shouldNotListStoriesAsUpdatedWhereThereIsNoDiff() {
		BDocDiff diff = new BDocDiff(bddDocWithGeneralBehaviourAndAStory, bddDocWithGeneralBehaviourAndAStory);
		assertTrue(diff.getUpdatedStories().isEmpty());
	}

	@Test
	public void shouldReportDiffForChangedGeneralBehaviour() {
		BDoc bddDocV1 = new BDoc(ExReference.class, new ClassBehaviourSorter());
		bddDocV1.setProject(new ProjectInfo("name", "1"));

		BDoc bddDocV2 = new BDoc(ExReference.class, new ClassBehaviourSorter());
		bddDocV2.setProject(new ProjectInfo("name", "1"));

		bddDocV2.addBehaviourFrom(new TestClass(TestWithOnlyGeneralBehaviourContent.class), BConst.SRC_TEST_JAVA);

		assertTrue(new BDocDiff(bddDocV1, bddDocV2).getModuleBehaviourDiff().diffExists());
	}

	@Test
	public void shouldNotReportDiffForUnchangedGeneralBehaviour() {
		BDocDiff bDocDiff = new BDocDiff(bddDocWithGeneralBehaviourAndAStory, bddDocWithGeneralBehaviourAndAStory);
		assertFalse(bDocDiff.getModuleBehaviourDiff().diffExists());
	}

	@Test
	public void shouldReportDiffForProjectInfo() {
		assertTrue(new BDocDiff(bddDocWithGeneralBehaviourAndAStory, bddDocWithUpdatedStory).getProjectDiff().diffExists());
	}

}
