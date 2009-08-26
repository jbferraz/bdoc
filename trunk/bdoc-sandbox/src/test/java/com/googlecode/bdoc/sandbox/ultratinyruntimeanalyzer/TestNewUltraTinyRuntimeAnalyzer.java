package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestNewUltraTinyRuntimeAnalyzer {

	private NewUltraTinyRuntimeAnalyzer analyzer;

	@Before
	public void resetAnalyzer() {
		this.analyzer = new NewUltraTinyRuntimeAnalyzer();
	}
	
	@Test
	public void shouldFindASingleGivenCriteria() throws Exception {	
		analyzer.analyze(TargetTezt.class, "testWithASingleGivenCriteria");
		assertEquals("criteria", analyzer.getScenario().getGiven().get(0) );
	}
	
	@Test
	public void shouldFindTwoGivenCriterias() throws Exception {
		analyzer.analyze(TargetTezt.class, "testWithATwoGivenCriterias");

		Scenario scenario = analyzer.getScenario();
		assertTrue(scenario.getGiven().contains("criteriaA"));
		assertTrue(scenario.getAndGiven().contains("criteriaB"));
	}

	/**
	 * TestClass that is used as testdata
	 */
	public static class TargetTezt {

		private TargetTezt given = createGiven();
		private TargetTezt and = createAnd();

		public void testWithASingleGivenCriteria() {
			given.criteria();
		}

		void criteria() {
		}

		public void testWithATwoGivenCriterias() {
			given.criteriaA();
			and.criteriaB();
		}

		void criteriaA() {
		}

		void criteriaB() {
		}

		TargetTezt createGiven() {
			return this;
		}

		TargetTezt createAnd() {
			return this;
		}
	}

}
