package com.googlecode.meta;

import static java.util.Arrays.asList;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class MethodMetadataProcessor {

	final private Class<?> targetClass;
	final private File javaFile;
	final private List<MethodMetadata> methodMetadata = new ArrayList<MethodMetadata>();

	public MethodMetadataProcessor(Class<?> targetClass, File javaFile) {
		this.targetClass = targetClass;
		this.javaFile = javaFile;
		compile();
	}

	private void compile() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(asList(javaFile));
		CompilationTask compilationTask = compiler.getTask(null, fileManager, null, null, null, compilationUnits);

		compilationTask.setProcessors(asList(new CheckNamesProcessor(methodMetadata)));
		compilationTask.call();
	}

	public MethodMetadata inspect(String methodName) {
		MethodMetadata result = new MethodMetadata(methodName);

		Method method = method(methodName);
		for (Type type : method.getParameterTypes()) {
			result.addArgumentMetadata(new ArgumentMetadata(type));
		}

		return result;
	}

	private Method method(String methodName) {
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		throw new IllegalStateException("Didn't find method for: " + methodName);
	}

	public MethodMetadata get(String methodName) {
		return methodMetadata.get(methodMetadata.indexOf(new MethodMetadata(methodName)));
	}

}
