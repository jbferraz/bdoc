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

package com.googlecode.bdoc.doc.domain;

import org.apache.commons.lang.Validate;

/**
 * @author Per Otto Bergum Christensen
 */
public interface UserStoryDescription {

	public static class Narrative {
		String role;
		String action;
		String benefit;

		public Narrative(String role, String action, String benefit) {
			Validate.notNull(role,"role");
			Validate.notNull(action,"action");
			Validate.notNull(benefit,"benefit");
			
			this.role = role;
			this.action = action;
			this.benefit = benefit;
		}

		public String getRole() {
			return role;
		}

		public String getAction() {
			return action;
		}

		public String getBenefit() {
			return benefit;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Narrative) {
				Narrative narrative = (Narrative) obj;
				return narrative.role.equals(role) && narrative.action.equals(action) && narrative.benefit.equals(benefit);
			}
			return false;
		}
	}

	Integer getId();

	String getTitle();

	Narrative getNarrative();
}
