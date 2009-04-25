package com.googlecode.bdoc.testsupport.excel;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestExcelExampleRunner {

	private ExcelExampleTableRunner excelExampleTableRunner = new ExcelExampleTableRunner("./src/test/resources/calc-operation-examples.xls", this);
	private List<List<Double>> arguments;

	@Test //skille ut camelcase metoder i egen bdoc-utils, lage skikkelig parent-pom, slik at alt kan deplyes samtidig.
	//kan denne skives som et scenario?
	@Ignore 
	public void shouldUseTestMethodNameToIdentifyExampleTableAndVerifyEachRowByRunningTheTestMethodWithItsArguments() {
		arguments = new ArrayList<List<Double>>();
		excelExampleTableRunner.verify("exampleOnAddition");
		assertEquals(asList(1D, 1D, 2D), arguments.get(0));
		assertEquals(asList(2D, 2D, 4D), arguments.get(1));
	}

	public void exampleOnAddition(double a, double b, double sum) {
		arguments.add(asList(a, b, sum));
	}
}
