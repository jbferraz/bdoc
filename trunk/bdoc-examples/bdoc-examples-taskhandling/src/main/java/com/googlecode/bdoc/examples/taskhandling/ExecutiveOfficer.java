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

import java.util.List;

public class ExecutiveOfficer {

	public String name;
	private TaskList taskList;

	public ExecutiveOfficer(String name) {
		this.name = name;
		this.taskList = new TaskList();
	}

	public Task createTask(String description) {
		return new Task(description, taskList);
	}

	public TaskList getTaskList() {
		return taskList;
	}

	public void start(Task task) {

		if (taskList.hasTasksInProgress()) {
			throw new IllegalStateException("There are already tasks in progress");
		}

		if (taskList.contains(task)) {
			task.start();
		} else {
			throw new IllegalArgumentException("Task [" + task + "] is not assigned to [" + name + "]");
		}
	}

	public boolean isAssignedTo(Task task) {
		return taskList.contains(task);
	}

	public void close(Task task) {
		taskList.getList().remove(task);
	}

	@Override
	public String toString() {
		return "[" + name + ", with tasklist: " + taskList + "]";
	}

	public void assign(List<Task> tasks) {
		for (Task task : tasks) {
			taskList.addTask(task);
		}		
	}
}
