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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.googlecode.bdoc.Ref;
import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.BehaviourFactory;
import com.googlecode.bdoc.doc.domain.ClassBehaviour;
import com.googlecode.bdoc.doc.domain.Scenario;
import com.googlecode.bdoc.doc.domain.TestBDoc;
import com.googlecode.bdoc.doc.domain.TestClass;
import com.googlecode.bdoc.doc.domain.TestClassBehaviour;
import com.googlecode.bdoc.doc.domain.TestMethod;
import com.googlecode.bdoc.doc.domain.TestTable;
import com.googlecode.bdoc.doc.domain.TestUserStory;
import com.googlecode.bdoc.doc.domain.UserStory;

public class TestBDocFactoryImpl {

	private BehaviourFactory dummyBehaviourFactory = new BehaviourFactory() {

		public void analyze(TestMethod method) {
		}

		public List<Scenario> getCreatedScenarios() {
			return null;
		}

		public List<TestTable> getCreatedTestTables() {
			return null;
		}

		public File sourceTestDirectory() {
			return new File("./src/test/java");
		}

	};

	@Test
	public void shouldCreateAClassBehaviourSorterWhenAReportConfigIsFound() {
		BDocFactoryImpl bdocFactoryImpl = BDocFactoryImpl.createForTest("MyReportConfig");
		bdocFactoryImpl.setTestClassDirectory(new File("./target/test-classes"));
		bdocFactoryImpl.setClassLoader(getClass().getClassLoader());
		assertNotNull(bdocFactoryImpl.createClassBehaviourSorter());
	}

	@Test
	public void shouldPassOnTheClassBehaviourSorterToBDocWhenAReportConfigIsFound() {
		BDocFactoryImpl bdocFactoryImpl = BDocFactoryImpl.createForTest("MyReportConfig");
		bdocFactoryImpl.setTestClassDirectory(new File("./target/test-classes"));
		bdocFactoryImpl.setStoryRefAnnotation(Ref.class);
		bdocFactoryImpl.setClassLoader(getClass().getClassLoader());

		BDoc bdoc = bdocFactoryImpl.createBDoc(dummyBehaviourFactory);
		bdoc.addBehaviourFrom(new TestClass(TestClassBehaviour.class), dummyBehaviourFactory);
		bdoc.addBehaviourFrom(new TestClass(TestUserStory.class), dummyBehaviourFactory);
		bdoc.addBehaviourFrom(new TestClass(TestBDoc.class), dummyBehaviourFactory);

		List<ClassBehaviour> classBehaviourList = bdoc.getUserstories().get(0).getClassBehaviour();

		assertEquals("BDoc", classBehaviourList.get(0).getClassName());
		assertEquals("UserStory", classBehaviourList.get(1).getClassName());
		assertEquals("ClassBehaviour", classBehaviourList.get(2).getClassName());
	}

	public static class MyReportConfig {
		Class<?>[] presentationOrder = { BDoc.class, UserStory.class, ClassBehaviour.class };
	}
}
