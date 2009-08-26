package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import java.lang.reflect.Method;

public class GivenCallback extends Callback {

	public GivenCallback(Class<?> testClass, Scenario scenario) {
		super( testClass, scenario );
	}

	protected void register(Method method) {
		scenario.addGiven( method.getName() );
	}
}
