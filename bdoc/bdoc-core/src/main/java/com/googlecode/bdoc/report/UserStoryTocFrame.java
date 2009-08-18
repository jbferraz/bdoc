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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.bdoc.BDocConfig;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.UserStory;
import com.googlecode.bdoc.doc.report.BDocMacroHelper;
import com.googlecode.bdoc.doc.report.TocItem;

public class UserStoryTocFrame {

	private Map<String, Object> model = new HashMap<String,Object>();

	public UserStoryTocFrame(BDoc bdoc, BDocConfig bdocConfig ) {
		model.put("toc", createToc(bdoc));
		model.put("bdocMacroHelper", new BDocMacroHelper( bdocConfig  ) );
	}
	
	private static List<TocItem> createToc(BDoc bdoc) {
		List<TocItem> toc = new ArrayList<TocItem>();
		for (UserStory userStory : bdoc.getUserstories()) {
			toc.add(new TocItem(userStory));
		}
		return toc;
	}
	
	public String html() {
		return FreeMarkerUtils.createContentFrom("user_story_toc_frame_html.ftl",model);
	}

}
