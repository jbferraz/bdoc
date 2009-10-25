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

package com.googlecode.bdoc.doc.util.apt;

import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

import org.apache.maven.shared.model.fileset.FileSet;
import org.apache.maven.shared.model.fileset.util.FileSetManager;

import com.googlecode.bdoc.BDocException;

public class MethodMetadataFactory {

	final private List<MethodMetadata> methodMetadata = new ArrayList<MethodMetadata>();
	final private File javaFile;

	public MethodMetadataFactory(File javaFile) {
		this.javaFile = javaFile;
		compile();
	}

	private void compile() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(asList(javaFile));
		CompilationTask compilationTask = compiler.getTask(null, fileManager, null, null, null, compilationUnits);

		compilationTask.setProcessors(asList(new MethodMetadataProcessor(methodMetadata)));
		compilationTask.call();
		deleteClasses(javaFile);
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
	
	public MethodMetadata get(String methodName) {
		int index = methodMetadata.indexOf(new MethodMetadata(methodName));
		if( index < 0 ) {
			throw new BDocException( "Method not found: " + methodName + ", for file: " + javaFile );
		}
		return methodMetadata.get(index);
	}

	public static File source(Class<?> c, File srcTestJava) {
		return new File(srcTestJava, c.getName().replace('.', '/') + ".java");
	}

}
