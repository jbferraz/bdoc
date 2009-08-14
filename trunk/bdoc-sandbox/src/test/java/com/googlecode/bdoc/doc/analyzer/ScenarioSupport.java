package com.googlecode.bdoc.doc.analyzer;

@SuppressWarnings("unchecked")
public class ScenarioSupport<T> {
	T given = (T) this;
	T and = (T) this;
	T when = (T) this;
	T then = (T) this;
}
