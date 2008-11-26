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

package bdddoc4j.plugin.diff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import bdddoc4j.diff.report.DiffReport;

public class TestBddLogDirDiff {

	private BddLogDirDiff bddLogDirDiff = new BddLogDirDiff();

	private Mockery context = new Mockery();

	private static final String FIRST_BDD_DOC = "bddDoc.1.xml";
	private static final String PREVIOUS_BDD_DOC = "bddDoc.2.xml";
	private static final String LAST_BDD_DOC = "bddDoc.3.xml";

	private String[] bddLogFiles = new String[3];
	{
		bddLogFiles[0] = FIRST_BDD_DOC;
		bddLogFiles[1] = LAST_BDD_DOC;
		bddLogFiles[2] = PREVIOUS_BDD_DOC;
	}

	@Test
	public void givenAListOfBddDocsWhenADiffIsExecutedThenEnsureThatTheTwoLatestFilesAreComparedFirst() {

		final DiffExecutor diffExecutor = context.mock(DiffExecutor.class);

		context.checking(new Expectations() {
			{
				one(diffExecutor).createDiffReport(PREVIOUS_BDD_DOC, LAST_BDD_DOC);
				will(returnValue(new DiffReport(true)));
			}
		});

		bddLogDirDiff.setDiffExecutor(diffExecutor);
		bddLogDirDiff.process(bddLogFiles);

		context.assertIsSatisfied();
	}

	@Test
	public void givenAListOfBddDocsWhereTheTwoLatestDocsHaveNoDiffWhenADiffIsExectuedThenEnsureThatTheLatestFileIsComparedWithAnEarlierVersion() {

		final DiffExecutor diffExecutor = context.mock(DiffExecutor.class);

		context.checking(new Expectations() {
			{
				one(diffExecutor).createDiffReport(PREVIOUS_BDD_DOC, LAST_BDD_DOC);
				will(returnValue(new DiffReport(false)));
				one(diffExecutor).createDiffReport(FIRST_BDD_DOC, LAST_BDD_DOC);
				will(returnValue(new DiffReport(true)));
			}
		});

		bddLogDirDiff.setDiffExecutor(diffExecutor);
		bddLogDirDiff.process(bddLogFiles);

		context.assertIsSatisfied();
	}

	@Test
	public void shouldReturnTheFirstDiffReportWhereADiffExists() {
		final DiffExecutor diffExecutor = context.mock(DiffExecutor.class);
		final DiffReport diffReport1 = new DiffReport(false);
		final DiffReport diffReport2 = new DiffReport(true);

		context.checking(new Expectations() {
			{
				one(diffExecutor).createDiffReport(PREVIOUS_BDD_DOC, LAST_BDD_DOC);
				will(returnValue(diffReport1));
				one(diffExecutor).createDiffReport(FIRST_BDD_DOC, LAST_BDD_DOC);
				will(returnValue(diffReport2));
			}
		});

		bddLogDirDiff.setDiffExecutor(diffExecutor);
		DiffReport diffReport = bddLogDirDiff.process(bddLogFiles);

		assertSame(diffReport, diffReport2);
	}

	@Test
	public void shouldReturnADiffReportWhereNoDiffExistsIfThereAreNoDiffBetweenTheFilesInTheDirectory() {
		final DiffExecutor diffExecutor = context.mock(DiffExecutor.class);

		context.checking(new Expectations() {
			{
				one(diffExecutor).createDiffReport(PREVIOUS_BDD_DOC, LAST_BDD_DOC);
				will(returnValue(new DiffReport(false)));
				one(diffExecutor).createDiffReport(FIRST_BDD_DOC, LAST_BDD_DOC);
				will(returnValue(new DiffReport(false)));
			}
		});

		bddLogDirDiff.setDiffExecutor(diffExecutor);
		assertFalse(bddLogDirDiff.process(bddLogFiles).diffExists());
	}
}
