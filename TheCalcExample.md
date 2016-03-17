# Addition #
### As a person ###
### I want a calculator to add two numbers ###
### so that I avoid silly math mistakes ###

Scenarios

  * Given the two values 4 and 5
  * When the add operation is executed
  * Then the result should be 9

Specifications

  * Calc
> > o Should add two numbers

Test tables

Example on addition
| **Value 1** | **Value 2** | **Expected sum** |
|:------------|:------------|:-----------------|
| 1           |	1 	         | 2                |
| 1           |	2 	         | 3                |
| 2           |	2 	         | 4                |


## The report above is generated from the following code: ##

```
@Ref(Story.ADDITION)
public class TestCalcBehaviour {

	private Calc calc = new Calc();

	private int value1;
	private int value2;
	private int sum;

	@Test
	public void shouldAddTwoNumbers() {
		givenTheTwoValues(4, 5);
		whenTheAddOperationIsExecuted();
		thenTheResultShouldBe(9);

		exampleOnAddition(1, 1, 2);
		exampleOnAddition(1, 2, 3);
		exampleOnAddition(2, 2, 4);
	}

	void givenTheTwoValues(int i, int j) {
		this.value1 = i;
		this.value2 = j;
	}

	void whenTheAddOperationIsExecuted() {
		this.sum = calc.add(value1, value2);
	}

	void thenTheResultShouldBe(int expectedSum) {
		assertEquals(expectedSum, sum);
	}

	void exampleOnAddition(int value1, int value2, int expectedSum) {
		assertEquals(expectedSum, new Calc().add(value1, value2));
	}
}
```

### How to configre BDoc for the report above ###
For now BDoc must be configured to make the report above:
```
<configuration>
	<storyRefAnnotationClassName>
		com.googlecode.bdoc.calc.bdoc.Ref
	</storyRefAnnotationClassName>
	<scenarioAnalyzer>dynamic</scenarioAnalyzer>
</configuration>
```

You could also take a look at the sample code: http://bdoc.googlecode.com/svn/trunk/bdoc-examples/bdoc-examples-calc/

Normal test starting with Test or Ending with Test will not be analyzed for the behaviour above. To trigger BDoc to take an extra look you ned to name your test "TestYourClassBehaviour".