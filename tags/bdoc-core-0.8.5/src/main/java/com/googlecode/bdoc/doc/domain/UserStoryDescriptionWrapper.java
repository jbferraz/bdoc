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

package com.googlecode.bdoc.doc.domain;

import static org.apache.commons.beanutils.MethodUtils.invokeMethod;

import com.googlecode.bdoc.doc.util.ConstantNameToSentenceTranslator;

/**
 * Wrapper for UserStoryDescription, that lets the developer defines stories
 * without compile-time-dependencies the BddDoc4J framework.
 * 
 * The story instance supplied must implement:
 * 
 * <code>
 * 		public Integer id();
 * 		public String name();
 * 		public[] String narrative();
 * </code>
 * 
 * @author Per Otto Bergum Christensen
 * 
 */
public class UserStoryDescriptionWrapper implements UserStoryDescription {

	private Object story;

	/**
	 * Constructor
	 * 
	 * @param story
	 *            instance.
	 */
	public UserStoryDescriptionWrapper(Object story) {
		this.story = story;
	}

	public Narrative getNarrative() {
		String[] narrative = (String[]) property("narrative");
		return new Narrative(narrative[0], narrative[1], narrative[2]);
	}

	public String getTitle() {
		return ConstantNameToSentenceTranslator.translate(String.valueOf(property("name")));
	}

	public Integer getId() {
		return (Integer) property("id");
	}

	private Object property(String beanProperty) {
		try {
			return invokeMethod(story, beanProperty, null);
		} catch (Exception e) {
			throw new RuntimeException("Error getting property [" + beanProperty + "] from [" + story.getClass().getName() + "]", e);
		}
	}

}
