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

import java.util.List;

import org.apache.commons.lang.Validate;

import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.Specification;
import com.googlecode.bdoc.doc.domain.Statement;


public class ClassBehaviourDiff implements Diff {

	private final String className;

	private final SimpleDiff<Scenario> scenarios;
	private final SimpleDiff<Specification> specifications;
	private final SimpleDiff<Statement> statements;

	public ClassBehaviourDiff(ClassBehaviour oldClassBehaviour, ClassBehaviour newClassBehaviour) {
		Validate.isTrue(oldClassBehaviour.getClassName().equals(newClassBehaviour.getClassName()));
		className = oldClassBehaviour.getClassName();

		scenarios = new SimpleDiff<Scenario>(oldClassBehaviour.getScenarios(), newClassBehaviour.getScenarios());
		specifications = new SimpleDiff<Specification>(oldClassBehaviour.getSpecifications(), newClassBehaviour.getSpecifications());
		statements = new SimpleDiff<Statement>(oldClassBehaviour.getStatements(), newClassBehaviour.getStatements());
	}

	public List<Scenario> getNewScenarios() {
		return scenarios.getNewItems();
	}

	public List<Scenario> getDeletedScenarios() {
		return scenarios.getDeletedItems();
	}

	public List<Specification> getNewSpecifications() {
		return specifications.getNewItems();
	}

	public List<Specification> getDeletedSpecifications() {
		return specifications.getDeletedItems();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ClassBehaviourDiff) && ((ClassBehaviourDiff) obj).className.equals(className);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bdddoc4j.diff.domain.Diff#diffExists()
	 */
	public boolean diffExists() {
		return scenarios.diffExists() || specifications.diffExists() || statements.diffExists();
	}

	public String getClassName() {
		return className;
	}

	public boolean hasNewSpecifications() {
		return !getNewSpecifications().isEmpty();
	}

	public boolean hasDeletedSpecifications() {
		return !getDeletedSpecifications().isEmpty();
	}

	public List<Statement> getNewStatements() {
		return statements.getNewItems();
	}

	public List<Statement> getDeletedStatements() {
		return statements.getDeletedItems();
	}

	public boolean hasNewStatements() {
		return !getNewStatements().isEmpty();
	}

	public boolean hasDeletedStatements() {
		return !getDeletedStatements().isEmpty();
	}
}