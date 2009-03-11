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

package com.googlecode.bdoc.doc.runtime;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.runtime.testdata.AccountBehaviour;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.ADVANCED_SCENARIO_SPECIFICATION)
public class TestRuntimeScenarioFactory {
	
	RuntimeScenarioFactory runtimeScenarioFactory = new RuntimeScenarioFactory();

	@Test
	public void shouldCreateAScenarioFromAListOfMethodCalls() {
		List<MethodCall> methodCalls = new ArrayList<MethodCall>();
		methodCalls.add(new MethodCall("given"));
		methodCalls.add(new MethodCall("when"));
		methodCalls.add(new MethodCall("then"));

		Scenario scenario = RuntimeScenarioFactory.create(methodCalls);
		assertEquals(new Scenario("givenWhenThen"), scenario);
	}

	@Test
	public void shouldCreateAScenarioFromTestMethodWithGivenWhenThenMethodCalls() {
		Scenario scenario = runtimeScenarioFactory.create(AccountBehaviour.class,"plainScenario");
		assertEquals(new Scenario("givenWhenThen"), scenario);
	}

	@Test
	public void shouldAddMethodCallArgumentValuesToTheEndOfEachScenarioPartSeperatedWithAnd() {
		Scenario scenario = runtimeScenarioFactory.create(AccountBehaviour.class,"scenarioWithArguments");
		assertEquals(new Scenario("given1When2And3Then4And5And6"), scenario);
	}

	@Test
	public void shouldUseTheWordOgForScenariosWrittenInNorwegian() {
		Scenario scenario = runtimeScenarioFactory.create(AccountBehaviour.class,"norwegianScenario");
		assertEquals(new Scenario("gitt1Naar2Og3Saa4Og5Og6"), scenario);
	}
}
