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

package bdddoc4j.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Per Otto Bergum Christensen
 */
public class ClassUtil {

	private static final String CLASS_POSTFIX = ".class";

	private File dir;

	private List<String> result;

	/**
	 * Constructor
	 * @param dir to look for .class-files
	 */
	public ClassUtil(File dir) {
		this.dir = dir;
	}

	public List<String> find() {
		result = new ArrayList<String>();
		visitAllDirsAndFiles(dir);
		return result;
	}

	private void visitAllDirsAndFiles(File file) {
		process(file);

		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				visitAllDirsAndFiles(new File(file, children[i]));
			}
		}
	}

	private void process(File file) {
		if (file.isFile() && file.getName().endsWith(CLASS_POSTFIX)) {
			String absoluteFilePath = file.getAbsolutePath();

			String relativeFilePath = absoluteFilePath.substring(dir.getAbsolutePath().length() + 1);

			String relativeFilePathWithoutPrefix = relativeFilePath.substring(0, relativeFilePath.indexOf(CLASS_POSTFIX));

			String className = relativeFilePathWithoutPrefix.replace('/', '.').replace('\\', '.');

			result.add(className);
		}
	}
}
