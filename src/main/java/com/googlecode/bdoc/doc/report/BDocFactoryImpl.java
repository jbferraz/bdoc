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

package com.googlecode.bdoc.doc.report;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.BehaviourFactory;
import com.googlecode.bdoc.doc.domain.ClassBehaviourSorter;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.util.ClassesDirectory;

/**
 * @author Per Otto Bergum Christensen
 */
public class BDocFactoryImpl implements BDocFactory {

	private ClassesDirectory testClassDirectory = new ClassesDirectory();

	private ProjectInfo projectInfo;
	private ClassLoader classLoader;
	private Class<? extends Annotation> storyRefAnnotation;
	private String reportConfigClassName = "ReportConfig";

	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	public void setTestClassDirectory(File baseDir) {
		this.testClassDirectory.setBaseDir(baseDir);
	}

	public void setIncludesFilePattern(String[] includesFilePattern) {
		testClassDirectory.setIncludes(includesFilePattern);
	}

	public void setExcludesFilePattern(String[] excludesFilePattern) {
		testClassDirectory.setExcludes(excludesFilePattern);
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public void setStoryRefAnnotation(Class<? extends Annotation> storyRefAnnotation) {
		this.storyRefAnnotation = storyRefAnnotation;
	}

	public BDoc createBDoc(BehaviourFactory behaviourFactory) {
		ClassBehaviourSorter classBehaviourSorter = createClassBehaviourSorter();
		BDoc bdoc = new BDoc(storyRefAnnotation, classBehaviourSorter);
		bdoc.setProject(projectInfo);
		bdoc.addBehaviourFrom(testClassDirectory, classLoader, behaviourFactory);
		return bdoc;
	}

	protected ClassBehaviourSorter createClassBehaviourSorter() {
		String reportConfigClassName = findReportConfigClassName();
		if (null != reportConfigClassName) {
			return createClassBehaviourSorterFromClassName(reportConfigClassName);
		}
		return null;
	}

	protected String findReportConfigClassName() {
		ClassesDirectory reportConfigDirectory = new ClassesDirectory();
		reportConfigDirectory.setBaseDir(testClassDirectory.getBaseDir());
		reportConfigDirectory.setIncludes("**/" + reportConfigClassName + ".class");
		List<String> result = reportConfigDirectory.classes();
		if (!result.isEmpty()) {
			return result.get(0);
		}

		return null;
	}

	private ClassBehaviourSorter createClassBehaviourSorterFromClassName(String className) {
		try {
			Class<?> reportConfigClass = classLoader.loadClass(className);
			
			Field field = reportConfigClass.getDeclaredField("presentationOrder");
			field.setAccessible(true);

			return new ClassBehaviourSorter(((Class<?>[]) field.get(reportConfigClass.newInstance())));

		} catch (Exception e) {
			throw new IllegalArgumentException("ReportConfig not accecpted: " + className, e);
		}
	}

	public static BDocFactoryImpl createForTest(String reportConfigClassName) {
		BDocFactoryImpl bdocFactoryImpl = new BDocFactoryImpl();
		bdocFactoryImpl.reportConfigClassName = reportConfigClassName;
		return bdocFactoryImpl;
	}
}