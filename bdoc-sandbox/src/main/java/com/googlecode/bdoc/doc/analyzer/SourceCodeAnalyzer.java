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

import org.apache.maven.shared.model.fileset.FileSet;
import org.apache.maven.shared.model.fileset.util.FileSetManager;

/**
 * 
 * @author Micael Vesterlund
 * 
 */
public class SourceCodeAnalyzer {

	public static ClassInfo analyze(File file) {
		List<File> files = getFileAsList(file);

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		ClassInfo classInfo = callCompiler(files, compiler, fileManager);

		try {
			fileManager.close();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}

		deleteClasses(file);

		return classInfo;
	}

	private static ClassInfo callCompiler(List<File> files, JavaCompiler compiler, StandardJavaFileManager fileManager) {
		CompilationTask compilationTask = getCompilationTask(files, compiler, fileManager);

		LinkedList<AbstractProcessor> processors = new LinkedList<AbstractProcessor>();
		CodeAnalyzerProcessor processor = new CodeAnalyzerProcessor();
		processors.add(processor);

		compilationTask.setProcessors(processors);
		compilationTask.call();
		return processor.getClassInfo();
	}

	private static CompilationTask getCompilationTask(List<File> files, JavaCompiler compiler, StandardJavaFileManager fileManager) {
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
		return task;
	}

	private static void deleteClasses(File file) {
		File dir = file.getParentFile();
		FileSetManager fileSetManager = new FileSetManager();
		FileSet fileSet = new FileSet();
		fileSet.setDirectory(dir.getPath());
		fileSet.addInclude("*.class");

		try {
			fileSetManager.delete(fileSet);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static List<File> getFileAsList(File file) {
		List<File> files = new LinkedList<File>();
		files.add(file);
		return files;
	}

}
