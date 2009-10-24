package com.googlecode.meta;

import java.lang.reflect.Type;

public class ArgumentMetadata {

	private String name;

	public ArgumentMetadata(Type type) {
		// TODO Auto-generated constructor stub
	}

	public ArgumentMetadata(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
