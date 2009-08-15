package com.googlecode.bdoc.doc.analyzer;

import java.util.List;

public class ClassInfo {

	private String name;

	private List<MethodInfo> methods;

	public ClassInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<MethodInfo> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodInfo> methods) {
		this.methods = methods;
	}
}
