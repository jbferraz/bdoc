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

import com.googlecode.bdoc.doc.domain.BDoc;
import com.googlecode.bdoc.doc.domain.ProjectInfo;
import com.googlecode.bdoc.doc.domain.BehaviourFactory;

/**
 * Used to create BDoc, should be replaced by BDocConfig, that should be input
 * to the BDoc Constructor.
 * 
 * @author Per Otto Bergum Christensen
 */
public interface BDocFactory {

	public void setProjectInfo(ProjectInfo projectInfo);

	public abstract void setTestClassDirectory(File testClassDirectory);

	public abstract void setIncludesFilePattern(String[] includesFilePattern);

	public abstract void setExcludesFilePattern(String[] excludesFilePattern);

	public abstract void setClassLoader(ClassLoader classLoader);

	public abstract void setStoryRefAnnotation(Class<? extends Annotation> storyRefAnnotation);

	public abstract void setTestAnnotation(Class<? extends Annotation> testAnnotation);

	public abstract void setIgnoreAnnotation(Class<? extends Annotation> ignoreAnnotation);

	public abstract BDoc createBDoc(BehaviourFactory behaviourFactory);

}