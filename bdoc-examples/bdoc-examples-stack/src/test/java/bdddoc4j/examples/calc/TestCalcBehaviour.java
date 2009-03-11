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

package bdddoc4j.examples.calc;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Demonstrates that a class also could have an integrationtest with behaviour
 * 
 * @author Per Otto Bergum Christensen
 */
public class TestCalcBehaviour {

	private int operand2;
	private int operand1;
	private int result;

	@Test
	public void shouldAddNumbers() {
		givenOperandOneIs(5);
		givenOperandTwoIs(4);
		whenTheAddOperationIsExecuted();
		thenTheResultShouldBe(9);
	}

	void givenOperandOneIs(int i) {
		this.operand1 = i;
	}

	void givenOperandTwoIs(int i) {
		this.operand2 = i;
	}

	void whenTheAddOperationIsExecuted() {
		this.result = new Calc().add(operand1, operand2);
	}

	void thenTheResultShouldBe(int i) {
		assertEquals(i, result);
	}


//	@Test
//	public void givenSixAddedWithFourWhenTheResultIsSubtractWithFiveEnsureTheResultIsFive() {
//
//		Assert.assertEquals(5, calc.subtract(calc.add(6, 4), 5));
//	}
}
