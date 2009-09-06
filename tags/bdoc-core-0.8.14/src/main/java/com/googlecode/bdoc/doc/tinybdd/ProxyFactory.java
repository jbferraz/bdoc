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

package com.googlecode.bdoc.doc.tinybdd;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyFactory<T> {

	private static PassThroughMethodInterceptor passThroughMethodInterceptor = new PassThroughMethodInterceptor();
	private static CallbackFilter callbackFilterOutMethods = new CallbackFilterWithoutFinalizeCalls();

	private Class<T> clazz;
	
	public ProxyFactory(Class<T> clazz) {
		this.clazz = clazz;
	}

	public static <T> ProxyFactory<T> forClass(Class<T> clazz) {
		return new ProxyFactory<T>(clazz);
	}	
	
	@SuppressWarnings("unchecked")
	public T createProxyWith( MethodInterceptor callback ) {
		Enhancer e = new Enhancer();
		e.setSuperclass(clazz);
		e.setCallbacks(new Callback[] { passThroughMethodInterceptor, callback });
		e.setCallbackFilter(callbackFilterOutMethods);

		return (T) e.create();
	}

	public static class PassThroughMethodInterceptor implements MethodInterceptor {
		public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			return proxy.invokeSuper(object, args);
		}
	}

	public static class CallbackFilterWithoutFinalizeCalls implements CallbackFilter  {
		public int accept(Method method) {
			if (method.getName().equals("finalize")) {
				return 0;
			}
			return 1;
		}
	}

}
