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
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.doc.domain.testdata.TestWithTwoSpecsWithOneScenarioEachBehaviour;
import com.googlecode.bdoc.doc.testdata.ExReference;
import com.googlecode.bdoc.doc.tinybdd.TinyBddAnalyzer;

public class TestBDocWithTinyBddAnalyzer {

	@Test
	public void shouldAddExampleForEachSpecificationThatContainsExamplesWithUseOfTinyBddAnalyzer() {
		BDoc bdoc = new BDoc(ExReference.class, new ClassBehaviourSorter());
		bdoc.addBehaviourFrom(new TestClass( TestWithTwoSpecsWithOneScenarioEachBehaviour.class ) , new TinyBddAnalyzer(BConst.SRC_TEST_JAVA) );
		
		ClassBehaviour classBehaviour = bdoc.classBehaviourInModuleBehaviour(TestWithTwoSpecsWithOneScenarioEachBehaviour.class);
		assertNotNull( classBehaviour );
		Statement spec1= classBehaviour.getStatements().get(0);
		Statement spec2= classBehaviour.getStatements().get(1);

		assertEquals( Scenario.parts( "given stateA","when action","then ensure"), spec1.getScenarios().get(0).getParts() );
		assertEquals( Scenario.parts( "given stateB","when action","then ensure"), spec2.getScenarios().get(0).getParts() );		
	}	
}
