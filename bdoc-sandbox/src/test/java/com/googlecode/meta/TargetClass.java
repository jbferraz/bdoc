package com.googlecode.meta;

import org.junit.Test;

public class TargetClass {

	
	@Test
	public void aTest() {
		tableExample( "est", 2,4 );
	}
	
	public void tableExample( String msg, int input, int expected ) {
		privateTableExample();
	}
	
	private void privateTableExample() {
		
	}
}
