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

package bdddoc4j.core.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.testdata.RefClass;

@Ref(Story.CREATE_BDOC_FROM_CODE)
public class TestClassBehaviour {

	private ClassBehaviour classBehaviour;

	@Before
	public void resetGlobalTestdata() {
		classBehaviour = new ClassBehaviour(TestSomeClass.class);
	}

	@Test
	public void givenThatATestClassIsAnnotatedWithRefClassWhenTheClassNameIsExtractedThenEnsureTheClassNameIsAsSpecifiedByTheClassRefAnnotation() {
		classBehaviour = new ClassBehaviour(TestClassThatHasAnnotationToAnotherClass.class);
		assertEquals( "AnotherClass",  classBehaviour.getClassName() );
	}

	@Test
	public void shouldTranslateTestClassNamesWithPrefixTestToTheNameOfTheClassUnderTestAndRemovePackage() {
		assertEquals("SomeClass", classBehaviour.getClassName());
	}

	@Test
	public void shouldNotTranslateTestClassNamesThatDoesNotStartWithTest() {
		assertEquals("AnotherClass", new ClassBehaviour(AnotherClass.class).getClassName());
		assertEquals("Ref", new ClassBehaviour(Ref.class).getClassName());
	}

	@Test
	public void shouldRecognizeBehaviourStartingWithShouldAsASpecification() {
		String spec = "shouldBeASpecification";
		classBehaviour.addBehaviour(spec);
		assertTrue(classBehaviour.getSpecifications().contains(new Specification(spec)));
	}

	@Test
	public void shouldRecognizeBehaviourStartingWithGivenAsAScenario() {
		String scenario = "givenShouldBeTheStartOfASCenarioWhenExtractedThenTransformed";
		classBehaviour.addBehaviour(scenario);
		assertTrue(classBehaviour.getScenarios().contains(new Scenario(scenario)));
	}

	@Test
	public void shouldRecognizeBehaviourAsAStatmentIfItIsNotAScenarioOrASpecification() {

		String statement = "statmentIsMoreGeneralThanSpecificationAndScenario";
		classBehaviour.addBehaviour(statement);
		assertTrue(classBehaviour.getStatements().contains(new Statement(statement)));
	}

	@Test
	public void shouldReturnEmptyListOfSpecificationsForANewClassBehviour() {
		assertTrue(new ClassBehaviour(TestSomeClass.class).getSpecifications().isEmpty());
	}

	public class TestSomeClass {
	}

	@RefClass(AnotherClass.class)
	public class TestClassThatHasAnnotationToAnotherClass {
	}

	public class AnotherClass {
	}
}
