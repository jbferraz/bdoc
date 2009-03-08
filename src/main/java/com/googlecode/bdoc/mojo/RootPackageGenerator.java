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

package com.googlecode.bdoc.mojo;

import org.apache.maven.project.MavenProject;

/**
 *  @author Per Otto Bergum Christensen
 */
public class RootPackageGenerator {

	private RootPackageGenerator() {

	}

	public static String calculateRootPackage(MavenProject project) {
		String groupId = project.getGroupId();
		String artifactId = project.getArtifactId();
		String result = null;

		if (groupId.equals(artifactId) || groupId.endsWith(artifactId)) {
			result = groupId;
		} else {
			String[] groupIdPices = groupId.split("\\.");

			if (1 == groupIdPices.length) {
				groupIdPices = groupId.split("-");
			}

			if (0 < groupIdPices.length) {
				String groupIdPostfix = groupIdPices[groupIdPices.length - 1];

				String[] artifactIdPices = artifactId.split("-");
				String artifactIdPrefix = artifactIdPices[0];

				if (groupIdPostfix.equals(artifactIdPrefix)) {
					result = groupId + artifactId.substring(artifactIdPrefix.length());
				}
			}
		}
		if (null == result) {
			result = groupId;
		}
		return result.replace('-', '.');
	}

}
