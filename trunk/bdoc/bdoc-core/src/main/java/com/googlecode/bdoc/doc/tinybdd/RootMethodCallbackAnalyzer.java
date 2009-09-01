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

import static com.googlecode.bdoc.doc.tinybdd.ProxyFactory.forClass;
import static java.util.Arrays.asList;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.googlecode.bdoc.BDocException;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.Scenario.Part;

public class RootMethodCallbackAnalyzer implements MethodInterceptor {

	private TestClassProxyWrapper rootTestClassProxy;
	private TestMethod testMethod;
	private List<Scenario> scenarios;

	private Scenario currentScenario;
	private Map<Object, String> methodCallReturnValues = new HashMap<Object, String>();
	private boolean debugToSystemOut = true;

	public RootMethodCallbackAnalyzer(TestClassProxyWrapper rootTestClassProxy, TestMethod testMethod, List<Scenario> scenarios) {
		this.testMethod = testMethod;
		this.scenarios = scenarios;
		this.rootTestClassProxy = rootTestClassProxy;
	}

	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {

		if (isScenarioKeywordFactory(method)) {
			
			if (2 != args.length) {
				throw new BDocException("ScenarioKeywordFactoryMethod [" + method.getName()
						+ "] is required to have arguments ('scenario keyword [String]', 'indented [true|false]')");
			}
			
			String keyword = String.valueOf(args[0]);
			boolean indented = Boolean.TRUE.equals(args[1]);			

			return forClass(testMethod.clazz()).createProxyWith(new ScenarioKeywordAnalyzer(keyword, indented));
		} else {
			Object returnValueFromSuper = proxy.invokeSuper(object, args);
			methodCallReturnValues.put(returnValueFromSuper, method.getName());
			return returnValueFromSuper;
		}
	}

	private void debug(String msg) {
		if( debugToSystemOut ) {
			System.out.println( msg );
		}
		
	}

	private boolean isScenarioKeywordFactory(Method method) {
		return method.getName().startsWith("create") && method.getReturnType().isAssignableFrom(testMethod.clazz());
	}

	// ---------------------------------------------------------------------------------------------------------------------------

	public class ScenarioKeywordAnalyzer implements MethodInterceptor {
		private String keyword;
		private boolean indented;

		public ScenarioKeywordAnalyzer(String keyword, boolean indented) {
			this.keyword = keyword;
			this.indented = indented;
		}

		public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {

			if (!isScenarioKeywordFactory(method)) {
				addPartFrom(method.getName(), asList(args));
			}

			if (rootTestClassProxy.hasProxy()) {
				return rootTestClassProxy.invoke(method, args);
			}
			return proxy.invokeSuper(object, args);
		}

		@SuppressWarnings("unchecked")
		private void addPartFrom(String methodName, List<? extends Object> args) {
			debug( keyword + "." + methodName );
			
			Part scenarioPart = new Part(keyword + " " + methodName);
			for (Object arg : args) {
				if (arg instanceof Collection) {
					Collection collection = (Collection) arg;
					if (methodCallReturnValues.containsKey(arg)) {
						TestTable testTable = new TestTable(methodCallReturnValues.get(arg));
						testTable.addCollectionToRows(collection);
						scenarioPart.addTestTable(testTable);

						scenarioPart.appendArgument(methodCallReturnValues.get(arg));
					}
				} else {
					scenarioPart.appendArgument(arg);
				}
			}

			if (null == currentScenario) {
				currentScenario = new Scenario(asList(scenarioPart));
				scenarios.add(currentScenario);
			} else {
				currentScenario.addPart(scenarioPart, indented);
			}
		}

	}

}
