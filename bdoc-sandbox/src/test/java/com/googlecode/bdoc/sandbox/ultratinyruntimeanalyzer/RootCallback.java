package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import static java.util.Arrays.asList;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class RootCallback implements MethodInterceptor {

	private String name;
	private Class<?> testClass;
	private Scenario scenario;

	public RootCallback(String name, Class<?> testClass, Scenario scenario) {
		this.name = name;
		this.testClass = testClass;
		this.scenario = scenario;
	}

	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println(name + ": " + method.getName());
		
		Object retValFromSuper = proxy.invokeSuper(object, args);
		
		if (method.getName().equals("createGiven") && (name.equals("root"))) {
			return NewUltraTinyRuntimeAnalyzer.createProxy(testClass, "given", scenario);
		}
		if (method.getName().equals("createAnd") && (name.equals("root"))) {
			return NewUltraTinyRuntimeAnalyzer.createProxy(testClass, "and", scenario);
		}
		if (name.equals("given") && (!asList("createGiven", "createAnd").contains(method.getName()))) {
			scenario.addGiven( method.getName() );
		}
		if (name.equals("and") && (!asList("createGiven", "createAnd").contains(method.getName()))) {
			scenario.addAnd( method.getName() );
		}
		return retValFromSuper;
	}

}
