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

	private ExecutiveOfficer executiveOfficer;

	@Before
	public void setupExecutiveOfficer() {
		executiveOfficer = new ExecutiveOfficer("Bob");
	}

	@Test
	public void givenAnEmptyTaskListForAnExecutiveOfficerWhenTheOfficerCreatesANewTaskThenEnsureItIsFoundInHisOrHerTaskList() {
		Task task = executiveOfficer.createTask("Register salesorder");
		assertTrue(executiveOfficer.getTaskList().contains(task));
	}

	@Test
	public void shouldBeAbleToOpenATask() {
		Task task = executiveOfficer.createTask("Register salesorder");
		executiveOfficer.openTask(task);
		assertTrue(task.isOpen());
	}

	@Test(expected = IllegalStateException.class)
	public void shouldOnlyBeAbleToOpenOneTaskAtTheTime() {
		Task task1 = executiveOfficer.createTask("Register salesorder");
		executiveOfficer.openTask(task1);

		Task task2 = executiveOfficer.createTask("Register payment");
		executiveOfficer.openTask(task2);
	}

	@Test
	public void aNewExecutiveOfficerShouldNotHaveAnyTasksInHisOrHerTaskList() {
		assertTrue(executiveOfficer.getTaskList().getList().isEmpty());
	}

}
