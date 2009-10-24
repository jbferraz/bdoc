package com.googlecode.meta;

import java.util.ArrayList;
import java.util.List;

public class MethodMetadata {

	private final String name;

	private List<ArgumentMetadata> argumentMetadataList = new ArrayList<ArgumentMetadata>();

	public MethodMetadata(String name) {
		this.name = name;
	}

	public List<ArgumentMetadata> getArgumentMetadata() {
		return argumentMetadataList;
	}

	public void addArgumentMetadata(ArgumentMetadata argumentMetadata) {
		argumentMetadataList.add(argumentMetadata);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MethodMetadata)) {
			return false;
		}

		return name.equals(((MethodMetadata) obj).name);
	}
	
	@Override
	public String toString() {
		return name + ": " + argumentMetadataList;
	}

}
