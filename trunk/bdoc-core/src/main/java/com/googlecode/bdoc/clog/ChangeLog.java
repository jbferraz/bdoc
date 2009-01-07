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

package com.googlecode.bdoc.clog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.Project;

public class ChangeLog {

	private BDoc latestBDoc;
	private List<BDocDiff> diffList = new ArrayList<BDocDiff>();

	public void scan(BDoc bdoc) {
		if (null == latestBDoc) {
			latestBDoc = new BDoc();
			latestBDoc.setProject(new Project("", ""));
		}
		BDocDiff bdocDiff = new BDocDiff(latestBDoc, bdoc);
		if (bdocDiff.diffExists()) {
			diffList.add(bdocDiff);
		}
		latestBDoc = bdoc;
	}

	public BDoc latestBDoc() {
		return latestBDoc;
	}

	public BDocDiff latestDiff() {
		return diffList.get(diffList.size() - 1);
	}

	public List<BDocDiff> diffList() {
		return diffList;
	}

	public static ChangeLog fromXmlFile(File changeLogXmlFile) {
		return new ChangeLog();
	}

}
