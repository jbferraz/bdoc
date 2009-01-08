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

package com.googlecode.bdoc.doc.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Per Otto Bergum Christensen
 */
public class ClassesDirectory {

	private static final String CLASS_POSTFIX = ".class";

	DirectoryScanner ds = new DirectoryScanner();

	public void setBaseDir(File baseDir) {
		ds.setBasedir(baseDir);
	}

	public List<String> classes() {
		Validate.notNull(ds.getBasedir(), "BaseDir must be set on " + getClass().getCanonicalName() );

		List<String> result = new ArrayList<String>();

		if (ds.getBasedir().isDirectory()) {
			ds.scan();

			for (String includedFile : ds.getIncludedFiles()) {
				process(new File(ds.getBasedir(), includedFile), result);
			}
		}

		return result;
	}

	private void process(File file, List<String> result) {
		if (file.isFile() && file.getName().endsWith(CLASS_POSTFIX)) {
			String absoluteFilePath = file.getAbsolutePath();

			String relativeFilePath = absoluteFilePath.substring(ds.getBasedir().getAbsolutePath().length() + 1);

			String relativeFilePathWithoutPrefix = relativeFilePath.substring(0, relativeFilePath.indexOf(CLASS_POSTFIX));

			String className = relativeFilePathWithoutPrefix.replace('/', '.').replace('\\', '.');

			result.add(className);
		}
	}

	/**
	 * @param includes
	 *            array of ant file patterns to include
	 */
	public void setIncludes(String[] includes) {
		ds.setIncludes(includes);
	}

	/**
	 * @param excludes
	 *            array of ant file patterns to exclude
	 */
	public void setExcludes(String[] excludes) {
		ds.setExcludes(excludes);
	}
}
