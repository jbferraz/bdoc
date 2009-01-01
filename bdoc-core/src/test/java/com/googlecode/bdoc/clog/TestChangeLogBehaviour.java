package com.googlecode.bdoc.clog;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.testdata.BDocTestHelper;
import com.googlecode.bdoc.doc.testdata.RefClass;

@Ref(Story.CHANGE_LOG)
@RefClass(ChangeLog.class)
public class TestChangeLogBehaviour {
	
	private ChangeLog givenAnNewChangeLog() {
		return new ChangeLog();
	}

	@Test
	public void shouldAddABDocDiffForAChange() {
		ChangeLog changeLog = givenAnNewChangeLog();
		whenABDocWithOneSpecificationIsScannedByTheChangeLog(changeLog);
		thenEnsureTheChangeLogContainsTheBDoc();
		thenEnsureTheLatestDiffInTheChangeLogContainsTheSpecification();
	}

	private void thenEnsureTheChangeLogContainsTheBDoc() {
		// TODO Auto-generated method stub
		
	}

	private void thenEnsureTheLatestDiffInTheChangeLogContainsTheSpecification() {
		// TODO Auto-generated method stub
		
	}

	private void whenABDocWithOneSpecificationIsScannedByTheChangeLog(ChangeLog changeLog) {
		BDoc bdoc = BDocTestHelper.bdocWithOneSpecification();		
	}


}
