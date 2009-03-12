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

package com.googlecode.bdoc.doc.runtime.testdata;

import static org.junit.Assert.*;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;

/**
 * Example to demonstrate and test creation of test tables.
 * 
 * @author Per Otto Bergum Christensen
 */
public class TestConvertUtilsBehaviour {

	@Test
	public void shouldConvertFromPrimitivToPrimitiv() {
		assertConversion(Boolean.FALSE, Integer.class, 0);
		assertConversion(Boolean.TRUE, Integer.class, 1);
		assertConversion(Integer.MAX_VALUE, Double.class, new Double(Integer.MAX_VALUE));
		assertConversion(new Double(4444), Integer.class, new Integer(4444));

	}

	void assertConversion(Object sourceValue, Class<?> targetClass, Object expectedValue) {
		Object outputValue = ConvertUtils.convert(sourceValue, targetClass);
		assertEquals("Conversion from [" + sourceValue.getClass().getName() + "] to [" + targetClass.getName() + "] failed", expectedValue,
				outputValue);
	}
}
