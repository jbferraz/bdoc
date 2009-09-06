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

package com.googlecode.bdoc.diff.report;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.diff.domain.BDocDiff;
import com.googlecode.bdoc.doc.report.BDocMacroHelper;
import com.googlecode.bdoc.doc.report.UserStoryHtmlReport;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author Per Otto Bergum Christensen
 */
public class HtmlDiffReport {

	private Map<String, Object> model;
	private Configuration cfg;

	public HtmlDiffReport(BDocDiff bDocDiff,BDocConfig bdocConfig) {
		cfg = new Configuration();

		final MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(new TemplateLoader[] {
				new ClassTemplateLoader(HtmlDiffReport.class, ""), new ClassTemplateLoader(UserStoryHtmlReport.class, "") });
		
		cfg.setTemplateLoader(multiTemplateLoader);
		cfg.setObjectWrapper(new DefaultObjectWrapper());

		model = new HashMap<String, Object>();
		model.put("bdocDiff", bDocDiff);
		model.put("bdocMacroHelper", new BDocMacroHelper( bdocConfig  ) );
	}

	public String html() {
		try {
			Template template = cfg.getTemplate("html_diff_report.ftl");

			StringWriter xhtml = new StringWriter();
			template.process(model, xhtml);
			return xhtml.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
