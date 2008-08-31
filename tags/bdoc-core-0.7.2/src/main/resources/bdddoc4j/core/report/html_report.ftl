<#--

    The MIT License
    
    Copyright (c) 2008 Per Otto Bergum Christensen
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.

-->

<#import "bdd_doc_macros.ftl" as bdddoc />
<#setting date_format="short">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<title>${bddDoc.project.name}</title>
		<#include "css.ftl">
	</head>
	<body>
		<h1>${bddDoc.project.name} ${bddDoc.project.version}</h1>
		<p>${bddDoc.docTime?date}</p>
		
		<#list bddDoc.userstories as userstory>
			<@bdddoc.story userstory=userstory/> 		
			<hr/>
		</#list>
		
		<div id="generalBehaviour">
			<h2><@bdddoc.text key="general.behaviour"/></h2>
		
			<#list bddDoc.generalBehaviour.packages as package>
				<@bdddoc.present_package_behaviour package=package/>
			</#list>
								
		</div>		
	</body>
</html>