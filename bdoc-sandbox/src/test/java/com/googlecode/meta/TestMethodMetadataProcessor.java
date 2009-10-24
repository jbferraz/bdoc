package com.googlecode.meta;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import com.googlecode.bdoc.sandbox.BConst;

public class TestMethodMetadataProcessor {

	@Test
	public void shouldRetreiveMethodMetadataForASpecificMethod() {

		MethodMetadata methodMetadata = new MethodMetadataProcessor(TargetClass.class, source(TargetClass.class)).get("tableExample");

		System.out.println( "methodMetadata: " + methodMetadata );
		assertEquals("msg", methodMetadata.getArgumentMetadata().get(0).getName());
	}

	private static File source(Class<?> c) {
		return new File(BConst.SRC_TEST_JAVA, c.getName().replace('.', '/') + ".java");
	}

}
