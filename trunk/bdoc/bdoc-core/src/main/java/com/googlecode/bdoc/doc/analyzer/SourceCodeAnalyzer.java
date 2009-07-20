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

package com.googlecode.bdoc.doc.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.AbstractProcessor;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

/**
 * 
 * @author Micael Vesterlund
 * 
 */
public class SourceCodeAnalyzer {

	// public static List<MethodCall> analyze(TestMethod testMethod) {
	// File file = new File(BConst.SRC_TEST_JAVA,
	// testMethod.getClass().getName().replace('.', '/') + ".java");
	// List<MethodCall> methodCalls = new ArrayList<MethodCall>();
	// List<MethodInfo> invokeProcessor = invokeProcessor(file);
	// for (MethodInfo methodInfo : invokeProcessor) {
	// if (methodInfo.getName().equals(testMethod.getName())) {
	// List<MethodInfo> methodInfos = methodInfo.getMethodInfos();
	// for (MethodInfo methodInfo2 : methodInfos) {
	// MethodCall methodCall = new MethodCall(methodInfo2.getName());
	// methodCalls.add(methodCall);
	// }
	// }
	// }
	// return methodCalls;
	// }

	public static List<MethodInfo> analyze(File file) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		List<File> files = getFileAsList(file);

		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
		LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();
		CodeAnalyzerProcessor processor = new CodeAnalyzerProcessor();
		processors.add(processor);
		task.setProcessors(processors);
		task.call();
		try {
			fileManager.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}

		return processor.getMethodInfos();
	}

	private static List<File> getFileAsList(File file) {
		List<File> files = new LinkedList<File>();
		files.add(file);
		return files;
	}

}
