package com.googlecode.bdoc.doc.analyzer;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {

	private String name;

	private List<MethodInfo> methods;

	public ClassInfo(String name) {
		this.name = name;
		methods = new ArrayList<MethodInfo>();
	}

	public String getName() {
		return name;
	}

	public List<MethodInfo> getMethods() {
		return methods;
	}

	public void addMethodInfo(MethodInfo methodInfo) {
		methods.add(methodInfo);
	}
}
