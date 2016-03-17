_Last update, 25.10.2009. Read more on the [News](News.md)._
# About #
> BDoc documents behaviour specified by [unit tests](http://en.wikipedia.org/wiki/Unit_testing). In its easiest form it is like [TestDox](http://agiledox.sourceforge.net), creating simple documentation from the method names in [JUnit](http://www.junit.org) test cases.

> In its more advanced form it can produce a quality report, describing userstories and behaviour implemented by the code. This will give extra value to the developers and testers of the team. It will contribute on giving attention to [unittesting](http://en.wikipedia.org/wiki/Unit_testing), [test-driven development](http://butunclebob.com/ArticleS.UncleBob.TheThreeRulesOfTdd) and [behaviour-driven development](http://behaviour-driven.org). By showing the report to the business people and testers the developers will get feedback on how they describe the domain, which contributes to code (and developers) being in sync with the [UbiquitousLanguage](http://domaindrivendesign.org/discussion/messageboardarchive/UbiquitousLanguage.html) of the project.

> A quality report giving value can be achieved by adding some thought to how a test is named, writing it as sentence describing behaviour. [DanNorh.net](http://dannorth.net/introducing-bdd) gives an excellent article on this topic. If [user stories](http://en.wikipedia.org/wiki/User_story) is used on the project it is also possible to reference the user story from the test, adding even more value to the end report.

> BDoc will structure a report by userstories, specifications and examples. The best documentation are as always examples, so look below.

> Read more about BDoc on [TheServerSide.com](http://www.theserverside.com/tt/articles/article.tss?l=BDoc)

# Example #
> The code below describes and tests the behaviour of an [ExecutiveOfficer](http://bdoc.googlecode.com/svn/trunk/bdoc-examples/bdoc-examples-taskhandling/src/main/java/com/googlecode/bdoc/examples/taskhandling/ExecutiveOfficer.java). The testcase uses a [Reference](http://bdoc.googlecode.com/svn/trunk/bdoc-examples/bdoc-examples-taskhandling/src/test/java/com/googlecode/bdoc/examples/taskhandling/Ref.java) to a [Story](http://bdoc.googlecode.com/svn/trunk/bdoc-examples/bdoc-examples-taskhandling/src/test/java/com/googlecode/bdoc/examples/taskhandling/Story.java), one scenario, one statement and a few specifications. Running BDoc on this code, together with testcases for [Task](http://bdoc.googlecode.com/svn/trunk/bdoc-examples/bdoc-examples-taskhandling/src/test/java/com/googlecode/bdoc/examples/taskhandling/TestTask.java) and [TaskList](http://bdoc.googlecode.com/svn/trunk/bdoc-examples/bdoc-examples-taskhandling/src/test/java/com/googlecode/bdoc/examples/taskhandling/TestTaskList.java), will produce the report shown below. BDoc now requires a [tiny bdd-framework](http://bdoc.googlecode.com/svn/trunk/bdoc-examples/bdoc-examples-taskhandling/src/test/java/com/googlecode/bdoc/examples/taskhandling/ExampleSupport.java) in order to extract example data. The framework (or the class if you like) must be implemented by your project, in the relevant language. BDoc needs this construction in order to inject proxies into the test, while it is running, analyzing method calls and data for the report.
```
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
```

Report generated by BDoc:

[![](http://www.perottobergumchristensen.com/bdoc/task-handling.jpg)](http://www.perottobergumchristensen.com/bdoc/site/bdoc)