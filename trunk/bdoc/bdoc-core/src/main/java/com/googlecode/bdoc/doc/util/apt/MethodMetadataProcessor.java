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

package com.googlecode.bdoc.doc.util.apt;

import static javax.lang.model.element.ElementKind.METHOD;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner6;

/**
 * @author Per Otto Bergum Christensen
 */
@SupportedAnnotationTypes("*")
public class MethodMetadataProcessor extends AbstractProcessor {

	private MethodMetadataScanner methodMetadataScanner;

	public MethodMetadataProcessor(List<MethodMetadata> methodMetadata) {
		this.methodMetadataScanner = new MethodMetadataScanner(methodMetadata);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (!roundEnv.processingOver()) {

			for (Element element : roundEnv.getRootElements()) {
				methodMetadataScanner.scan(element);
			}
		}
		return false;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latest();
	}

	/**
	 * 
	 */
	class MethodMetadataScanner extends ElementScanner6<Void, Void> {

		private List<MethodMetadata> methodMetadataList;

		public MethodMetadataScanner(List<MethodMetadata> methodMetadata) {
			this.methodMetadataList = methodMetadata;
		}

		@Override
		public Void visitExecutable(ExecutableElement e, Void p) {

			if (e.getKind() == METHOD) {
				MethodMetadata methodMetadata = new MethodMetadata(e.getSimpleName().toString());
				for (VariableElement variableElement : e.getParameters()) {
					methodMetadata.addArgumentMetadata(new ArgumentMetadata(variableElement.getSimpleName().toString()));
				}

				methodMetadataList.add(methodMetadata);
			}
			return null;
		}
	}
}
