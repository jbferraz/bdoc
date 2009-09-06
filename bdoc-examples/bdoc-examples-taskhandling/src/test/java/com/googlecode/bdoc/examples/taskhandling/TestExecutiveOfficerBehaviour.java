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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

@Ref(Story.TASKTRACKING)
public class TestExecutiveOfficerBehaviour extends ExampleSupport<TestExecutiveOfficerBehaviour> {

	private ExecutiveOfficer currentOfficer;
	private Task currentTask;

	@Test
	public void aNewExecutiveOfficerShouldNotHaveAnyTasksInHisOrHerTaskList() {
		ExecutiveOfficer officer = new ExecutiveOfficer("Bob");
		assertTrue(officer.getTaskList().getList().isEmpty());
	}

	@Test
	public void defaultAssignmentOfTasksIsToTheCreatorOfATask() {
		given.officer(new ExecutiveOfficer("Bob"));
		when.theOfficerCreatesATaskWithDescription("*Register salesorder*");
		then.ensureTheTaskIsAssignedToTheOfficer(currentOfficer);
	}

	void officer(ExecutiveOfficer officer) {
		this.currentOfficer = officer;
	}

	void theOfficerCreatesATaskWithDescription(String description) {
		currentTask = currentOfficer.createTask(description);
	}

	void ensureTheTaskIsAssignedToTheOfficer(ExecutiveOfficer officer) {
		assertTrue(officer.isAssignedTo(currentTask));
	}

	@Test
	public void aTaskShouldBeMarkedWithInprogressWhenItIsStartedByAnExecutiveOfficer() {
		ExecutiveOfficer officer = new ExecutiveOfficer("Bob");
		Task task = officer.createTask("Register salesorder");
		officer.start(task);
		assertTrue(task.getInProgress());
	}

	@Test
	public void aClosedTaskShouldBeRemovedFromTheTasklist() {
		given.officer(new ExecutiveOfficer("Bob"));
		and.theOfficerCreatesATaskWithDescription("*Register salesorder*");
		and.theOfficerStartsTheTask(currentTask);
		when.theOfficerClosesTheTask(currentTask);
		then.ensureTheTaskIsRemovedFromTheExecutiveOfficer(currentOfficer);
	}

	void theOfficerStartsTheTask(Task task) {
		currentOfficer.start(task);
	}

	void theOfficerClosesTheTask(Task task) {
		currentOfficer.close(task);
	}

	void ensureTheTaskIsRemovedFromTheExecutiveOfficer(ExecutiveOfficer officer) {
		assertFalse(officer.getTaskList().getList().contains(currentTask));
	}

	@Test
	public void shouldHaveAnEstimatedEtcEqualToTheSumOfTasksAssigned() {
		given.officer(new ExecutiveOfficer("Bob"));
		and.theOfficerIsAssigned(tasksForHandlingAStandardSalesOrder());
		then.ensureEstimatedEtcInMinutesEquals(12);
	}

	void theOfficerIsAssigned(List<Task> tasks) {
		currentOfficer.assign( tasks );
	}

	List<Task> tasksForHandlingAStandardSalesOrder() {
		List<Task> list = new ArrayList<Task>();
		list.add(new Task("Register salesorder",4));
		list.add(new Task("Check stock",2));
		list.add(new Task("Print and finish order",6));
		return list;
	}

	void ensureEstimatedEtcInMinutesEquals(int expectedEtc) {
		assertEquals(expectedEtc, currentOfficer.getTaskList().getEtc());
	}

}
