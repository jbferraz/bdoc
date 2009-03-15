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

/**
 * @author Per Otto Bergum Christensen
 */
public class BDoc {

	public static final String TEST_METHOD_PREFIX = "test";

	protected transient Class<? extends Annotation> storyRefAnnotation;
	protected transient TestAnnotations testAnnotations = new TestAnnotations();

	protected ProjectInfo projectInfo = new ProjectInfo("unnamed", "unknown");
	protected Calendar docTime = Calendar.getInstance();
	protected List<UserStory> userStories = new ArrayList<UserStory>();
	protected GeneralBehaviour generalBehaviour = new GeneralBehaviour();

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
	public BDoc(Class<? extends Annotation> testAnnotation, Class<? extends Annotation> storyRefAnnotation,
			Class<? extends Annotation> ignoreAnnotation) {
		this.storyRefAnnotation = storyRefAnnotation;
		this.testAnnotations = new TestAnnotations(testAnnotation, ignoreAnnotation);
	}

	/**
	 * @param testClass
	 * @return this instance
	 */
	public BDoc addBehaviourFrom(TestClass testClass, BehaviourFactory behaviourFactory) {
		UserStory userStory = null;

		if ((null != storyRefAnnotation) && (testClass.isAnnotationPresent(storyRefAnnotation))) {
			userStory = userStory(testClass.getAnnotation(storyRefAnnotation));
		}

		if (testClass.classIsAnnotatedWithIgnore(testAnnotations)) {
			return this;
		}

		for (TestMethod method : testClass.getTestMethods(testAnnotations)) {

			if ((null != storyRefAnnotation) && (method.isAnnotationPresent(storyRefAnnotation))) {
				userStory = userStory(method.getAnnotation(storyRefAnnotation));
			}

			ClassBehaviour classBehaviour = null;
			if (null != userStory) {
				classBehaviour = userStory.addBehaviour(testClass.clazz(), method.camelCaseSentence());
			} else {
				classBehaviour = generalBehaviour.addBehaviour(testClass.clazz(), method.camelCaseSentence());
			}

			if (testClass.isMarkedAsContainerOfScenariosSpecifiedInTestMethodBlocks()) {
				Scenario scenario = behaviourFactory.createScenario(testClass, method);
				if (null != scenario) {
					classBehaviour.addScenario(scenario);
				}
			}
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
		UserStory userStory = new UserStory(userStoryDescription(storyRefAnnotation));
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
		return Collections.unmodifiableList(userStories);
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

	public GeneralBehaviour getGeneralBehaviour() {
		return generalBehaviour;
	}

	public ClassBehaviour classBehaviourInGeneralBehaviour(Class<? extends Object> testClass) {
		return generalBehaviour.classBehaviourFor(testClass);
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
				addBehaviourFrom(new TestClass(behaviourFactory.javaSourceDir(), classLoader.loadClass(className)), behaviourFactory );
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public List<Specification> specifications() {
		List<Specification> result = new ArrayList<Specification>();

		result.addAll(generalBehaviour.specifications());
		for (UserStory userStory : userStories) {
			result.addAll(userStory.specifications());
		}
		return result;
	}

	public List<Scenario> scenarios() {
		List<Scenario> result = new ArrayList<Scenario>();

		result.addAll(generalBehaviour.scenarios());

		for (UserStory userStory : userStories) {
			result.addAll(userStory.getScenarios());
		}
		return result;
	}

	/**
	 * Helper method for test - when default annotations should be set, after serializing the BDoc, since annotations are transient
	 */
	public void setDefaultTestAnnotations() {
		testAnnotations = new TestAnnotations();
	}
}