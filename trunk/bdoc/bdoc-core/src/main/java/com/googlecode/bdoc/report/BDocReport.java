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
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.googlecode.bdoc.BDocException;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

public class BDocReport {

	private String indexHtmlContent;
	private String userStoriesTocFrameHtmlContent;

	public BDocReport() {
		try {
			indexHtmlContent = createIndexHtmlContent();
			userStoriesTocFrameHtmlContent = createUserStoriesTocFrameHtmlContent();
		} catch (Throwable e) {
			throw new BDocException(e);
		}
	}

	private String createIndexHtmlContent() throws TemplateException, IOException {
		Configuration cfg = new Configuration();
		final MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(new TemplateLoader[] { new ClassTemplateLoader(
				BDocReport.class, "") });

		cfg.setTemplateLoader(multiTemplateLoader);
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		Map<String, Object> model = new HashMap<String, Object>();
		StringWriter xhtml = new StringWriter();
		cfg.getTemplate("index_html.ftl").process(model, xhtml);
		return xhtml.toString();
	}

	private String createUserStoriesTocFrameHtmlContent() throws TemplateException, IOException {
		Configuration cfg = new Configuration();
		final MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(new TemplateLoader[] { new ClassTemplateLoader(
				BDocReport.class, "") });

		cfg.setTemplateLoader(multiTemplateLoader);
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		Map<String, Object> model = new HashMap<String, Object>();
		StringWriter xhtml = new StringWriter();
		cfg.getTemplate("user_stories_toc_frame_html.ftl").process(model, xhtml);
		return xhtml.toString();
	}

	public void writeTo(File baseDir) {
		File reportDirectory = createReportDirectory(baseDir);
		writeFile(reportDirectory, "index.html", indexHtmlContent);
		writeFile(reportDirectory, "user_stories_toc_frame.html", userStoriesTocFrameHtmlContent );
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
