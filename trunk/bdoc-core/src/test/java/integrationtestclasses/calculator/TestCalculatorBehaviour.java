/**
 * The MIT License
 * 
 * Copyright (c) 2008, 2009 @Author(s)
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

package integrationtestclasses.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Per Otto Bergum Christensen
 */
public class TestCalculatorBehaviour {

	private Calculator calculator;

	@Before
	public void resetCalculator() {
		calculator = new Calculator();
	}

	private void givenAValueForOperandOne(Operand operand) {
		calculator.setOperandOne(operand);
	}

	private void givenAValueForOperandTwo(Operand operand) {
		calculator.setOperandTwo(operand);
	}

	private void whenTheAddOperationIsExecuted() {
		calculator.add();
	}

	private void thenEnsureTheResultIsTheSumOfTheTwoValues(double expectedResult) {
		assertEquals(expectedResult, calculator.result(), .001);
	}

	@Test
	public void shouldAddADoubleWithAnInteger() {
		givenAValueForOperandOne(new Operand(4.5D));
		givenAValueForOperandTwo(new Operand(10));
		whenTheAddOperationIsExecuted();
		thenEnsureTheResultIsTheSumOfTheTwoValues(14.5D);
	}

@Test
public void shouldAddNumbers() {
	addExample(2, 2, 4);
	addExample(2, 3, 5);
	addExample(4, -4, 0);

}

public void addExample(int operand1, int operand2, int result) {
	givenAValueForOperandOne(new Operand(operand1));
	givenAValueForOperandTwo(new Operand(operand2));
	whenTheAddOperationIsExecuted();
	thenEnsureTheResultIsTheSumOfTheTwoValues(result);
}

/*
 * Given a value for operand one and a value for operand two 
 * When the add operation is executed 
 * Then ensure the result is the sum of the two values 
 * 
 * | operand1 | operand2 | result |
 * -------------------------------- 
 * | 2        | 2        | 4      |
 * | 2        | 3        | 5      |
 * | 4        | -4		 | 0      |
 * 
 */
	
	
	
}
