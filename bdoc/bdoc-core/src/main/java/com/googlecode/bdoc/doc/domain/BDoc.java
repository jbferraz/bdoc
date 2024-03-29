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

package com.googlecode.bdoc.doc.domain;

import static com.googlecode.bdoc.doc.util.Select.from;
import static com.googlecode.bdoc.doc.util.TestMethodReferenceFinder.findTestMethodReferenceFor;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.googlecode.bdoc.doc.util.ClassesDirectory;
import com.googlecode.bdoc.doc.ztatic.JavaTestSourceBehaviourParser;

;

/**
 * @author Per Otto Bergum Christensen
 * @author Micael Vesterlund
 */
public class BDoc {

	public static final String TEST_METHOD_PREFIX = "test";

	protected transient Class<? extends Annotation> storyRefAnnotation;

	protected ProjectInfo projectInfo = new ProjectInfo("unnamed", "unknown");
	protected Calendar docTime = Calendar.getInstance();
	protected List<UserStory> userStories = new ArrayList<UserStory>();
	protected ModuleBehaviour moduleBehaviour = new ModuleBehaviour();

	private ClassBehaviourSorter classBehaviourSorter;

	/**
	 * Constructor - for test
	 */
	public BDoc() {
	}

	/**
	 * Constructor - for test
	 */
	public BDoc(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	/**
	 * Constructor
	 * 
	 * @param testAnnotation
	 *            marks method as a test
	 * @param storyRefAnnotation
	 *            marks method as something with a reference to story
	 */
	public BDoc(Class<? extends Annotation> storyRefAnnotation, ClassBehaviourSorter classBehaviourSorter) {
		this.storyRefAnnotation = storyRefAnnotation;
		this.classBehaviourSorter = classBehaviourSorter;
		this.moduleBehaviour.setClassBehaviourSorter( classBehaviourSorter );
	}

	/**
	 * @param testClass
	 * @return this instance
	 */
	public BDoc addBehaviourFrom(TestClass testClass, BehaviourFactory behaviourFactory) {

		testClass.registerTestMethodReferences(findTestMethodReferenceFor(testClass, behaviourFactory.sourceTestDirectory()));

		UserStory userStory = null;

		if ((null != storyRefAnnotation) && (testClass.isAnnotationPresent(storyRefAnnotation))) {
			userStory = userStory(testClass.getAnnotation(storyRefAnnotation));
		}

		if (testClass.classIsAnnotatedWithIgnore()) {
			return this;
		}

		for (TestMethod method : testClass.getTestMethods()) {

			if ((null != storyRefAnnotation) && (method.isAnnotationPresent(storyRefAnnotation))) {
				userStory = userStory(method.getAnnotation(storyRefAnnotation));
			}

			if (testClass.shouldBeAnalyzedForExtendedBehaviour()) {
				behaviourFactory.analyze(method);
				method.setScenarios(behaviourFactory.getCreatedScenarios());
				method.setTestTables(behaviourFactory.getCreatedTestTables());
			}

			if (null != userStory) {
				userStory.addBehaviour(method);
			}

			moduleBehaviour.addBehaviour(method);
		}
		return this;
	}

	/**
	 * Adds a userstory if it doesn't exist
	 * 
	 * @param storyRefAnnotation
	 *            identifies userstory to add
	 */
	private UserStory userStory(Annotation storyRefAnnotation) {
		UserStory userStory = new UserStory(userStoryDescription(storyRefAnnotation), classBehaviourSorter);
		if (userStories.contains(userStory)) {
			return from(userStories).equalTo(userStory);
		} else {
			userStories.add(userStory);
			return userStory;
		}
	}

	/**
	 * @param annotation
	 *            that has UserStoryMarker as value
	 * @return UserStoryMarker given by annotation value
	 */
	private static UserStoryDescription userStoryDescription(Annotation annotation) {
		Validate.notNull(annotation, "annotation");
		try {
			Method valueMethod = annotation.getClass().getMethod("value");
			Object storyReference = valueMethod.invoke(annotation);
			if (UserStoryDescription.class.isAssignableFrom(storyReference.getClass())) {
				return (UserStoryDescription) storyReference;
			}
			return new UserStoryDescriptionWrapper(storyReference);
		} catch (Exception e) {
			throw new RuntimeException("Problem with annotation [" + annotation + "]. Root cause => " + e.getMessage(), e);
		}
	}

	/**
	 * @return an unmodifiable list of the userstories
	 */
	public List<UserStory> getUserstories() {
		Collections.sort(userStories);
		return userStories;
	}

	public Date getDocTime() {
		return docTime.getTime();
	}

	public ProjectInfo getProject() {
		return projectInfo;
	}

	public void setProject(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}

	/**
	 * Gets the userStory given by the criteria
	 * 
	 * @param userStoryDescription
	 *            that identifies the userstory
	 * @return instance of a userStory
	 * @throws IllegalArgumentException
	 *             if no item is found
	 */
	public UserStory userStoryFor(UserStoryDescription userStoryDescription) {
		return from(userStories).equalTo(new UserStory(userStoryDescription));
	}

	public ModuleBehaviour getModuleBehaviour() {
		return moduleBehaviour;
	}

	public ClassBehaviour classBehaviourInModuleBehaviour(Class<? extends Object> testClass) {
		return moduleBehaviour.classBehaviourFor(testClass);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof BDoc) && ((BDoc) obj).docTime.equals(docTime) && ((BDoc) obj).projectInfo.equals(projectInfo);
	}

	/**
	 * For test
	 */
	public BDoc addBehaviourFrom(TestClass testClass, File testSrcDir) {
		return addBehaviourFrom(testClass, new JavaTestSourceBehaviourParser(testSrcDir));
	}

	public void addBehaviourFrom(ClassesDirectory testClassesDirectory, ClassLoader classLoader, BehaviourFactory behaviourFactory) {
		List<String> classes = testClassesDirectory.classes();
		for (String className : classes) {
			try {
				TestClass testClass = new TestClass(classLoader.loadClass(className));
				addBehaviourFrom(testClass, behaviourFactory);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public List<Specification> specifications() {
		List<Specification> result = new ArrayList<Specification>();

		result.addAll(moduleBehaviour.specifications());
		for (UserStory userStory : userStories) {
			result.addAll(userStory.specifications());
		}
		return result;
	}

}