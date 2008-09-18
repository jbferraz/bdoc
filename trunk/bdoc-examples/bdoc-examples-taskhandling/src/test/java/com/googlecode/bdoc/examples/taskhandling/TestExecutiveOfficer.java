/**
 * The MIT License
 * 
 * Copyright (c) 2008 Per Otto Bergum Christensen
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.googlecode.bdoc.examples.taskhandling;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

@Ref(Story.TASKTRACKING)
public class TestExecutiveOfficer {

private ExecutiveOfficer bob;

@Before
public void setupExecutiveOfficer() {
	bob = new ExecutiveOfficer("Bob");
}

@Test // Scenario - given[]When[]Then[]
public void givenAnOfficerWithNoTasksWhenTheOfficerCreatesANewTaskThenEnsureItIsAssignedToTheOfficer() {
	Task task = bob.createTask("Register salesorder");
	assertTrue(bob.isAssignedTo(task));
}

@Test // Specification - should[]
public void shouldBeAbleToStartATaskAssigned() {
	Task task = bob.createTask("Register salesorder");
	bob.start(task);
	assertTrue(task.isInProgress());
}

@Test // Statement - []
public void aNewExecutiveOfficerShouldNotHaveAnyTasksInHisOrHerTaskList() {
	assertTrue(bob.getTaskList().getList().isEmpty());
}


@Test
public void givenATaskStartedByAnExecutiveOfficerWhenTheOfficerClosesTheTaskThenEnsureItIsRemovedFromTheTaskList() {
	Task task = bob.createTask("Register salesorder");
	bob.start(task);
	bob.close( task );
	assertTrue(bob.getTaskList().getList().isEmpty());		
}	

	@Test(expected = IllegalArgumentException.class) 
	public void shouldNotBeAbleToStartATaskAssignedToOthers() {
		ExecutiveOfficer sally = new ExecutiveOfficer("sally");
		Task task = sally.createTask("Pay salary");
		bob.start(task);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldOnlyBeAbleToStartOneTaskAtTheTime() {
		Task task1 = bob.createTask("Register salesorder");
		bob.start(task1);

		Task task2 = bob.createTask("Register payment");
		bob.start(task2);
	}

}
