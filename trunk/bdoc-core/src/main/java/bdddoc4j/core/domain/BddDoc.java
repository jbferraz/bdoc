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

package bdddoc4j.core.domain;

import static bdddoc4j.core.util.Select.from;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * @author Per Otto Bergum Christensen
 */
public class BddDoc {

	private static final String TEST_METHOD_PREFIX = "test";
	protected transient Class<? extends Annotation> testAnnotation;
	protected transient Class<? extends Annotation> storyRefAnnotation;

	protected Project project;
	protected Calendar docTime = Calendar.getInstance();
	protected List<UserStory> userStories = new ArrayList<UserStory>();
	protected GeneralBehaviour generalBehaviour = new GeneralBehaviour();

	/**
	 * Constructor - for extensions
	 */
	protected BddDoc() {
	}

	/**
	 * Constructor
	 * 
	 * @param testAnnotation
	 *            marks method as a test
	 * @param storyRefAnnotation
	 *            marks method as something with a reference to story
	 */
	public BddDoc(Class<? extends Annotation> testAnnotation, Class<? extends Annotation> storyRefAnnotation) {
		this.testAnnotation = testAnnotation;
		this.storyRefAnnotation = storyRefAnnotation;
	}

	/**
	 * @param testClass
	 *            that describes behaviour with testmethods
	 */
	public void addBehaviourFrom(Class<? extends Object> testClass) {
		UserStory userStory = null;

		if ((null != storyRefAnnotation) && (testClass.isAnnotationPresent(storyRefAnnotation))) {
			userStory = userStory(testClass.getAnnotation(storyRefAnnotation));
		}

		for (Method testMethod : testClass.getMethods()) {

			if (!testMethod.isAnnotationPresent(testAnnotation) && !testMethod.getName().startsWith(TEST_METHOD_PREFIX)) {
				continue;
			}

			if ((null != storyRefAnnotation) && (testMethod.isAnnotationPresent(storyRefAnnotation))) {
				userStory = userStory(testMethod.getAnnotation(storyRefAnnotation));
			}

			String camelCaseSentence = getCamelCaseSentence(testMethod);

			if (null != userStory) {
				userStory.addBehaviour(testClass, camelCaseSentence);
			} else {
				generalBehaviour.addBehaviour(testClass, camelCaseSentence);
			}
		}
	}

	/**
	 * Gets the camelCaseSentence from the testmethod, removing 'test' if JUnit
	 * 3 is used
	 * 
	 * @param testMethod
	 *            that specifies the test
	 * @return camelCaseSentence describeing behaviour
	 */
	private String getCamelCaseSentence(Method testMethod) {
		String camelCaseSentence = testMethod.getName();
		if (camelCaseSentence.startsWith(TEST_METHOD_PREFIX)) {
			camelCaseSentence = camelCaseSentence.substring(TEST_METHOD_PREFIX.length());
			camelCaseSentence = camelCaseSentence.substring(0, 1).toLowerCase()
					+ camelCaseSentence.substring(1, camelCaseSentence.length());
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	public ClassBehaviour getGeneralBehaviourFor(Class<? extends Object> testClass) {
		return generalBehaviour.classBehaviourFor(testClass);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof BddDoc) && ((BddDoc) obj).docTime.equals(docTime) && ((BddDoc) obj).project.equals(project);
	}

}