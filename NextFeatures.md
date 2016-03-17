BDoc is evolving and its next features are implemented (but not stable for the moment).

This is what to expect:
```
----------------------------------------------------------------------------------
Given the two operands 4 and 5
When the add operation is executed
Then the result should be 9
----------------------------------------------------------------------------------
Will be generated from the following: 

public class TestCalcBehaviour {

    private int operand1;
    private int operand2;
    private int result;

    @Test
    public void shouldAddNumbers() {
        givenTheTwoOperands(4, 5);
        whenTheAddOperationIsExecuted();
        thenTheResultShouldBe(9);
    }

    void givenTheTwoOperands(int i, int j) {
        this.operand1 = i;
        this.operand2 = j;
    }

    void whenTheAddOperationIsExecuted() {
        this.result = new Calc().add(operand1, operand2);
    }

    void thenTheResultShouldBe(int i) {
        assertEquals(i, result);
    }
}

----------------------------------------------------------------------------------
Example on add operation
| a | b  | sum |
----------------
| 1 | 1 |   2  |
| 2 | 2 |   4  |
| 2 | 4 |   6  |
----------------------------------------------------------------------------------

Will be generated from the following:  
 
@Test
public void shouldAddTwoNumbers() {
    exampleOnAddOperation(1,1,2);
    exampleOnAddOperation(2,2,4);
    exampleOnAddOperation(2,4,6);     
}
 
void exampleOnAddOperation(int a, int b, int sum ) {
    assertEquals( sum, calc.add( a,b) );
}
```