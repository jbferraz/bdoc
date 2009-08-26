package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import java.lang.reflect.Method;

public class WhenCallback extends Callback {

	public WhenCallback(Class<?> testClass, Scenario scenario) {
		super( testClass, scenario );
	}

	protected void register(Method method) {
		scenario.addWhen( method.getName() );
	}

}
