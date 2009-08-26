package com.googlecode.bdoc.sandbox.ultratinyruntimeanalyzer;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

	private List<String> given = new ArrayList<String>();
	private List<String> andGiven = new ArrayList<String>();
	private List<String> when = new ArrayList<String>();
	private List<String> then = new ArrayList<String>();

	public List<String> getGiven() {
		return given;
	}

	public void addGiven(String criteria) {
		given.add(criteria);
	}

	public List<String> getAndGiven() {
		return andGiven;
	}

	public void addAnd(String criteria) {
		andGiven.add(criteria);
	}

	public List<String> getWhen() {
		return when;
	}

	public List<String> getThen() {
		return then;
	}

	public void addWhen(String name) {
		this.when.add(name);
	}

	public void addThen(String name) {
		this.then.add(name);
	}
}
