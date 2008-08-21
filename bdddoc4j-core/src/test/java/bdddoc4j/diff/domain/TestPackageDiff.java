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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.domain.Package;
import bdddoc4j.core.domain.Scenario;
import bdddoc4j.core.domain.Specification;
import bdddoc4j.core.domain.Statement;

@Ref(Story.DIFF_OF_BDDDOCS)
public class TestPackageDiff {

	private static final String ANOTHER_STATEMENT = "anotherStatement";

	private static final String ASTATEMENT_ABOUT_TEST_DATA = "aStatementAboutTestData";

	private static final String SHOULD_DO_LIKE_THAT_ALSO = "shouldDoLikeThatAlso";

	private static final String SHOULD_DO_LIKE_THAT = "shouldDoLikeThat";

	private static final String GIVEN_XYZ_WHEN_THEN = "givenXyzWhenThen";

	private static final String GIVEN_WHEN_THEN = "givenWhenThen";

	private final Package emptyPackage = Package.forClass(TestAsTestdata.class);
	private final Package packageWithClassBehaviour = Package.forClass(TestAsTestdata.class);
	{
		packageWithClassBehaviour.addBehaviour(TestAsTestdata.class, SHOULD_DO_LIKE_THAT);
		packageWithClassBehaviour.addBehaviour(TestAsTestdata.class, GIVEN_WHEN_THEN);
		packageWithClassBehaviour.addBehaviour(TestAsTestdata.class, ASTATEMENT_ABOUT_TEST_DATA);
	}

	private final Package packageWithOneClassBehaviourWhereScenarioAndSpecificationIsReplaced = Package.forClass(TestAsTestdata.class);
	{
		packageWithOneClassBehaviourWhereScenarioAndSpecificationIsReplaced.addBehaviour(TestAsTestdata.class,
				"shouldDoLikeThatVersion2");
		packageWithOneClassBehaviourWhereScenarioAndSpecificationIsReplaced
				.addBehaviour(TestAsTestdata.class, "givenWhenThenVersion2");
	}

	private final Package packageWithUpdatedClassBehaviour = Package.forClass(TestAsTestdata.class);
	{
		packageWithUpdatedClassBehaviour.addBehaviour(TestAsTestdata.class, SHOULD_DO_LIKE_THAT);
		packageWithUpdatedClassBehaviour.addBehaviour(TestAsTestdata.class, SHOULD_DO_LIKE_THAT_ALSO);
		packageWithUpdatedClassBehaviour.addBehaviour(TestAsTestdata.class, ANOTHER_STATEMENT);
		packageWithUpdatedClassBehaviour.addBehaviour(TestAsTestdata.class, GIVEN_WHEN_THEN);
		packageWithUpdatedClassBehaviour.addBehaviour(TestAsTestdata.class, GIVEN_XYZ_WHEN_THEN);
	}

	@Test
	public void shouldReportDiffForUnchangedBehaviourInAPackage() {
		assertFalse(new PackageDiff(packageWithClassBehaviour, packageWithClassBehaviour).diffExists());
	}

	@Test
	public void shouldReturnScenariosAsNewWhenTheyAreAddedAsPartOfANewClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(emptyPackage, packageWithClassBehaviour);

		assertTrue(packageDiff.hasNewScenarios());
		assertTrue(packageDiff.getNewScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldReturnScenariosAsDeletedWhenTheyAreRemovedFromAClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour,
				packageWithOneClassBehaviourWhereScenarioAndSpecificationIsReplaced);

		assertTrue(packageDiff.hasNewScenarios());
		assertTrue(packageDiff.getDeletedScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldReturnScenariosAsDeletedWhenTheClassBehaviourIsDeleted() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour, emptyPackage);
		assertTrue(packageDiff.hasDeletedScenarios());
		assertTrue(packageDiff.getDeletedScenarios().contains(new Scenario(GIVEN_WHEN_THEN)));
	}

	@Test
	public void shouldReturnScenariosAsNewWhenTheyAreAddedToAnExistingClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour, packageWithUpdatedClassBehaviour);
		assertTrue(packageDiff.hasNewScenarios());
		assertTrue(packageDiff.getNewScenarios().contains(new Scenario(GIVEN_XYZ_WHEN_THEN)));
	}

	@Test
	public void shouldReturnNewClassSpecificationsForSpecificationsAddedAsPartOfANewClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(emptyPackage, packageWithClassBehaviour);
		assertTrue(packageDiff.hasNewClassSpecifications());
		assertTrue(packageDiff.getNewClassSpecifications().get(0).getSpecifications().contains(new Specification(SHOULD_DO_LIKE_THAT)));
	}

	@Test
	public void shouldReturnNewClassSpecificationsForSpecificationsAddedToAnExistingClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour, packageWithUpdatedClassBehaviour);
		assertTrue(packageDiff.hasNewClassSpecifications());
		assertTrue(packageDiff.getNewClassSpecifications().get(0).getSpecifications().contains(
				new Specification(SHOULD_DO_LIKE_THAT_ALSO)));
	}

	@Test
	public void shouldReturnNewClassStatementsForStatementsAddedAsPartOfANewClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(emptyPackage, packageWithClassBehaviour);
		assertTrue(packageDiff.hasNewClassStatements());
		assertTrue(packageDiff.getNewClassStatements().get(0).getStatements().contains(new Statement(ASTATEMENT_ABOUT_TEST_DATA)));
	}

	@Test
	public void shouldReturnNewClassStatementsForStatementsAddedToAnExistingClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour, packageWithUpdatedClassBehaviour);
		assertTrue(packageDiff.hasNewClassStatements());
		assertTrue(packageDiff.getNewClassStatements().get(0).getStatements().contains(new Statement(ANOTHER_STATEMENT)));
	}

	@Test
	public void shouldReturnDeletedClassSpecificationsWhenASpecificationIsRemovedFromAClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour,
				packageWithOneClassBehaviourWhereScenarioAndSpecificationIsReplaced);

		assertTrue(packageDiff.hasDeletedClassSpecifications());
		assertTrue(packageDiff.getDeletedClassSpecifications().get(0).getSpecifications().contains(
				new Specification(SHOULD_DO_LIKE_THAT)));
	}

	@Test
	public void shouldReturnDeletedClassSpecficationsWhenTheirClassBehaviourIsDeleted() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour, emptyPackage);
		assertTrue(packageDiff.hasDeletedClassSpecifications());
		assertTrue(packageDiff.getDeletedClassSpecifications().get(0).getSpecifications().contains(
				new Specification(SHOULD_DO_LIKE_THAT)));
	}

	@Test
	public void shouldReturnDeletedClassStatementsWhenTheirClassBehaviourIsDeleted() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour, emptyPackage);
		assertTrue(packageDiff.hasDeletedClassStatements());
		assertTrue(packageDiff.getDeletedClassStatements().get(0).getStatements().contains(new Statement(ASTATEMENT_ABOUT_TEST_DATA)));
	}

	@Test
	public void shouldReturnDeletedClassStatementsWhenAStatementIsRemovedFromAClassBehaviour() {
		PackageDiff packageDiff = new PackageDiff(packageWithClassBehaviour,
				packageWithOneClassBehaviourWhereScenarioAndSpecificationIsReplaced);

		assertTrue(packageDiff.hasDeletedClassStatements());
		assertTrue(packageDiff.getDeletedClassStatements().get(0).getStatements().contains(new Statement(ASTATEMENT_ABOUT_TEST_DATA)));
	}

	public class TestAsTestdata {
	}
}
