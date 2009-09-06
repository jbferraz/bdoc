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
import static com.googlecode.bdoc.doc.util.JavaCodeUtil.argumentNames;
import static java.util.Arrays.asList;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.googlecode.bdoc.BDocException;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TableColumn;
import com.googlecode.bdoc.doc.domain.TableRow;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.Scenario.Part;

public class RootMethodCallbackAnalyzer implements MethodInterceptor {

	private static final String CREATE_SCENARIO_KEYWORD = "createScenarioKeyword";
	private TestClassProxyWrapper rootTestClassProxy;
	private TestMethod testMethod;
	private List<Scenario> scenarios;
	private List<TestTable> testTables;

	private Scenario currentScenario;
	private TestTable currentTestTable;
	private Map<Object, String> methodCalls = new HashMap<Object, String>();
	private File srcTestJava;

	private boolean debugToSystemOut = false;

	public RootMethodCallbackAnalyzer(TestClassProxyWrapper rootTestClassProxy, TestMethod testMethod, List<Scenario> scenarios,
			List<TestTable> testTables, File javaTestSourceDir) {
		this.testMethod = testMethod;
		this.scenarios = scenarios;
		this.testTables = testTables;
		this.rootTestClassProxy = rootTestClassProxy;
		this.srcTestJava = javaTestSourceDir;
	}

	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {

		if (isScenarioKeywordFactory(method)) {

			if (0 == args.length) {
				throw new BDocException("Scenario keyword factory method [" + method.getName()
						+ "] is required to have one argument ('localized keyword [String]' ), number of arguments was ["
						+ args.length + "]");
			}

			String keyword = method.getName().replace(CREATE_SCENARIO_KEYWORD, "");
			String localizedKeyword = String.valueOf(args[0]);

			return forClass(testMethod.clazz()).createProxyWith(new ScenarioKeywordAnalyzer(keyword, localizedKeyword));
		} else if (isExampleKeywordFactory(method)) {

			if (1 != args.length) {
				throw new BDocException("Example keyword factory method [" + method.getName()
						+ "] is required to have arguments ('scenario keyword [String]', 'indented [true|false]')");
			}

			String keyword = String.valueOf(args[0]);

			return forClass(testMethod.clazz()).createProxyWith(new ExampleKeywordAnalyzer(keyword));
		} else {
			Object returnValueFromSuper = proxy.invokeSuper(object, args);
			methodCalls.put(returnValueFromSuper, method.getName());
			return returnValueFromSuper;
		}
	}

	private void debug(String msg) {
		if (debugToSystemOut) {
			System.out.println(msg);
		}

	}

	private boolean isScenarioKeywordFactory(Method method) {
		boolean startsWithScenarioKeyword = method.getName().startsWith(CREATE_SCENARIO_KEYWORD);

		return startsWithScenarioKeyword && method.getReturnType().isAssignableFrom(testMethod.clazz());
	}

	private boolean isExampleKeywordFactory(Method method) {
		return method.getName().startsWith("createExampleKeyword") && method.getReturnType().isAssignableFrom(testMethod.clazz());
	}

	// ---------------------------------------------------------------------------------------------------------------------------

	public class ExampleKeywordAnalyzer extends AbstractKeywordAnalyzer {

		public ExampleKeywordAnalyzer(String keyword) {
			super(keyword);
		}

		@Override
		void addPartFrom(String methodName, List<? extends Object> args) {
			if (null == currentTestTable) {
				currentTestTable = new TestTable(methodName);
				testTables.add(currentTestTable);
				for (String argName : argumentNames(new TestClass(testMethod.clazz()), methodName, srcTestJava)) {
					currentTestTable.addHeaderColumn(new TableColumn(argName));
				}
			}
			addRow(args);
		}

		private void addRow(List<? extends Object> args) {
			TableRow tableRow = new TableRow();
			for (Object arg : args) {
				tableRow.addColumn(new TableColumn(arg));
			}
			currentTestTable.addRow(tableRow);
		}
	}

	//------------------------------------------------------------------------------------------------------
	
	public class ScenarioKeywordAnalyzer extends AbstractKeywordAnalyzer {
		boolean indented;
		String keyword;

		public ScenarioKeywordAnalyzer(String keyword, String localizedKeyword) {
			super(localizedKeyword);
			this.keyword = keyword;
			this.indented = !asList("Given", "When", "Then").contains(keyword);
			debug("Analyzer: [keyword = " + keyword + "] [ localizedKeyword = " + localizedKeyword + "] [indented = " + indented + "]");
		}

		@Override
		@SuppressWarnings("unchecked")
		void addPartFrom(String methodName, List<? extends Object> args) {
			debug(localizedKeyword + "." + methodName);

			Part scenarioPart = new Part(localizedKeyword + " " + methodName);
			for (Object arg : args) {
				if (arg instanceof Collection) {
					Collection collection = (Collection) arg;

					if (methodCalls.containsKey(arg)) {
						scenarioPart.appendListArgument(methodCalls.get(arg), collection );
					}
				} else {
					scenarioPart.appendArgument(arg);
				}
			}

			if (null == currentScenario) {
				currentScenario = new Scenario(asList(scenarioPart));
				scenarios.add(currentScenario);
			} else {
				if (keyword.equals("Given")) {
					currentScenario = new Scenario(asList(scenarioPart));
					scenarios.add(currentScenario);
				} else {
					currentScenario.addPart(scenarioPart, indented);
				}
			}
		}
	}

	// --------------------------------------------------------------------------------------
	public abstract class AbstractKeywordAnalyzer implements MethodInterceptor {
		protected String localizedKeyword;

		public AbstractKeywordAnalyzer(String localizedKeyword) {
			this.localizedKeyword = localizedKeyword;
		}

		public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {

			if (!isScenarioKeywordFactory(method) && !isExampleKeywordFactory(method)) {
				addPartFrom(method.getName(), asList(args));
			}

			if (rootTestClassProxy.hasProxy()) {
				return rootTestClassProxy.invoke(method, args);
			}
			return proxy.invokeSuper(object, args);
		}

		abstract void addPartFrom(String methodName, List<? extends Object> args);
	}

}
