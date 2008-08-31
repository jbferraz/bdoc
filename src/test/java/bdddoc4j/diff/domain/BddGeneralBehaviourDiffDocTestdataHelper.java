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

import bdddoc4j.core.domain.BddDoc;
import bdddoc4j.core.domain.Project;
import bdddoc4j.core.testdata.ExReference;
import bdddoc4j.core.testdata.TestWithGeneralBehaviour;
import bdddoc4j.diff.domain.subpackage.TestClassNewInSubPackage;
import bdddoc4j.diff.domain.testpackageremoved.TestClassRepresentsGeneralBehaviourThatIsRemoved;

public class BddGeneralBehaviourDiffDocTestdataHelper {

	private final BddDoc oldBddDoc = new BddDoc(org.junit.Test.class, ExReference.class);
	private final BddDoc newBddDoc = new BddDoc(org.junit.Test.class, ExReference.class);

	{
		Project project = new Project("test-project", "version1");
		oldBddDoc.setProject(project);
		oldBddDoc.addBehaviourFrom(TestClassRepresentsGeneralBehaviourThatIsRemoved.class);
		oldBddDoc.addBehaviourFrom(TestWithGeneralBehaviour.class);
		oldBddDoc.getGeneralBehaviourFor(TestWithGeneralBehaviour.class).addBehaviour(
				"givenAScenarioWhenItIsRemovedThenEnsureItIsReportedAsDeleted");

		oldBddDoc.getGeneralBehaviourFor(TestWithGeneralBehaviour.class).addBehaviour(
				"shouldReportDeletedSpecifications");

		newBddDoc.setProject(project);
		newBddDoc.addBehaviourFrom(TestClassNewInSubPackage.class);
		newBddDoc.addBehaviourFrom(TestWithGeneralBehaviour.class);

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
