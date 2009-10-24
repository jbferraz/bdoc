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

package com.googlecode.bdoc.diff.domain;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.Story;
import com.googlecode.bdoc.diff.domain.TimeDiff;

/**
 * @author Per Otto Bergum Christensen
 */
@Ref(Story.DIFF_OF_BDOCS)
public class TestTimeDiff {

	private Calendar time1 = new GregorianCalendar(2009, 10, 20);

	@Test
	public void shouldReportTimeDiffLessThanOneHour() {

		Calendar time2 = (Calendar) time1.clone();
		time2.add(Calendar.MINUTE, 59);

		TimeDiff timeDiff = new TimeDiff(time1, time2);
		assertTrue(timeDiff.isLessThanOneHour());
		assertEquals(59, timeDiff.getMinutes());
	}

	@Test
	public void shouldReportTimeDiffGreaterOrEqualToOneHourAndLessThanOneDay() {
		Calendar time2 = (Calendar) time1.clone();
		time2.add(Calendar.MINUTE, 59);
		time2.add(Calendar.HOUR, 23);

		TimeDiff timeDiff = new TimeDiff(time1, time2);
		assertTrue(timeDiff.isEqualToOrGreaterThanOneHourAndLessThanOneDay());
		assertEquals(59, timeDiff.getMinutes());
		assertEquals(23, timeDiff.getHours());
	}

	@Test
	public void shouldReportTimeDiffEqualToOneDayOrMore() {
		Calendar time2 = (Calendar) time1.clone();
		time2.add(Calendar.MINUTE, 0);
		time2.add(Calendar.DAY_OF_WEEK, 1);

		TimeDiff timeDiff = new TimeDiff(time1, time2);
		assertTrue(timeDiff.isEqualToOneDayOrMore());
		assertEquals(0, timeDiff.getMinutes());
		assertEquals(0, timeDiff.getHours());
		assertEquals(1, timeDiff.getDays());
	}
}
