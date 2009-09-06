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

import org.apache.commons.beanutils.MethodUtils;

import com.googlecode.bdoc.BDocException;
import com.googlecode.bdoc.doc.domain.TestMethod;

public class TestClassProxyWrapper {

	private Object proxy;

	public void setProxy(Object proxy) {
		this.proxy = proxy;
	}

	public void runTest(TestMethod testMethod) {
		try {
			MethodUtils.invokeExactMethod(proxy, testMethod.getName(), null);
		} catch (Exception e) {
			throw new BDocException(e);
		}
	}

	public boolean hasProxy() {
		return null != proxy;
	}

	public Object invoke(Method method, Object[] args) {
		try {			
			method.setAccessible(true);
			return method.invoke(proxy, args);
		} catch (Exception e) {
			throw new BDocException(e);
		}
	}

}
