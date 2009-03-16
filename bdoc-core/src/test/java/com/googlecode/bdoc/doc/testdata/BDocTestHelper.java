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

package com.googlecode.bdoc.doc.testdata;

import integrationtestclasses.stack.StackBehavior;


import org.junit.Ignore;
import org.junit.Test;

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestClass;

/**
 * Class with static helper methods for generating testdata.
 * 
 * @author Per Otto Bergum Christensen
 */
public class BDocTestHelper {

	private BDocTestHelper() {
	}

	public static BDoc bdocWithTwoStoriesThreeScenariosFourSpecificationsAndGeneralBehaviour() {
		BDoc bdoc = bdocWithProject();
		bdoc.addBehaviourFrom(new TestClass(TestClassWithThreeScenariosThreeSpecificationsAndThreeStatements.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestClassWithFourSpecifications.class), BConst.SRC_TEST_JAVA);
		bdoc.addBehaviourFrom(new TestClass(TestClassWithGeneralBehaviour.class), BConst.SRC_TEST_JAVA);

		return bdoc;
	}

	public static BDoc bdocWithOneSpecification() {
		BDoc bdoc = bdocWithProject();
		bdoc.addBehaviourFrom(new TestClass(TestClassWithOneSpecification.class), BConst.SRC_TEST_JAVA);
		return bdoc;
	}

	public static BDoc bdocWithOneScenario() {
		BDoc bdoc = bdocWithProject();
		bdoc.addBehaviourFrom(new TestClass(TestClassWithOneScenario.class), BConst.SRC_TEST_JAVA);
		return bdoc;
	}

	public static BDoc bdocWithProject() {
		BDoc bdoc = new BDoc(Test.class, ExReference.class, Ignore.class);
		bdoc.setProject(testProject());
		return bdoc;
	}

	public static ProjectInfo testProject() {
		return new ProjectInfo("Testproject", "1.0");
	}

	public static BDoc bdocWithAdvancedScenarioSpecification() {
		BDoc bddDoc = bdocWithProject();
		bddDoc.addBehaviourFrom(new TestClass(StackBehavior.class), BConst.SRC_TEST_JAVA);

		return bddDoc;
	}

	@ExReference(ExStory.STORY1)
	public class TestClassWithThreeScenariosThreeSpecificationsAndThreeStatements {

		@Test
		public void givenWhenThen1() {
		}

		@Test
		public void givenWhenThen2() {
		}

		@Test
		public void givenWhenThen3() {
		}

		@Test
		public void statementAboutSomething1() {
		}

		@Test
		public void statementAboutSomething2() {
		}

		@Test
		public void statementAboutSomething3() {
		}

		@Test
		public void shouldDoThatAndThat() {
		}

		@Test
		public void shouldNeverDoThat() {
		}

		@Test
		public void shouldAlsoDoThat() {
		}
	}

	public class TestClassWithOneSpecification {

		@Test
		public void shouldBeASpecification() {

		}
	}

	public class TestClassWithOneScenario {

		@Test
		public void givenWhenThen() {

		}
	}

	public static Scenario addScenario(BDoc bdoc) {
		bdoc.addBehaviourFrom(new TestClass(TestClassWithOneScenario.class), BConst.SRC_TEST_JAVA);
		ClassBehaviour classBehaviour = bdoc.classBehaviourInGeneralBehaviour(TestClassWithOneScenario.class);
		return classBehaviour.getScenarios().get(0);
	}

}
