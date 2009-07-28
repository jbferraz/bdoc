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

package com.googlecode.bdoc.doc.analyzer;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Name;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

/**
 * 
 * @author Micael Vesterlund
 * 
 */
public class CodeAnalyzerTreeVisitor extends TreePathScanner<Object, Trees> {

	private List<MethodInfo> methodInfos;

	public CodeAnalyzerTreeVisitor() {
		methodInfos = new ArrayList<MethodInfo>();
	}

	public List<MethodInfo> getMethodInfos() {
		return methodInfos;
	}

	@Override
	public Object visitMethod(MethodTree methodTree, Trees trees) {
		TreePath path = getCurrentPath();
		Tree leaf = path.getLeaf();
		if (leaf != null) {
			MethodTree methodTreeLeaf = (MethodTree) leaf;
			Name name = methodTreeLeaf.getName();
			ModifiersTree modifiers = methodTreeLeaf.getModifiers();
			boolean isTestMethod = false;
			List<? extends AnnotationTree> annotations = modifiers.getAnnotations();
			for (AnnotationTree annotationTree : annotations) {
				Tree annotationType = annotationTree.getAnnotationType();
				Kind kind = annotationType.getKind();
				Name identifier = null;
				if (kind.equals(Kind.IDENTIFIER)) {
					IdentifierTree identifierTree = (IdentifierTree) annotationType;
					identifier = identifierTree.getName();
				} else {
					MemberSelectTree memberSelectTree = (MemberSelectTree) annotationType;
					identifier = memberSelectTree.getIdentifier();
				}
				if (identifier.contentEquals("Test")) {
					isTestMethod = true;
				}

			}
			if (isTestMethod) {
				MethodInfo methodInfo = new MethodInfo(name.toString());
				List<MethodInfo> methods = new ArrayList<MethodInfo>();
				BlockTree body = methodTreeLeaf.getBody();
				List<? extends StatementTree> statements = body.getStatements();
				for (StatementTree statementTree : statements) {
					Kind kind = statementTree.getKind();
					if (kind.equals(Kind.EXPRESSION_STATEMENT)) {
						ExpressionStatementTree expressionStatementTree = (ExpressionStatementTree) statementTree;
						ExpressionTree expression = expressionStatementTree.getExpression();
						extractMethods(methods, expression);
					} else if (kind.equals(Kind.VARIABLE)) {
						VariableTree variableTree = (VariableTree) statementTree;
						ExpressionTree expression = variableTree.getInitializer();
						extractMethods(methods, expression);
					}
				}
				methodInfo.setMethodInfos(methods);
				methodInfos.add(methodInfo);
			}
		}

		return super.visitMethod(methodTree, trees);
	}

	private void extractMethods(List<MethodInfo> methods, ExpressionTree expression) {
		if (expression.getKind().equals(Kind.METHOD_INVOCATION)) {
			MethodInvocationTree methodInvocationTree = (MethodInvocationTree) expression;
			ExpressionTree methodSelect = methodInvocationTree.getMethodSelect();
			if (methodSelect.getKind().equals(Kind.IDENTIFIER)) {
				IdentifierTree identifierTree = (IdentifierTree) methodSelect;
				Name name = identifierTree.getName();
				methods.add(new MethodInfo(name.toString()));
			}
		}
	}

}
