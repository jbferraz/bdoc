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

import static org.apache.commons.beanutils.MethodUtils.invokeMethod;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Describes behaviour for a Class.
 * 
 * Could be: - scenario - specification - statement - test table
 * 
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
public class ClassBehaviour implements ClassSpecifications, ClassStatements {

	private static final String TEST = "Test";

	private String className;

	private List<Scenario> scenarios = new ArrayList<Scenario>();

	private List<Specification> specifications = new ArrayList<Specification>();

	private List<Statement> statements = new ArrayList<Statement>();

	private List<TestTable> testTables = new ArrayList<TestTable>();

	public ClassBehaviour(Class<? extends Object> testClass) {
		this.className = retreiveClassName(testClass);
	}

	/**
	 * Retreives the name of the class under test
	 * 
	 * @param testClass
	 *            with tests
	 * @return name of class under test
	 */
	private String retreiveClassName(Class<? extends Object> testClass) {

		Annotation[] annotations = testClass.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().endsWith("RefClass")) {
				try {
					String className = String.valueOf(invokeMethod(annotation, "value", null));
					return removePackageAndParentClassName(className);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}

		String className = removePackageAndParentClassName(testClass.getName());

		if (className.equalsIgnoreCase(TEST)) {
			return className;
		}

		if (className.endsWith(TEST)) {
			return StringUtils.removeEnd(className, TEST);
		} else {
			if (className.startsWith(TEST)) {
				return StringUtils.removeStart(className, TEST);
			}
		}

		return className;
	}

	/**
	 * Removes the package and parent className from a fullClassName. Ex:
	 * com.bdoc.MyClass$SubClass => SubClass
	 * 
	 * @param name
	 *            to strip
	 * @return just the className
	 */
	private String removePackageAndParentClassName(String fullClassName) {
		String className = fullClassName;
		if (className.contains(".")) {
			String[] splitOnDot = className.split("\\.");
			className = splitOnDot[splitOnDot.length - 1];
		}

		if (className.contains("$")) {
			String[] splitOnInnerClass = className.split("\\$");
			className = splitOnInnerClass[splitOnInnerClass.length - 1];
		}
		return className;
	}

	public void addBehaviour(TestMethod testMethod) {
		addBehaviour(testMethod.camelCaseSentence());
	}

	private void addBehaviour(String camelCaseSentence) {

		if (Scenario.Pattern.match(camelCaseSentence)) {
			scenarios.add(new Scenario(camelCaseSentence));
			return;
		}

		if (Specification.Pattern.match(camelCaseSentence)) {
			specifications.add((Specification) new Specification(camelCaseSentence));
			return;
		}

		statements.add(new Statement(camelCaseSentence));
	}

	public String getClassName() {
		return className;
	}

	@Override
	public String toString() {
		return "className=" + className;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ClassBehaviour) && ((ClassBehaviour) obj).className.equals(className);
	}

	public List<Specification> getSpecifications() {
		return specifications;
	}

	/**
	 * @return unmodifiableList of scenarios
	 */
	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public List<TestTable> getTestTables() {
		if (null == testTables) {
			testTables = new ArrayList<TestTable>();
		}
		return testTables;
	}

	public boolean hasTestTables() {
		return !getTestTables().isEmpty();
	}

	public boolean hasSpecifications() {
		return !specifications.isEmpty();
	}

	public boolean hasScenarios() {
		return !scenarios.isEmpty();
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public boolean hasStatements() {
		return !statements.isEmpty();
	}

	public void addScenarios(List<Scenario> scenarios) {
		this.scenarios.addAll(scenarios);
	}

	public void addScenario(Scenario scenario) {
		this.scenarios.add(scenario);
	}

	public void addTestTables(List<TestTable> testTables) {
		this.testTables.addAll(testTables);
	}

	public void addSpecification(Specification specification) {
		this.specifications.add(specification);

	}

	public void addStatement(Statement statement) {
		this.statements.add(statement);
	}

}
