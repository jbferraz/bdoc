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

package com.googlecode.bdoc.mojo;

import org.apache.maven.project.MavenProject;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.bdoc.mojo.RootPackageGenerator;

/**
 *  @author Per Otto Bergum Christensen
 */
public class TestRootPackageGenerator {

	private MavenProject project;

	@Before
	public void resetProject() {
		project = new MavenProject();
	}

	@Test
	public void shouldUseGroupidAsPackageIfItIsEqualWithTheArtifactid() {
		project.setGroupId("test");
		project.setArtifactId("test");
		assertEquals("test", RootPackageGenerator.calculateRootPackage(project));
	}

	@Test
	public void shouldUseGroupidAsPackageIfItEndsWithTheArtifactid() {
		project.setGroupId("com.test");
		project.setArtifactId("test");
		assertEquals("com.test", RootPackageGenerator.calculateRootPackage(project));

	}

	@Test
	public void givenACompoundGroupIdWithDotAsSeperatorWhenTheArtifactidPrefixMatchesTheGroupidPostfixThenAddTheGroupIdAndTheArtifactPostfix() {
		project.setGroupId("com.test");
		project.setArtifactId("test-all");
		assertEquals("com.test.all", RootPackageGenerator.calculateRootPackage(project));
	}

	@Test
	public void givenACompoundGroupIdWithDashAsSeperatorWhenTheArtifactidPrefixMatchesTheGroupidPostfixThenAddTheGroupIdAndTheArtifactPostfix() {
		project.setGroupId("com-test");
		project.setArtifactId("test-all");
		assertEquals("com.test.all", RootPackageGenerator.calculateRootPackage(project));
	}

	@Test
	public void shouldUseTheGroupIdIfNoOtherRulesApplies() {
		project.setGroupId("com.test");
		project.setArtifactId("a.a");
		assertEquals("com.test", RootPackageGenerator.calculateRootPackage(project));
	}

	@Test
	public void shouldReplaceDashWithDot() {
		project.setGroupId("test-test");
		project.setArtifactId("test-test");
		assertEquals("test.test", RootPackageGenerator.calculateRootPackage(project));
	}

}
