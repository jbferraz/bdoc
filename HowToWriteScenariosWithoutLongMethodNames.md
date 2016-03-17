One way to write Scenarios are to write a test on the form given..When..Then. BDoc will translate this into:
  * Given.. .
  * When.. .
  * Then.. .
Micael Vesterlund pointed out the problem with this standard approach, to long method names, and proposed an advanced scenario specification:
```
    // STANDARD
    @Test
    public void givenAStackContainingItemsWhenPeekIsCalledThenTheStackShouldProvideTheValueOfTheMostRecentPushedValueAndThatValueShouldRemainInTheStack {
    .. .
}
```

```
    // ADVANCED 
    private void givenAStackWithTwoPushedValues(String value1, String value2) {
        stack = new Stack<String>();
        stack.push(value1);
        stack.push(value2);
    }

    private String whenPopIsCalled() {
        return stack.pop();
    }

    private void thenTheLastItemPushedShouldBeReturned(String pushed, String poped) {
        assertThat(pushed, equalTo(poped));
    }

    private void thenTheValueShouldNotRemainInTheStack(int sizeBefore, int sizeAfter) {
        assertThat(sizeAfter, equalTo(sizeBefore - 1));
    }

    @Test
    public void shouldPopSecondPushedValueFirst() {
        givenAStackWithTwoPushedValues(VALUE_1, VALUE_2);
        int sizeBefore = stack.size();
        String poped = whenPopIsCalled();
        int sizeAfter = stack.size();
        thenTheLastItemPushedShouldBeReturned(VALUE_2, poped);
        thenTheValueShouldNotRemainInTheStack(sizeBefore, sizeAfter);
    } 
```
This is now the specification for how to write advanced scenarios in BDoc. The criteria for letting BDoc know that an advanced scenario is used is to let the postfix of the test be _Behaviour_. It might be a good idea to use the RefClass annotation to connect the test class with the correct implementation class. E.g.
```
@RefClass( Stack.class )
public class TestStackBehavior {
```
Read more on HowtoChangeDefaultGeneratedClassNames.