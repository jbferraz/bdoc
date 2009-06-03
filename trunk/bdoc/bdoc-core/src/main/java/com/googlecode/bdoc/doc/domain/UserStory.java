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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * @author Per Otto Bergum Christensen
 */
public class UserStory extends ModuleBehaviour implements UserStoryDescription, Comparable<UserStory> {

	private final Integer id;
	private final String title;
	private final Narrative narrative;

	public UserStory(UserStoryDescription userStoryDescription) {
		Validate.notNull(userStoryDescription);
		this.id = userStoryDescription.getId();
		this.title = userStoryDescription.getTitle();
		this.narrative = userStoryDescription.getNarrative();
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Narrative getNarrative() {
		return narrative;
	}

	public List<Scenario> getScenarios() {
		List<Scenario> scenarios = new ArrayList<Scenario>();

		for (Package javaPackage : getPackages()) {
			scenarios.addAll(javaPackage.getScenarios());
		}

		return Collections.unmodifiableList(scenarios);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof UserStory) && ((UserStory) obj).id.equals(id);
	}

	public List<ClassSpecifications> getClassSpecifications() {
		List<ClassSpecifications> result = new ArrayList<ClassSpecifications>();

		for (Package javaPackage : getPackages()) {
			if (javaPackage.hasClassSpecifications()) {
				result.addAll((javaPackage.getClassSpecifications()));
			}
		}

		return result;
	}

	public List<ClassStatements> getClassStatements() {
		List<ClassStatements> result = new ArrayList<ClassStatements>();
		for (Package javaPackage : getPackages()) {
			if (javaPackage.hasClassStatements()) {
				result.addAll((javaPackage.getClassStatements()));
			}
		}
		return result;
	}

	public List<TestTable> getTestTables() {
		List<TestTable> testTables = new ArrayList<TestTable>();

		for (Package javaPackage : getPackages()) {
			testTables.addAll(javaPackage.getTestTables());
		}

		return Collections.unmodifiableList(testTables);
	}

	public int compareTo(UserStory otherUserStory) {
		return id.compareTo(otherUserStory.getId());
	}
}
