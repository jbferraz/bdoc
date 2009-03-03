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

package com.googlecode.bdoc.difflog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.report.XmlReport;
import com.thoughtworks.xstream.XStream;

/**
 * Holds a changelog off diffs between bdocs that have been scaned.
 * 
 * @author Per Otto Bergum Christensen
 */
public class DiffLog {

	private static final int DEFAULT_NUMBER_OF_CHANGE_LOG_ITEMS = 100;
	private BDoc latestBDoc;
	private List<BDocDiff> diffList = new ArrayList<BDocDiff>();
	private transient int numberOfChangeLogItemsToKeep = DEFAULT_NUMBER_OF_CHANGE_LOG_ITEMS;

	public void scan(BDoc bdoc) {
		if (null != latestBDoc) {
			BDocDiff bdocDiff = new BDocDiff(latestBDoc, bdoc);
			if (bdocDiff.diffExists()) {
				addBDocDiff(bdocDiff);
			}
		}
		latestBDoc = bdoc;
	}

	public void addBDocDiff(BDocDiff bdocDiff) {
		diffList.add(0, bdocDiff);
		if (getNumberOfChangeLogItemsToKeep() < diffList.size()) {
			diffList.remove(diffList.size() - 1);
		}
	}

	public BDoc latestBDoc() {
		return latestBDoc;
	}

	public BDocDiff latestDiff() {
		return diffList.get(diffList.size() - 1);
	}

	public List<BDocDiff> getDiffList() {
		return diffList;
	}

	public static DiffLog fromXmlFile(File changeLogXmlFile) {
		try {
			return (DiffLog) xstream().fromXML(FileUtils.readFileToString(changeLogXmlFile));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeToFile(File changeLogXmlFile) {
		try {
			FileUtils.writeStringToFile(changeLogXmlFile, xstream().toXML(this));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static XStream xstream() {
		XStream xstream = new XStream();
		XmlReport.addAlias(xstream);
		xstream.alias("changeLog", DiffLog.class);
		return xstream;
	}

	public void setNumberOfChangeLogItemsToKeep(int numberOfChangeLogItemsToKeep) {
		this.numberOfChangeLogItemsToKeep = numberOfChangeLogItemsToKeep;
	}

	public int getNumberOfChangeLogItemsToKeep() {
		if (0 == numberOfChangeLogItemsToKeep) {
			numberOfChangeLogItemsToKeep = DEFAULT_NUMBER_OF_CHANGE_LOG_ITEMS;
		}
		return numberOfChangeLogItemsToKeep;
	}
}
