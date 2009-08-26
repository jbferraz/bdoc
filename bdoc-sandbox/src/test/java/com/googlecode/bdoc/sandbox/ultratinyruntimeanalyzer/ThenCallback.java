package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import java.lang.reflect.Method;

public class ThenCallback extends Callback {

	public ThenCallback(Class<?> testClass, Scenario scenario) {
		super( testClass, scenario );
	}

	protected void register(Method method) {
		scenario.addThen( method.getName() );
	}

}
