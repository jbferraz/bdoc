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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.doc.domain.TestModuleBehaviour.TestExample;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestPackage {

	private static final String ITS_ALL_ABOUT_BEHAVIOUR = "itsAllAboutBehaviour";
	private static final String SHOULD_VERIFY_THE_IMPORTANT_STUFF = "shouldVerifyTheImportantStuff";
	private static final String GIVEN_WHEN_THEN = "givenWhenThen";

	private Package javaPackage;

	@Before
	public void resetGlobalTestdata() {
		javaPackage = Package.forClass(TestExample.class);
	}

	@Test
	public void shouldNotSpecifyAnySpecificationsForPackageWithoutBehaviourAdded() {
		assertFalse(javaPackage.hasClassSpecifications());
	}

	@Test
	public void shouldNotSpecifyAnyScenariosForPackageWithoutBehaviourAdded() {
		assertFalse(javaPackage.hasScenarios());
	}

	@Test
	public void shouldReturnSpecificationsAddedToThePackage() {
		javaPackage.addBehaviour(new TestMethod(TestExample.class, SHOULD_VERIFY_THE_IMPORTANT_STUFF));
		assertTrue(javaPackage.getClassSpecifications().get(0).getSpecifications().contains(
				new Specification(SHOULD_VERIFY_THE_IMPORTANT_STUFF)));
	}

	@Test
	public void shouldReturnStatementsAddedToThePackage() {
		javaPackage.addBehaviour(new TestMethod(TestExample.class, ITS_ALL_ABOUT_BEHAVIOUR));
		assertTrue(javaPackage.hasClassStatements());
		assertTrue(javaPackage.getClassStatements().get(0).getStatements().contains(new Statement(ITS_ALL_ABOUT_BEHAVIOUR)));
	}

	@Test
	public void shouldReturnScenariosAddedToThePackage() {
		Package javaPackage = Package.forClass(TestExample.class);
		javaPackage.addBehaviour(new TestMethod(TestExample.class, GIVEN_WHEN_THEN));
		assertTrue(javaPackage.getScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldSortClassBehaviourAfterSpecification() {
		ClassBehaviourSorter classBehaviourSorter = new ClassBehaviourSorter(TestMyFirstClass.class, TestMySecondClass.class);

		Package javaPackage = new Package(TestMySecondClass.class.getPackage(), classBehaviourSorter);
		javaPackage.addBehaviour(new TestMethod(TestMySecondClass.class, "testMe"));
		javaPackage.addBehaviour(new TestMethod(TestMyFirstClass.class, "testMe"));

		assertEquals("MyFirstClass", javaPackage.getClassBehaviour().get(0).getClassName());
		assertEquals("MySecondClass", javaPackage.getClassBehaviour().get(1).getClassName());
	}

	public class TestMySecondClass {
		@Test
		public void testMe() {

		}
	}

	public class TestMyFirstClass {
		@Test
		public void testMe() {

		}
	}

}
