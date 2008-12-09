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

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;

@Ref( Story.ADVANCED_SCENARIO_SPECIFICATION  )
public class TestSourceClassBehaviourParser {

	@Test
	public void shouldComposeScenarioFromTestConstructedWithGivenWhenThenMethods() throws IOException {
		String stackJava = FileUtils.readFileToString(new File("src/test/java/integrationtestclasses/stack/StackBehavior.java"));

		String[] testMethods = new String[] { "shouldPopSecondPushedValueFirst" };
		SourceClassBehaviourParser sourceClassBehaviourParser = new SourceClassBehaviourParser(stackJava, testMethods);

		List<Scenario.Part> scenarioParts = new ArrayList<Scenario.Part>();
		scenarioParts.add( new Scenario.Part("givenAStackWithTwoPushedValues") );
		scenarioParts.add( new Scenario.Part("whenPopIsCalled") );
		scenarioParts.add( new Scenario.Part("thenTheLastItemPushedShouldBeReturned") );
		scenarioParts.add( new Scenario.Part("thenTheValueShouldNotRemainInTheStack") );
		
		Scenario expectedScenario = new Scenario( scenarioParts );
		assertEquals(expectedScenario , sourceClassBehaviourParser.getScenarios().get(0) );
	}
	
	//test at en metode uten et scenario ikke gir feil

}
