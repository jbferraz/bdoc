package com.googlecode.bdoc.sandbox;

import com.googlecode.bdoc.sandbox.apptest.AppTest2;


public class BDocRuntime {

	public static void main(String[] args) {
		log("start");

//		AppTest appTest = (AppTest) Trace.newInstance(AppTest.class);
//		appTest.testApp();
		AppTest2 appTest = (AppTest2) Trace.newInstance(AppTest2.class);
		appTest.testApp();

		log("end");
	}

	private static void log(String msg) {
		System.out.println(msg);
	}
}
