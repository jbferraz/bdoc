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

package com.googlecode.bdoc.report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.BDocException;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.doc.report.BDocMacroHelper;

import freemarker.template.TemplateException;

public class BDocReport {

	private String indexFrameSet;
	private String blankFrame;
	private String userStoryTocFrame;
	private ProjectInfoFrame projectInfoFrame;

	private Map<String, Object> model = new HashMap<String, Object>();
	private BDoc bdoc;
	private List<UserStorySpecificationsFrame> userStorySpecificationFrameItems = new ArrayList<UserStorySpecificationsFrame>();
	private String userStoryExamplesFrame;
	private BDocConfig bdocConfig;

	public BDocReport(BDoc bdoc, BDocConfig bdocConfig) {
		this.bdoc = bdoc;
		this.bdocConfig = bdocConfig;
		this.model.put("bdoc", bdoc);
		this.model.put("bdocMacroHelper", new BDocMacroHelper(bdocConfig));
		try {
			makeBDocReport();
		} catch (Throwable e) {
			throw new BDocException(e);
		}
	}

	private void makeBDocReport() throws TemplateException, IOException {
		for (UserStory userStory : bdoc.getUserstories()) {
			userStorySpecificationFrameItems.add(new UserStorySpecificationsFrame(userStory, bdocConfig));
		}

		indexFrameSet = BDocReportUtils.createContentFrom("index.ftl", model);
		userStoryTocFrame = new UserStoryTocFrame(userStorySpecificationFrameItems, bdocConfig).html();
		userStoryExamplesFrame = BDocReportUtils.createContentFrom("user_story_examples_frame_html.ftl", model);
		projectInfoFrame = new ProjectInfoFrame( bdoc, bdocConfig );
		blankFrame = BDocReportUtils.createContentFrom("blank.ftl", model);
	}

	public void writeTo(File baseDir) {
		File reportDirectory = createReportDirectory(baseDir);
		writeFile(reportDirectory, "index.html", indexFrameSet);
		writeFile(reportDirectory, "user_story_toc_frame.html", userStoryTocFrame);

		for (UserStorySpecificationsFrame userStorySpec : userStorySpecificationFrameItems) {
			writeFile(reportDirectory, userStorySpec.getFileName(), userStorySpec.html());
		}

		writeFile(reportDirectory, "user_story_examples_frame.html", userStoryExamplesFrame);
		writeFile(reportDirectory, "project_info.html", projectInfoFrame.html() );
		writeFile(reportDirectory, "blank.html", blankFrame );
	}

	private void writeFile(File reportDirectory, String fileName, String content) {
		try {
			FileUtils.writeStringToFile(new File(reportDirectory, fileName), content);
		} catch (IOException e) {
			throw new BDocException(e);
		}
	}

	private File createReportDirectory(File basetDir) {
		try {
			File reportDir = new File(basetDir, "bdoc");
			FileUtils.forceMkdir(reportDir);
			return reportDir;
		} catch (IOException e) {
			throw new BDocException(e);
		}
	}

}
