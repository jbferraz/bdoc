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

package com.googlecode.bdoc.diff.domain;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.bdoc.doc.domain.ClassSpecifications;
import com.googlecode.bdoc.doc.domain.ClassStatements;
import com.googlecode.bdoc.doc.domain.Package;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.UserStory;

/**
 * @author Per Otto Bergum Christensen
 */
public class UserStoryDiff extends ModuleBehaviourDiff implements ModuleDiff {

	private final Integer userStoryId;
	private final NarrativeDiff narrativeDiff;
	private final StringDiff titleDiff;

	public UserStoryDiff(UserStory oldVersion, UserStory newVersion) {
		super(oldVersion, newVersion);
		userStoryId = oldVersion.getId();
		titleDiff = new StringDiff(oldVersion.getTitle(), newVersion.getTitle());
		narrativeDiff = NarrativeDiff.create(oldVersion.getNarrative(), newVersion.getNarrative());
	}

	public NarrativeDiff getNarrativeDiff() {
		return narrativeDiff;
	}

	public boolean hasTitleDiff() {
		return null != titleDiff;
	}

	public StringDiff getTitleDiff() {
		return titleDiff;
	}

	public boolean hasNarrativeDiff() {
		return null != narrativeDiff;
	}

	public Integer getUserStoryId() {
		return userStoryId;
	}

	public boolean diffExists() {
		return super.diffExists() || titleDiff.diffExists() || (hasNarrativeDiff() && narrativeDiff.diffExists());
	}

	public String getTitle() {
		return titleDiff.getNewValue();
	}

	public List<ClassStatements> getNewClassStatements() {
		List<ClassStatements> result = new ArrayList<ClassStatements>();
		for (Package newPackage : getNewPackages()) {
			if (newPackage.hasClassStatements()) {
				result.addAll(newPackage.getClassStatements());
			}
		}

		for (PackageDiff packageDiff : getPackageDiff()) {
			if (packageDiff.hasNewClassStatements()) {
				result.addAll(packageDiff.getNewClassStatements());
			}
		}

		return result;
	}

	public boolean hasNewScenarios() {
		return !getNewScenarios().isEmpty();
	}

	public List<Scenario> getNewScenarios() {
		List<Scenario> result = new ArrayList<Scenario>();

		for (Package newPackage : getNewPackages()) {
			if (newPackage.hasScenarios()) {
				result.addAll(newPackage.getScenarios());
			}
		}

		for (PackageDiff packageDiff : getPackageDiff()) {
			if (packageDiff.hasNewScenarios()) {
				result.addAll(packageDiff.getNewScenarios());
			}
		}

		return result;
	}

	public boolean hasNewClassSpecifications() {
		return !getNewClassSpecifications().isEmpty();
	}

	public boolean hasNewClassStatements() {
		return !getNewClassStatements().isEmpty();
	}

	public List<ClassSpecifications> getNewClassSpecifications() {
		List<ClassSpecifications> result = new ArrayList<ClassSpecifications>();

		for (Package newPackage : getNewPackages()) {
			if (newPackage.hasClassSpecifications()) {
				result.addAll(newPackage.getClassSpecifications());
			}
		}

		for (PackageDiff packageDiff : getPackageDiff()) {
			if (packageDiff.hasNewClassSpecifications()) {
				result.addAll(packageDiff.getNewClassSpecifications());
			}
		}

		return result;
	}

	public boolean hasDeletedClassSpecifications() {
		return !getDeletedClassSpecifications().isEmpty();
	}

	public List<ClassSpecifications> getDeletedClassSpecifications() {
		List<ClassSpecifications> result = new ArrayList<ClassSpecifications>();

		for (Package deletedPackage : packages.getDeletedItems()) {
			if (deletedPackage.hasClassSpecifications()) {
				result.addAll((List<ClassSpecifications>) deletedPackage.getClassSpecifications());
			}
		}

		for (PackageDiff packageDiff : packages.getUpdatedItems()) {
			if (packageDiff.hasDeletedClassSpecifications()) {
				result.addAll(packageDiff.getDeletedClassSpecifications());
			}
		}

		return result;
	}

	public List<Scenario> getDeletedScenarios() {

		List<Scenario> result = new ArrayList<Scenario>();

		for (Package deletedPackage : packages.getDeletedItems()) {
			if (deletedPackage.hasScenarios()) {
				result.addAll(deletedPackage.getScenarios());
			}
		}

		for (PackageDiff packageDiff : packages.getUpdatedItems()) {
			if (packageDiff.hasDeletedScenarios()) {
				result.addAll(packageDiff.getDeletedScenarios());
			}
		}

		return result;
	}

	public boolean hasDeletedScenarios() {
		return !getDeletedScenarios().isEmpty();
	}

	public boolean hasDeletedClassStatements() {
		return !getDeletedClassStatements().isEmpty();
	}

	public List<ClassStatements> getDeletedClassStatements() {
		List<ClassStatements> result = new ArrayList<ClassStatements>();

		for (Package deletedPackage : packages.getDeletedItems()) {
			if (deletedPackage.hasClassStatements()) {
				result.addAll((List<ClassStatements>) deletedPackage.getClassStatements());
			}
		}

		for (PackageDiff packageDiff : packages.getUpdatedItems()) {
			if (packageDiff.hasDeletedClassStatements()) {
				result.addAll(packageDiff.getDeletedClassStatements());
			}
		}

		return result;
	}

}
