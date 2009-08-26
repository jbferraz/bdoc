package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
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

		if (method.getName().equals("createGiven")) {
			return createProxy(new GivenCallback(testClass, scenario));

		}
		if (method.getName().equals("createWhen")) {
			return createProxy(new WhenCallback(testClass, scenario));
		}

		if (method.getName().equals("createThen")) {
			return createProxy(new ThenCallback(testClass, scenario));
		}
		
		if (method.getName().equals("createAnd")) {
			return createProxy(new AndCallback(testClass, scenario));
		}
		return retValFromSuper;
	}

	public static Object createProxy(Callback callback) {
		Enhancer e = new Enhancer();
		e.setSuperclass(callback.getTestClass());
		e.setCallback(callback);
		return e.create();
	}

}
