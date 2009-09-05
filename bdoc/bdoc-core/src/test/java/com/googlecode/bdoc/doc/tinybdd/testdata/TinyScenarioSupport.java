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

package com.googlecode.bdoc.doc.tinybdd.testdata;

@SuppressWarnings("unchecked")
public class TinyScenarioSupport<T> {

	protected T given = createScenarioKeywordGiven("given");
	protected T when = createScenarioKeywordWhen("when");
	protected T then = createScenarioKeywordThen("then" );
	protected T and = createScenarioKeywordGeneric("and");
	protected T example = createExampleKeyword("example");

	public T createScenarioKeyword(String scenarioPart, boolean indent) {
		return (T) this;
	}

	public T createScenarioKeywordGiven(String localizedGiven) {
		return (T) this;
	}

	public T createScenarioKeywordWhen(String localizedGiven) {
		return (T) this;
	}

	public T createScenarioKeywordThen(String localizedGiven) {
		return (T) this;
	}

	public T createScenarioKeywordGeneric(String localizedGiven) {
		return (T) this;
	}

	public T createExampleKeyword(String keyword) {
		return (T) this;
	}

}
