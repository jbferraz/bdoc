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

import static com.googlecode.bdoc.doc.util.Select.from;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Per Otto Bergum Christensen
 */
public class ModuleBehaviour {

	private final List<Package> packages = new ArrayList<Package>();

	public ClassBehaviour classBehaviourFor(Class<? extends Object> testClass) {
		return from(packages).equalTo(Package.forClass(testClass)).getBehaviourFor(testClass);
	}

	public ClassBehaviour addBehaviour(TestMethod method) {

		Package classPackage = Package.forClass(method.clazz());
		if (packages.contains(classPackage)) {
			classPackage = from(packages).equalTo(classPackage);
		} else {
			packages.add(classPackage);
		}

		ClassBehaviour classBehaviour = classPackage.addBehaviour(method);
//		if (method.hasScenarios()) {
//			classBehaviour.addScenarios(method.getScenarios());
//		}
		return classBehaviour;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public boolean hasClassSpecifications() {
		for (Package javaPackage : packages) {
			if (javaPackage.hasClassSpecifications()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasClassStatements() {
		for (Package javaPackage : packages) {
			if (javaPackage.hasClassStatements()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return list of all specifications found in general behaviour
	 */
	public List<Specification> specifications() {
		ArrayList<Specification> result = new ArrayList<Specification>();
		for (Package javaPackage : packages) {

			for (ClassSpecifications classSpecifications : javaPackage.getClassSpecifications()) {
				result.addAll(classSpecifications.getSpecifications());
			}

		}
		return result;
	}

	public List<Scenario> scenarios() {
		List<Scenario> result = new ArrayList<Scenario>();
		for (Package javaPackage : packages) {
			result.addAll(javaPackage.getScenarios());
		}
		return result;
	}

}
