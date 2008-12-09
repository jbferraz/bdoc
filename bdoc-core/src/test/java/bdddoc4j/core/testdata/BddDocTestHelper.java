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

package bdddoc4j.core.testdata;

import java.io.File;

import org.junit.Test;

import bdddoc4j.core.domain.BDoc;
import bdddoc4j.core.domain.Project;
import bdddoc4j.core.domain.TestClass;

/**
 * Class with static helper methods for generating testdata.
 * 
 * @author Per Otto Bergum Christensen
 */
public class BddDocTestHelper {
	
	public static final File SRC_TEST_JAVA = new File("src/test/java");

	private BddDocTestHelper() {
	}

	public static BDoc bddDocWithTwoStoriesThreeScenariosFourSpecificationsAndGeneralBehaviour() {
		BDoc bddDoc = new BDoc(org.junit.Test.class, ExReference.class);
		bddDoc.setProject(new Project("Testproject", "1.0"));
		bddDoc.addBehaviourFrom(new TestClass(TestClassWithThreeScenariosThreeSpecificationsAndThreeStatements.class), SRC_TEST_JAVA );
		bddDoc.addBehaviourFrom(new TestClass(TestClassWithFourSpecifications.class), SRC_TEST_JAVA );
		bddDoc.addBehaviourFrom(new TestClass(TestClassWithGeneralBehaviour.class), SRC_TEST_JAVA );

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

}
