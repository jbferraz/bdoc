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

import static org.apache.commons.beanutils.MethodUtils.invokeMethod;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes behaviour for a Class.
 * 
 * @author Per Otto Bergum Christensen
 */
public class ClassBehaviour implements ClassSpecifications, ClassStatements {

	private String className;

	private List<Scenario> scenarios = new ArrayList<Scenario>();

	private List<Specification> specifications = new ArrayList<Specification>();

	private List<Statement> statements = new ArrayList<Statement>();

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

		String name = null;

		Annotation[] annotations = testClass.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().endsWith("RefClass")) {
				try {
					name = String.valueOf(invokeMethod(annotation, "value", null));
					break;
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}
		}

		if (null == name) {
			name = testClass.getName();
		}

		if (name.contains(".")) {
			String[] splitOnDot = name.split("\\.");
			name = splitOnDot[splitOnDot.length - 1];
		}

		if (name.contains("$")) {
			String[] splitOnInnerClass = name.split("\\$");
			name = splitOnInnerClass[splitOnInnerClass.length - 1];
		}

		if (name.contains("Test")) {
			String[] splitOnTestPrefix = name.split("Test");
			name = splitOnTestPrefix[splitOnTestPrefix.length - 1];
		}

		return name;
	}

	public void addBehaviour(String camelCaseSentence) {

		if (Scenario.Pattern.match(camelCaseSentence)) {
			scenarios.add(new Scenario(camelCaseSentence));
			return;
		}

		if (Specification.Pattern.match(camelCaseSentence)) {
			specifications.add(new Specification(camelCaseSentence));
			return;
		}

		statements.add(new Statement(camelCaseSentence));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bdddoc4j.core.domain.SpecificationContainer#getClassName()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see bdddoc4j.core.domain.SpecificationContainer#getSpecifications()
	 */
	public List<Specification> getSpecifications() {
		return specifications;
	}

	/**
	 * @return unmodifiableList of scenarios
	 */
	public List<Scenario> getScenarios() {
		return scenarios;
	}

	/*
	 * @see bdddoc4j.core.domain.SpecificationContainer#hasSpecifications()
	 */
	public boolean hasSpecifications() {
		return !specifications.isEmpty();
	}

	public boolean hasScenarios() {
		return !scenarios.isEmpty();
	}

	/*
	 * @see bdddoc4j.core.domain.ClassStatements#getStatements()
	 */
	public List<Statement> getStatements() {
		return statements;
	}

	public boolean hasStatements() {
		return !statements.isEmpty();
	}
}
