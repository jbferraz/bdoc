package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import static java.util.Arrays.asList;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


public abstract class Callback implements MethodInterceptor {

	protected Scenario scenario;
	protected Class<?> testClass;

	public Callback(Class<?> testClass, Scenario scenario) {
		this.scenario = scenario;
		this.testClass = testClass;
	}

	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if (!asList("createGiven","createAnd","createWhen","createThen").contains(method.getName())) {
			register(method);
		}
		
		return proxy.invokeSuper(object, args);
	}

	public Class<?> getTestClass() {
		return testClass;
	}

	abstract void register(Method method);
}