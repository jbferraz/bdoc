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

	private static final String TEST_METHOD_PREFIX = "test";
	protected transient Class<? extends Annotation> testAnnotation;
	protected transient Class<? extends Annotation> ignoreAnnotation;
	protected transient Class<? extends Annotation> storyRefAnnotation;

	protected ProjectInfo projectInfo;
	protected Calendar docTime = Calendar.getInstance();
	protected List<UserStory> userStories = new ArrayList<UserStory>();
	protected GeneralBehaviour generalBehaviour = new GeneralBehaviour();

	/**
	 * Constructor - for test
	 */
	public BDoc() {
		this.testAnnotation = org.junit.Test.class;
		this.ignoreAnnotation = org.junit.Ignore.class;
	}

	/**
	 * Constructor - for test
	 */
	public BDoc(ProjectInfo projectInfo) {
		this.testAnnotation = org.junit.Test.class;
		this.ignoreAnnotation = org.junit.Ignore.class;
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
		this.testAnnotation = testAnnotation;
		this.storyRefAnnotation = storyRefAnnotation;
		this.ignoreAnnotation = ignoreAnnotation;
	}

	/**
	 * @param testClass
	 *            that describes behaviour with testmethods
	 */
	public void addBehaviourFrom(TestClass testClass, File testSrcDir) {
		UserStory userStory = null;

		if ((null != storyRefAnnotation) && (testClass.isAnnotationPresent(storyRefAnnotation))) {
			userStory = userStory(testClass.getAnnotation(storyRefAnnotation));
		}

		if (classIsAnnotatedWithIgnore(testClass)) {
			return;
		}

		for (Method method : testClass.getMethods()) {

			if (test(method)) {
				if ((null != storyRefAnnotation) && (method.isAnnotationPresent(storyRefAnnotation))) {
					userStory = userStory(method.getAnnotation(storyRefAnnotation));
				}

				ClassBehaviour classBehaviour = null;
				if (null != userStory) {
					classBehaviour = userStory.addBehaviour(testClass.clazz(), camelCaseSentence(method));
				} else {
					classBehaviour = generalBehaviour.addBehaviour(testClass.clazz(), camelCaseSentence(method));
				}

				if (testClass.isMarkedAsContainerOfScenariosSpecifiedInTestMethodBlocks()) {
					Scenario scenario = testClass.getScenarioFromTestMethodBlock(method.getName(), testSrcDir);
					if (null != scenario) {
						classBehaviour.addScenario(scenario);
					}
				}
			}
		}
	}

	private boolean classIsAnnotatedWithIgnore(TestClass testClass) {
		return (null != ignoreAnnotation) && (testClass.isAnnotationPresent(ignoreAnnotation));
	}

	/**
	 * Tells if the metod is a testMethod
	 * 
	 * @param method
	 *            to check
	 * @return true if method is a test
	 */
	private boolean test(Method method) {
		if ((null != testAnnotation) && method.isAnnotationPresent(testAnnotation)) {
			if ((null != ignoreAnnotation) && !method.isAnnotationPresent(ignoreAnnotation)) {
				return true;
			}
		}

		boolean testMethod = false;
		boolean ignore = false;
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			String name = annotation.annotationType().getName();
			if (name.endsWith(".Test")) {
				testMethod = true;
			} else if (name.endsWith(".Ignore")) {
				ignore = true;
			}
		}

		if (testMethod && !ignore) {
			return true;
		}

		if (method.getName().startsWith(TEST_METHOD_PREFIX)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the camelCaseSentence from the testmethod, removing 'test' if JUnit 3 is used
	 * 
	 * @param testMethod
	 *            that specifies the test
	 * @return camelCaseSentence describeing behaviour
	 */
	private String camelCaseSentence(Method testMethod) {
		String camelCaseSentence = testMethod.getName();
		if (camelCaseSentence.startsWith(TEST_METHOD_PREFIX)) {
			camelCaseSentence = camelCaseSentence.substring(TEST_METHOD_PREFIX.length());
			camelCaseSentence = camelCaseSentence.substring(0, 1).toLowerCase() + camelCaseSentence.substring(1, camelCaseSentence.length());
		}
		return camelCaseSentence;
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

	public void addBehaviourFrom(ClassesDirectory testClassesDirectory, ClassLoader classLoader, File testSrcDir) {
		List<String> classes = testClassesDirectory.classes();
		for (String className : classes) {
			try {
				addBehaviourFrom(new TestClass(classLoader.loadClass(className)), testSrcDir);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public List<Specification> specifications() {
		List<Specification> result = new ArrayList<Specification>();

		for (Package javaPackage : generalBehaviour.getPackages()) {
			for (ClassSpecifications classSpecifications : javaPackage.getClassSpecifications()) {
				result.addAll(classSpecifications.getSpecifications());
			}
		}

		for (UserStory userStory : userStories) {
			for (ClassSpecifications classSpecifications : userStory.getClassSpecifications()) {
				result.addAll(classSpecifications.getSpecifications());
			}

		}
		return result;
	}

}