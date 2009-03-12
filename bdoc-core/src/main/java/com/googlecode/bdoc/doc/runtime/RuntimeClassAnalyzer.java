/**
 * The MIT License
 * 
 * Copyright (c) 2008, 2009 @Author(s)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.googlecode.bdoc.doc.runtime;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Per Otto Bergum Christensen
 */
public class RuntimeClassAnalyzer implements MethodInterceptor {

	private Object testInstance;
	private String initialMethodCall;
	private List<MethodCall> methodCalls;

	public RuntimeClassAnalyzer(Class<?> testClass) {
		Enhancer e = new Enhancer();
		e.setSuperclass(testClass);
		e.setCallback(this);
		testInstance = e.create();
	}

	public List<MethodCall> invoke(String methodName) {
		this.initialMethodCall = methodName;
		methodCalls = new ArrayList<MethodCall>();
		try {
			testInstance.getClass().getMethod(methodName, new Class[0]).invoke(testInstance);
		} catch (Throwable e) {
			return null;
		}
		return methodCalls;
	}

	public Object intercept(Object obj, java.lang.reflect.Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object retValFromSuper = proxy.invokeSuper(obj, args);

		if (!method.getName().equals(initialMethodCall)) {
			methodCalls.add(new MethodCall(method, args));
		}

		return retValFromSuper;
	}

}
