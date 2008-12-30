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

package com.googlecode.bdoc.diff.domain;

import com.googlecode.bdoc.diff.domain.BddDocDiff;
import com.googlecode.bdoc.diff.domain.subpackage.TestClassNewInSubPackage;
import com.googlecode.bdoc.diff.domain.testpackageremoved.TestClassRepresentsGeneralBehaviourThatIsRemoved;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.Project;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.testdata.BddDocTestHelper;
import com.googlecode.bdoc.doc.testdata.ExReference;
import com.googlecode.bdoc.doc.testdata.TestWithGeneralBehaviour;


public class BddGeneralBehaviourDiffDocTestdataHelper {

	private final BDoc oldBddDoc = new BDoc(org.junit.Test.class, ExReference.class);
	private final BDoc newBddDoc = new BDoc(org.junit.Test.class, ExReference.class);

	{
		Project project = new Project("test-project", "version1");
		oldBddDoc.setProject(project);
		oldBddDoc.addBehaviourFrom(new TestClass(TestClassRepresentsGeneralBehaviourThatIsRemoved.class), BddDocTestHelper.SRC_TEST_JAVA);
		oldBddDoc.addBehaviourFrom(new TestClass(TestWithGeneralBehaviour.class), BddDocTestHelper.SRC_TEST_JAVA);
		oldBddDoc.getGeneralBehaviourFor(TestWithGeneralBehaviour.class).addBehaviour(
				"givenAScenarioWhenItIsRemovedThenEnsureItIsReportedAsDeleted");

		oldBddDoc.getGeneralBehaviourFor(TestWithGeneralBehaviour.class).addBehaviour(
				"shouldReportDeletedSpecifications");

		newBddDoc.setProject(project);
		newBddDoc.addBehaviourFrom(new TestClass(TestClassNewInSubPackage.class), BddDocTestHelper.SRC_TEST_JAVA);
		newBddDoc.addBehaviourFrom(new TestClass(TestWithGeneralBehaviour.class), BddDocTestHelper.SRC_TEST_JAVA);

		newBddDoc.getGeneralBehaviourFor(TestWithGeneralBehaviour.class).addBehaviour(
				"givenANewScenario1WhenDiffIsRunThenPackageIsUpdatedWithANewScenario");

		newBddDoc.getGeneralBehaviourFor(TestWithGeneralBehaviour.class).addBehaviour(
				"givenANewScenario2WhenDiffIsRunThenPackageIsUpdatedWithANewScenario");

		newBddDoc.getGeneralBehaviourFor(TestWithGeneralBehaviour.class).addBehaviour("shouldRepresentANewSpecification1");
		newBddDoc.getGeneralBehaviourFor(TestWithGeneralBehaviour.class).addBehaviour("shouldRepresentANewSpecification2");

	}

	private BddGeneralBehaviourDiffDocTestdataHelper() {
	}

	private static BddGeneralBehaviourDiffDocTestdataHelper instance() {
		return new BddGeneralBehaviourDiffDocTestdataHelper();
	}

	public static BddDocDiff getBddDocDiffForUpdatedGeneralBehaviour() {
		return new BddDocDiff(instance().oldBddDoc, instance().newBddDoc);
	}

}
