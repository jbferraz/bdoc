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

package bdddoc4j.diff.domain;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import bdddoc4j.core.doc.Ref;
import bdddoc4j.core.doc.Story;
import bdddoc4j.core.domain.Project;

@Ref(Story.DIFF_OF_BDDDOCS)
public class TestProjectDiff {

	private Project oldVersion = new Project("name1", "version1");
	private Project newVersion = new Project("name2", "version2");

	private ProjectDiff projectDiff = new ProjectDiff(oldVersion, newVersion);

	@Test
	public void shouldReportDiffOnProject() {
		assertTrue(projectDiff.diffExists());
	}

	@Test
	public void shouldReportDiffOnProjectName() {
		assertTrue(projectDiff.getNameDiff().diffExists());
	}

	@Test
	public void shouldReportDiffOnProjectNameAndVersion() {
		assertTrue(projectDiff.getVersionDiff().diffExists());
	}
}
