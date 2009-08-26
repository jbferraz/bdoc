package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import net.sf.cglib.proxy.Enhancer;

public class NewUltraTinyRuntimeAnalyzer {

	private Object testInstance;
	private Scenario scenario = new Scenario();

	public void analyze(Class<?> testClass, String testMethod) throws Exception {
		testInstance = createProxy(testClass, "root",scenario);
		testClass.getMethod(testMethod, new Class[0]).invoke(testInstance);
	}

	public static Object createProxy(Class<?> testClass, String name, Scenario scenario) {
		Enhancer e = new Enhancer();
		e.setSuperclass(testClass);
		e.setCallback(new RootCallback(name, testClass, scenario));

		return e.create();
	}

	public Scenario getScenario() {
		return scenario;
	}

}
