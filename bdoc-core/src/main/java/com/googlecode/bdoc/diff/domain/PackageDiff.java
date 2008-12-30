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

import java.util.ArrayList;
import java.util.List;

import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.ClassSpecifications;
import com.googlecode.bdoc.doc.domain.ClassStatements;
import com.googlecode.bdoc.doc.domain.Package;
import com.googlecode.bdoc.doc.domain.Scenario;


public class PackageDiff implements ModuleDiff {

	private String name;

	private final ListDiff<ClassBehaviour, ClassBehaviourDiff> classBehaviourListDiff;

	public PackageDiff(Package oldPackage, Package newPackage) {
		name = newPackage.getName();
		classBehaviourListDiff = DiffFactory.create(oldPackage.getClassBehaviour(), newPackage.getClassBehaviour());
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof PackageDiff) && ((PackageDiff) obj).name.equals(name);
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#getName()
	 */
	public String getName() {
		return name;
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#diffExists()
	 */
	public boolean diffExists() {
		return classBehaviourListDiff.diffExists();
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#hasNewScenarios()
	 */
	public boolean hasNewScenarios() {
		return !getNewScenarios().isEmpty();
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#hasDeletedScenarios()
	 */
	public boolean hasDeletedScenarios() {
		return !getDeletedScenarios().isEmpty();
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#hasDeletedClassSpecifications()
	 */
	public boolean hasDeletedClassSpecifications() {
		return !getDeletedClassSpecifications().isEmpty();
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#hasNewClassSpecifications()
	 */
	public boolean hasNewClassSpecifications() {
		return !getNewClassSpecifications().isEmpty();
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#getNewScenarios()
	 */
	public List<Scenario> getNewScenarios() {
		List<Scenario> newScenarios = new ArrayList<Scenario>();
		for (ClassBehaviour classBehaviour : classBehaviourListDiff.getNewItems()) {
			newScenarios.addAll(classBehaviour.getScenarios());
		}

		for (ClassBehaviourDiff classBehaviourDiff : classBehaviourListDiff.getUpdatedItems()) {
			newScenarios.addAll(classBehaviourDiff.getNewScenarios());
		}

		return newScenarios;
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#getDeletedScenarios()
	 */
	public List<Scenario> getDeletedScenarios() {

		List<Scenario> deletedScenarios = new ArrayList<Scenario>();

		for (ClassBehaviour classBehaviour : classBehaviourListDiff.getDeletedItems()) {
			deletedScenarios.addAll(classBehaviour.getScenarios());
		}

		for (ClassBehaviourDiff classBehaviourDiff : classBehaviourListDiff.getUpdatedItems()) {
			deletedScenarios.addAll(classBehaviourDiff.getDeletedScenarios());
		}

		return deletedScenarios;
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#getNewClassSpecifications()
	 */
	public List<ClassSpecifications> getNewClassSpecifications() {
		List<ClassSpecifications> newClassSpecifications = new ArrayList<ClassSpecifications>();

		for (ClassBehaviour classBehaviour : classBehaviourListDiff.getNewItems()) {
			if (classBehaviour.hasSpecifications()) {
				newClassSpecifications.add(classBehaviour);
			}
		}

		for (ClassBehaviourDiff classBehaviourDiff : classBehaviourListDiff.getUpdatedItems()) {
			if (classBehaviourDiff.hasNewSpecifications()) {
				newClassSpecifications.add(new DefaultClassSpecificationsImpl(classBehaviourDiff.getClassName(), classBehaviourDiff
						.getNewSpecifications()));
			}
		}

		return newClassSpecifications;
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#getDeletedClassSpecifications()
	 */
	public List<ClassSpecifications> getDeletedClassSpecifications() {
		List<ClassSpecifications> deletedClassSpecifications = new ArrayList<ClassSpecifications>();

		for (ClassBehaviour classBehaviour : classBehaviourListDiff.getDeletedItems()) {
			if (classBehaviour.hasSpecifications()) {
				deletedClassSpecifications.add(classBehaviour);
			}
		}

		for (ClassBehaviourDiff classBehaviourDiff : classBehaviourListDiff.getUpdatedItems()) {
			if (classBehaviourDiff.hasDeletedSpecifications()) {
				deletedClassSpecifications.add(new DefaultClassSpecificationsImpl(classBehaviourDiff.getClassName(),
						classBehaviourDiff.getDeletedSpecifications()));
			}
		}

		return deletedClassSpecifications;
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#hasNewClassStatements()
	 */
	public boolean hasNewClassStatements() {
		return !getNewClassStatements().isEmpty();
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#getNewClassStatements()
	 */
	public List<ClassStatements> getNewClassStatements() {
		List<ClassStatements> newClassStatements = new ArrayList<ClassStatements>();

		for (ClassBehaviour classBehaviour : classBehaviourListDiff.getNewItems()) {
			if (classBehaviour.hasSpecifications()) {
				newClassStatements.add(classBehaviour);
			}
		}

		for (ClassBehaviourDiff classBehaviourDiff : classBehaviourListDiff.getUpdatedItems()) {
			if (classBehaviourDiff.hasNewStatements()) {
				newClassStatements.add(new DefaultClassStatementsImpl(classBehaviourDiff.getClassName(), classBehaviourDiff
						.getNewStatements()));
			}
		}

		return newClassStatements;
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#getDeletedClassStatements()
	 */
	public List<ClassStatements> getDeletedClassStatements() {
		List<ClassStatements> deletedClassStatements = new ArrayList<ClassStatements>();

		for (ClassBehaviour classBehaviour : classBehaviourListDiff.getDeletedItems()) {
			if (classBehaviour.hasStatements()) {
				deletedClassStatements.add(classBehaviour);
			}
		}

		for (ClassBehaviourDiff classBehaviourDiff : classBehaviourListDiff.getUpdatedItems()) {
			if (classBehaviourDiff.hasDeletedStatements()) {
				deletedClassStatements.add(new DefaultClassStatementsImpl(classBehaviourDiff.getClassName(), classBehaviourDiff
						.getDeletedStatements()));
			}
		}

		return deletedClassStatements;
	}

	/* 
	 * @see bdddoc4j.diff.domain.ModuleDiff#hasDeletedClassStatements()
	 */
	public boolean hasDeletedClassStatements() {
		return !getDeletedClassStatements().isEmpty();
	}

}