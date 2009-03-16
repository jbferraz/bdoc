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

import com.googlecode.bdoc.BConst;
import com.googlecode.bdoc.diff.domain.subpackage.TestClassNewInSubPackage;
import com.googlecode.bdoc.diff.domain.testpackageremoved.TestClassRepresentsGeneralBehaviourThatIsRemoved;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.testdata.ExReference;
import com.googlecode.bdoc.doc.testdata.TestWithGeneralBehaviour;

/**
 *  @author Per Otto Bergum Christensen
 */
public class BDocGeneralBehaviourDiffDocTestdataHelper {

	private final BDoc oldBddDoc = new BDoc(org.junit.Test.class, ExReference.class, org.junit.Ignore.class);
	private final BDoc newBddDoc = new BDoc(org.junit.Test.class, ExReference.class, org.junit.Ignore.class);

	{
		ProjectInfo projectInfo = new ProjectInfo("test-project", "version1");
		oldBddDoc.setProject(projectInfo);
		oldBddDoc.addBehaviourFrom(new TestClass(TestClassRepresentsGeneralBehaviourThatIsRemoved.class), BConst.SRC_TEST_JAVA);
		oldBddDoc.addBehaviourFrom(new TestClass(TestWithGeneralBehaviour.class), BConst.SRC_TEST_JAVA);
		oldBddDoc.classBehaviourInGeneralBehaviour(TestWithGeneralBehaviour.class).addBehaviour(
				"givenAScenarioWhenItIsRemovedThenEnsureItIsReportedAsDeleted");

		oldBddDoc.classBehaviourInGeneralBehaviour(TestWithGeneralBehaviour.class).addBehaviour("shouldReportDeletedSpecifications");

		newBddDoc.setProject(projectInfo);
		newBddDoc.addBehaviourFrom(new TestClass(TestClassNewInSubPackage.class), BConst.SRC_TEST_JAVA);
		newBddDoc.addBehaviourFrom(new TestClass(TestWithGeneralBehaviour.class), BConst.SRC_TEST_JAVA);

		newBddDoc.classBehaviourInGeneralBehaviour(TestWithGeneralBehaviour.class).addBehaviour(
				"givenANewScenario1WhenDiffIsRunThenPackageIsUpdatedWithANewScenario");

		newBddDoc.classBehaviourInGeneralBehaviour(TestWithGeneralBehaviour.class).addBehaviour(
				"givenANewScenario2WhenDiffIsRunThenPackageIsUpdatedWithANewScenario");

		newBddDoc.classBehaviourInGeneralBehaviour(TestWithGeneralBehaviour.class).addBehaviour("shouldRepresentANewSpecification1");
		newBddDoc.classBehaviourInGeneralBehaviour(TestWithGeneralBehaviour.class).addBehaviour("shouldRepresentANewSpecification2");

	}

	private BDocGeneralBehaviourDiffDocTestdataHelper() {
	}

	private static BDocGeneralBehaviourDiffDocTestdataHelper instance() {
		return new BDocGeneralBehaviourDiffDocTestdataHelper();
	}

	public static BDocDiff getBddDocDiffForUpdatedGeneralBehaviour() {
		return new BDocDiff(instance().oldBddDoc, instance().newBddDoc);
	}

}
