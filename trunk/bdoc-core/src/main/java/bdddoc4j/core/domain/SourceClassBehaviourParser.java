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

import java.util.ArrayList;
import java.util.List;

import bdddoc4j.core.util.JavaCodeUtil;

public class SourceClassBehaviourParser {

	private String javaClassSource;
	private String[] testMethods;

	public SourceClassBehaviourParser(String javaClassSource, String[] testMethods) {
		this.javaClassSource = javaClassSource;
		this.testMethods = testMethods;
	}

	public List<Scenario> getScenarios() {
		List<Scenario> result = new ArrayList<Scenario>();
		
		for (String testMethod : testMethods) {			
			String testMethodSource = JavaCodeUtil.javaBlockAfter(javaClassSource, testMethod );
			result.add( new Scenario( JavaCodeUtil.getGivenWhenThenMethods(testMethodSource) ) );			
		}
		
		return result;
	}

}
