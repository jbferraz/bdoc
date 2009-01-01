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

package com.googlecode.bdoc;

/**
 * @author Per Otto Bergum Christensen
 */
public enum Story {

	CREATE_BDOC_FROM_CODE(1, "As a developer", "I want to implement a userstory", "so that a BDoc can be extracted from the code"),

	XML_REPORT(2, "As a developer in the project", "I want to open the BDoc", "so that it opens in an XML editor"),

	HTML_REPORT(3, "As a stakeholder in the project", "I want to generated BDoc as html", "so that it opens in a browser"),

	CLASS_DIAGRAM(5, "As a developer in the project", "I want to generated a class diagram of the domain model",
			"so that it opens in a browser"),

	STORY_REFERENCE_CODE_GENERATOR(6, "As a developer in the project", "I want to generate an example of the story and ref class",
			"so that I can start using it in the code"),

	DIFF_OF_BDOCS(7, "As a developer in the project", "I want to diff to versions of a BDoc",
			"so that I can see the difference between the two versions"),

	HTML_DIFF_REPORT(8, "As a stakeholder in the project", "I want to generated BDocDiff as html", "so that it opens in a browser"),

	ADVANCED_SCENARIO_SPECIFICATION(9, "As a developer in the project",
			"I want to write a scenario as a series of given when then methods",
			"so that I can use more than one line to describe the behaviour"),

	CHANGE_LOG(10, "As a developer in the project", "I want a bdoc changelog to be generated continuously",
			"so that I can keep track of each change in behaviour");

	private Integer id;
	private String[] narrative;

	Story(Integer id, String role, String action, String benefit) {
		this.id = id;
		this.narrative = new String[] { role, action, benefit };
	}

	public Integer id() {
		return id;
	}

	public String[] narrative() {
		return narrative;
	}
}
