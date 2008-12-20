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

package bdddoc4j.core.report;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBddDocMacroHelper {

	@Test
	public void shouldTransformAFullClassnameToJustTheNameOfTheClass() {
		assertEquals("TheClass", new BddDocMacroHelper().formatClassname("package.subpackage.TheClass"));
	}

	@Test
	public void shouldFindTextForAGivenKey() {
		assertEquals("New user stories", new BddDocMacroHelper().text("new.user.stories"));
	}

	@Test
	public void shouldBePossibleToSetScenarioFormatter() throws Exception {
		new BddDocMacroHelper(new EachOnNewLineScenarioLinesFormatter());
	}

}
