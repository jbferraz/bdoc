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

package bdddoc4j.diff.domain;

import java.util.Date;
import java.util.Calendar;

public class TimeDiff {

	private final long minutes;
	private long hours = 0;
	private final Calendar time1;
	private final Calendar time2;

	public TimeDiff(Calendar time1, Calendar time2) {
		this.time1 = time1;
		this.time2 = time2;
		long totalMinutes = (time2.getTimeInMillis() - time1.getTimeInMillis()) / (60 * 1000);
		if (totalMinutes < 60) {
			minutes = totalMinutes;
		} else {
			hours = totalMinutes / 60;
			minutes = totalMinutes % 60;
		}
	}

	public boolean isLessThanOneHour() {
		return 0 == hours;
	}

	public boolean isEqualToOrGreaterThanOneHourAndLessThanOneDay() {
		return (0 < hours) && (hours < 24);
	}

	public boolean isEqualToOneDayOrMore() {
		return 24 <= hours;
	}

	public long getMinutes() {
		return minutes;
	}

	public long getHours() {
		return hours % 24;
	}

	public long getDays() {
		return hours / 24;
	}

	public Date getTime1() {
		return time1.getTime();
	}

	public Date getTime2() {
		return time2.getTime();
	}
}
