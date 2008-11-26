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

import static bdddoc4j.core.util.Select.from;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Package {

	private String name;

	private List<ClassBehaviour> classBehaviourList = new ArrayList<ClassBehaviour>();

	public Package(java.lang.Package javaPackage) {
		name = javaPackage.getName();
	}

	public ClassBehaviour getBehaviourFor(Class<? extends Object> testClass) {
		return from(classBehaviourList).equalTo(new ClassBehaviour(testClass));
	}

	public void addBehaviour(Class<? extends Object> testClass, String camelCaseSentence) {
		ClassBehaviour classBehaviour = new ClassBehaviour(testClass);
		if (classBehaviourList.contains(classBehaviour)) {
			classBehaviour = from(classBehaviourList).equalTo(classBehaviour);
		} else {
			classBehaviourList.add(classBehaviour);
		}
		classBehaviour.addBehaviour(camelCaseSentence);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Package) && ((Package) obj).name.equals(name);
	}

	public List<ClassBehaviour> getClassBehaviour() {
		return Collections.unmodifiableList(classBehaviourList);
	}

	/**
	 * Helper factory for creating a package from a given class
	 * 
	 * @param clazz
	 *            to create Package from
	 * @return instance of Package
	 */
	public static Package forClass(Class<? extends Object> clazz) {
		return new Package(clazz.getPackage());
	}

	public String getName() {
		return name;
	}

	public boolean hasClassSpecifications() {
		return !getClassSpecifications().isEmpty();
	}

	public boolean hasClassStatements() {
		return !getClassStatements().isEmpty();
	}

	public boolean hasScenarios() {
		return !getScenarios().isEmpty();
	}


	public List<Scenario> getScenarios() {
		List<Scenario> result = new ArrayList<Scenario>();
		for (ClassBehaviour classBehaviour : classBehaviourList) {
			if (classBehaviour.hasScenarios()) {
				result.addAll(classBehaviour.getScenarios());
			}
		}

		return result;
	}

	public List<ClassSpecifications> getClassSpecifications() {
		List<ClassSpecifications> result = new ArrayList<ClassSpecifications>();

		for (ClassBehaviour classBehaviour : classBehaviourList) {
			if (classBehaviour.hasSpecifications()) {
				result.add(classBehaviour);
			}
		}

		return result;
	}

	public List<ClassStatements> getClassStatements() {
		List<ClassStatements> result = new ArrayList<ClassStatements>();

		for (ClassBehaviour classBehaviour : classBehaviourList) {
			if (classBehaviour.hasStatements()) {
				result.add(classBehaviour);
			}
		}

		return result;
	}

}
