> The code below is added to the testcode of the [ExecutiveOfficer](http://bdoc.googlecode.com/svn/trunk/bdoc-examples/bdoc-examples-taskhandling/src/main/java/com/googlecode/bdoc/examples/taskhandling/ExecutiveOfficer.java). Running the diff-report on the code will produce the following **[DIFF-REPORT](http://bdoc.googlecode.com/files/bdoc-diff-25-08-08.html)**
```
	@Test
	public void givenATaskStartedByAnExecutiveOfficerWhenTheOfficerClosesTheTaskThenEnsureItIsRemovedFromTheTaskList() {
		Task task = bob.createTask("Register salesorder");
		bob.start(task);
		bob.close( task );
		assertTrue(bob.getTaskList().getList().isEmpty());		
	}
```