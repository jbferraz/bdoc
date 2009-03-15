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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.testdata.RefClass;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.ADVANCED_SCENARIO_SPECIFICATION)
@RefClass(BDoc.class)
public class TestBDocBehaviour {

	private BDoc givenAnEmptyBDoc() {
		return new BDoc();
	}

	private ClassBehaviour whenABehaviourTestClassIsAddedWithAScenarioDescribedInATestMethodBlock(BDoc doc) {
		doc.addBehaviourFrom(new TestClass(TestBDocBehaviour.class), BConst.SRC_TEST_JAVA);
		return doc.classBehaviourInGeneralBehaviour(TestBDocBehaviour.class);
	}

	private void thenEnsureThatTheScenarioIsExtracted(ClassBehaviour behaviour) {

		List<Scenario.Part> expectedScenarioParts = new ArrayList<Scenario.Part>();
		expectedScenarioParts.add(new Scenario.Part("givenAnEmptyBDoc"));
		expectedScenarioParts.add(new Scenario.Part("whenABehaviourTestClassIsAddedWithAScenarioDescribedInATestMethodBlockDoc"));
		expectedScenarioParts.add(new Scenario.Part("thenEnsureThatTheScenarioIsExtractedBehaviour"));

		assertTrue(behaviour.getScenarios().contains(new Scenario(expectedScenarioParts)));
	}

	@Test
	public void shouldAddScenariosSpecifiedInATestMethodBlock() {
		BDoc doc = givenAnEmptyBDoc();
		ClassBehaviour behaviour = whenABehaviourTestClassIsAddedWithAScenarioDescribedInATestMethodBlock(doc);
		thenEnsureThatTheScenarioIsExtracted(behaviour);
	}
}
