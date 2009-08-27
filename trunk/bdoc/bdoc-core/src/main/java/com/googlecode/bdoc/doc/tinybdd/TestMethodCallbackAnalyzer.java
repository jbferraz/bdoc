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

import static java.util.Arrays.asList;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.googlecode.bdoc.doc.domain.Scenario;
import static com.googlecode.bdoc.doc.domain.Scenario.Part;
import com.googlecode.bdoc.doc.domain.TestMethod;

public class TestMethodCallbackAnalyzer implements MethodInterceptor {

	private TestClassProxyWrapper rootTestClassProxy;
	private TestMethod testMethod;
	private List<Scenario> scenarios;

	private Scenario currentScenario;

	public TestMethodCallbackAnalyzer(TestClassProxyWrapper rootTestClassProxy, TestMethod testMethod, List<Scenario> scenarios) {
		this.testMethod = testMethod;
		this.scenarios = scenarios;
		this.rootTestClassProxy = rootTestClassProxy;
	}

	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if (isScenarioKeywordFactory(method)) {
			String keyword = method.getName().replace("create", "");
			return ProxyUtils.createProxy(testMethod.clazz(), new ScenarioKeywordAnalyzer(keyword));
		}

		return proxy.invokeSuper(object, args);
	}

	private boolean isScenarioKeywordFactory(Method method) {
		return method.getName().startsWith("create") && method.getReturnType().equals(testMethod.clazz());
	}

	// ---------------------------------------------------------------------------------------------------------------------------

	public class ScenarioKeywordAnalyzer implements MethodInterceptor {
		private String name;

		public ScenarioKeywordAnalyzer(String name) {
			this.name = name;
		}

		public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			// System.out.println("ScenarioKeywordAnalyzer [" + name + "]: " +
			// method.getName());
			if (!isScenarioKeywordFactory(method)) {
				addPart(name, method.getName());
			}

			if (rootTestClassProxy.hasProxy()) {
				return rootTestClassProxy.invoke(method, args);
			}
			return proxy.invokeSuper(object, args);
		}

		private void addPart(String keyword, String description) {
			Part scenarioPart = new Part(keyword + " " + description);

			if (null == currentScenario) {
				currentScenario = new Scenario(asList(scenarioPart));
				scenarios.add(currentScenario);
			} else {
				currentScenario.addPart(scenarioPart);
			}
		}

	}

}
