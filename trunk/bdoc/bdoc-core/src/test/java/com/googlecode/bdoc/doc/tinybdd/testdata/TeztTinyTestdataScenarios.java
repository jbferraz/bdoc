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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class TeztTinyTestdataScenarios extends TinyScenarioSupport<TeztTinyTestdataScenarios> {

	private int number;
	private TeztTinyTestdataScenarios gitt = createScenarioKeyword("gitt", false);

	@Test
	public void simpleGivenWhenThen() {
		given.criteriaA();
		when.actionA();
		then.ensureA();
	}

	void criteriaA() {
	}

	void actionA() {
	}

	void ensureA() {
	}

	@Test
	public void willFailIfStateIsNotHandledByBddAnalyzer() {
		given.numberWithValue(42);
		then.ensureNumberIs(42);
	}

	void numberWithValue(int i) {
		this.number = i;
	}

	void ensureNumberIs(int i) {
		assertEquals(i, number);
	}

	@Test
	public void scenarioWithPrimitivArgument() {
		given.numberWithValue(10);
	}

	@Test
	public void containsScenarioWithTable() {
		given.listWith(randomNumbers());
	}

	void listWith(Object randomNumbers) {
	}

	Object randomNumbers() {
		return Arrays.asList(1, 2, 3);
	}

	@Test
	public void containsScenarioWithNorwegianLanguage() {
		gitt.tilstandX();
	}

	void tilstandX() {
	}

	@Test
	public void scenarioWithAnd() {

		given.stateA();
		and.stateB();
	}

	void stateB() {
	}

	void stateA() {

	}

	@Test
	public void noExamples() {
	}

	@Test
	public void spexWithExample() {
		example.addOperation(2, 2, 4);
		example.addOperation(3, 2, 5);
	}

	void addOperation(int operator1, int operator2, int sum) {
	}

}
