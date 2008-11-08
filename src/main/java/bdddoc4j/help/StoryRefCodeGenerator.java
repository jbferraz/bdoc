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

package bdddoc4j.help;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;

import bdddoc4j.core.util.SysProp;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class StoryRefCodeGenerator {

	public enum Result {
		SUCCESS, REF_JAVA_EXISTS, STORY_JAVA_EXISTS
	};

	private String javaPackage;
	private File testDirectory;
	private Configuration cfg;
	private StringBuilder resultLog = new StringBuilder();

	public StoryRefCodeGenerator() {
		cfg = new Configuration();
		cfg.setTemplateLoader(new ClassTemplateLoader(StoryRefCodeGenerator.class, ""));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
	}

	public Result execute() throws IOException {
		Validate.notNull(javaPackage, "javaPackage");
		Validate.notNull(testDirectory, "testDirectory");

		File javaPackageDir = new File(testDirectory, javaPackage.replace('.', '/'));
		FileUtils.forceMkdir(javaPackageDir);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("javaPackage", javaPackage);

		File refJava = new File(javaPackageDir, "Ref.java");
		if (refJava.exists()) {
			return Result.REF_JAVA_EXISTS;
		}
		generateJavaFile(refJava, model, "ref_java.ftl");

		File storyJava = new File(javaPackageDir, "Story.java");
		if (storyJava.exists()) {
			return Result.STORY_JAVA_EXISTS;
		}
		generateJavaFile(storyJava, model, "story_java.ftl");

		return Result.SUCCESS;
	}

	/**
	 * @param refJava
	 * @param model
	 *            with the packageName for the java-file to generate
	 * @param templateName
	 *            for the java-file to generate
	 * @return generated java source
	 */
	private void generateJavaFile(File refJava, Map<String, Object> model, String templateName) {
		try {
			Template template = cfg.getTemplate(templateName);

			StringWriter result = new StringWriter();
			template.process(model, result);
			FileUtils.writeStringToFile(refJava, result.toString());
			resultLog.append("File created: " + refJava.getAbsolutePath() + SysProp.NL);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setJavaPackage(String javaPackage) {
		this.javaPackage = javaPackage;
	}

	public void setTestDirectory(File testDirectory) {
		this.testDirectory = testDirectory;
	}

	public String getResultLog() {
		return resultLog.toString();
	}
}
