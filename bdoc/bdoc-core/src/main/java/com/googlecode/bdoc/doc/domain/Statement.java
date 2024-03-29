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

import java.util.List;

/**
 * A Statement defines loose behaviour, without the classic 'should'
 * 
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
/**
 * @author Per Otto
 * 
 */
public class Statement {

	private static final String $SPEC$ = "$spec$";

	private static final String SPACE = " ";

	private TestMethodReference testMethodReference;
	
	protected String camelCaseSentence;

	private List<Scenario> scenarios;
	private List<TestTable> testTables;

	/** Spec is referenced inside the camelCaseSentence */
	protected String spec;

	

	public Statement(String camelCaseSentence) {
		this(camelCaseSentence, null);
	}

	public Statement(TestMethod testMethod) {
		this(testMethod.camelCaseSentence(), testMethod.getSpec());
		this.scenarios = testMethod.getScenarios();
		this.testTables = testMethod.getTestTables();
		this.testMethodReference = testMethod.getTestMethodReference();
	}

	public Statement(String camelCaseSentence, String spec) {
		this.camelCaseSentence = camelCaseSentence;
		this.spec = spec;
	}

	public String getSentence() {
		String result = camelCaseSentence;
		if (hasSpec()) {
			result = camelCaseSentence.replace($SPEC$, SPACE + spec + SPACE);
		}
		return result.trim();
	}
	
	public String getCamelCaseSentence() {
		return camelCaseSentence;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Statement) && ((Statement) obj).camelCaseSentence.equals(camelCaseSentence);
	}

	public boolean hasSpec() {
		return (null != spec) && camelCaseSentence.contains($SPEC$);
	}

	public String getSpec() {
		return spec;
	}

	public List<Scenario> getScenarios() {
		return scenarios;
	}

	public List<TestTable> getTestTables() {
		return testTables;
	}

	@Override
	public String toString() {
		return camelCaseSentence;
	}

	public boolean hasTestTables() {
		return null != testTables && !testTables.isEmpty();
	}
	
	public boolean hasScenarios() {
		return null != scenarios && !scenarios.isEmpty();
	}
	
	public boolean hasExamples() {
		return hasScenarios() || hasTestTables();
	}

	public TestMethodReference getTestMethodReference() {
		return testMethodReference;
	}

}