package com.googlecode.bdoc.sandbox.apptest;

@SuppressWarnings("unchecked")
public class ExperimentalScenarioSupport<T> {
	T given = createGiven();
	T and = createAnd();
	T when = createWhen();
	T then = createThen();;
	
	public T createGiven() {
		return (T)this;
	}
	public T createAnd() {
		return (T)this;
	}
	public T createWhen() {
		return (T)this;
	}
	public T createThen() {
		return (T)this;
	}
}
