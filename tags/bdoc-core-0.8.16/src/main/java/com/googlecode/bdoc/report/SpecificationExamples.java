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

package com.googlecode.bdoc.report;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.ClassSpecifications;
import com.googlecode.bdoc.doc.domain.ClassStatements;
import com.googlecode.bdoc.doc.domain.Specifications;
import com.googlecode.bdoc.doc.domain.Statement;

public class SpecificationExamples {

	private BDocConfig bdocConfig;
	private Set<StatementExampleFrame> exampleFrameSet = new TreeSet<StatementExampleFrame>();

	public SpecificationExamples(BDocConfig bdocConfig) {
		this.bdocConfig = bdocConfig;
	}

	public void addFrom(Specifications specifications) {
		for (ClassStatements classStatements : specifications.getClassStatements()) {
			addStatementsWithExamples(classStatements.getClassName(), classStatements.getStatements());
		}
		for (ClassSpecifications classStatements : specifications.getClassSpecifications()) {
			addStatementsWithExamples(classStatements.getClassName(), classStatements.getSpecifications());
		}
	}

	void addStatementsWithExamples(String className, List<? extends Statement> statements) {
		for (Statement statement : statements) {
			if (statement.hasExamples()) {
				addStatementExampleFrame(new StatementExampleFrame(className, statement, bdocConfig));
			}
		}
	}

	boolean addStatementExampleFrame(StatementExampleFrame statementExampleFrame) {
		return exampleFrameSet.add(statementExampleFrame);
	}

	public Set<StatementExampleFrame> list() {
		return exampleFrameSet;
	}
}
