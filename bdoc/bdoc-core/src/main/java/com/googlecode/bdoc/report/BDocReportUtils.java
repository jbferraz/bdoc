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

import java.io.StringWriter;
import java.util.Map;

import com.googlecode.bdoc.BDocException;
import com.googlecode.bdoc.doc.report.AbstractHtmlReport;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

public class BDocReportUtils {

	private static Configuration cfg = new Configuration();
	static {
		MultiTemplateLoader multiTemplateLoader = BDocReportUtils.createTemplateLoader();
		cfg.setTemplateLoader(multiTemplateLoader);
		cfg.setObjectWrapper(new DefaultObjectWrapper());

	}

	private BDocReportUtils() {

	}

	private static MultiTemplateLoader createTemplateLoader() {
		final MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(new TemplateLoader[] {
				new ClassTemplateLoader(BDocReport.class, ""), //
				new ClassTemplateLoader(AbstractHtmlReport.class, "") });

		return multiTemplateLoader;
	}

	public static Configuration cfg() {
		return cfg;
	}

	public static String createContentFrom(String template, Map<String, Object> model) {

		try {
			StringWriter xhtml = new StringWriter();
			cfg().getTemplate(template).process(model, xhtml);
			return xhtml.toString();
		} catch (Exception e) {
			throw new BDocException(e);
		}

	}

}
