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

package com.googlecode.bdoc.diff.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.UserStory;


public class BDocDiff {

	private final ProjectDiff projectDiff;
	private TimeDiff docTimeDiff;
	private final ListDiff<UserStory, UserStoryDiff> userStories;
	private final GeneralBehaviourDiff generalBehaviourDiff;

	public BDocDiff(BDoc oldVersion, BDoc newVersion) {
		Validate.notNull(oldVersion, "oldVersion");
		Validate.notNull(newVersion, "newVersion");
		generalBehaviourDiff = new GeneralBehaviourDiff(oldVersion.getGeneralBehaviour(), newVersion.getGeneralBehaviour());
		userStories = DiffFactory.create(oldVersion.getUserstories(), newVersion.getUserstories());
		projectDiff = new ProjectDiff(oldVersion.getProject(), newVersion.getProject());
		docTimeDiff = new TimeDiff(calendar(oldVersion.getDocTime()), calendar(newVersion.getDocTime()));
	}

	private Calendar calendar(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(date.getTime());
		return instance;
	}

	public TimeDiff getDocTimeDiff() {
		return docTimeDiff;
	}

	public boolean hasNewStories() {
		return !getNewStories().isEmpty();
	}

	public List<UserStory> getNewStories() {
		return userStories.getNewItems();
	}

	public List<UserStory> getDeletedStories() {
		return userStories.getDeletedItems();
	}

	public boolean hasDeletedStories() {
		return !getDeletedStories().isEmpty();
	}

	public boolean hasUpdatedStories() {
		return !getUpdatedStories().isEmpty();
	}

	public List<UserStoryDiff> getUpdatedStories() {
		return userStories.getUpdatedItems();
	}

	public GeneralBehaviourDiff getGeneralBehaviourDiff() {
		return generalBehaviourDiff;
	}

	public boolean hasGeneralBehaviourDiff() {
		return getGeneralBehaviourDiff().diffExists();
	}

	public boolean hasUserStoryDiff() {
		return userStories.diffExists();
	}

	public boolean diffExists() {
		return generalBehaviourDiff.diffExists() || userStories.diffExists();
	}

	public ProjectDiff getProjectDiff() {
		return projectDiff;
	}

	/**
	 * Setter - For test purpose
	 */
	public void setDocTimeDiff(TimeDiff docTimeDiff) {
		this.docTimeDiff = docTimeDiff;
	}

}