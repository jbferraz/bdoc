<#--

    The MIT License
    
    Copyright (c) 2008, 2009 @Author(s)
    
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
<#--
	@author Per Otto Bergum Christensen
-->

<#import "report_macros.ftl" as report_macro />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" type="text/css" href="stylesheet.css"/>
	</head>
	<body>
		<h2><a class="notvisible" target="_top" href="${this.getFileName()}">${this.getTitle()}</a></h2>
		
		<#if this.hasNarrative()  >
			<div class="narrative">
				<i>
				${this.narrative.role},<br/>
				${this.narrative.action},<br/>
				${this.narrative.benefit}
				</i>
			</div>
		</#if>
	
		<div class="classBehaviour">
			<#list this.getClassBehaviour() as classBehaviour>		
				<span>${bdocMacroHelper.format(classBehaviour.className)}</span>
				<ul class="specifications">
					<@li_statements classBehaviour=classBehaviour statements=classBehaviour.statements/>
					<@li_statements classBehaviour=classBehaviour statements=classBehaviour.specifications/>				
				</ul>
			</#list>
		</div>
				
	</body>
</html>

<#macro li_statements classBehaviour statements>
	<#list statements as statement>
		<li>
			<#if statement.hasExamples()>
				<a href="${bdocMacroHelper.hrefToStatementExampleFrame(classBehaviour,statement)}" target="examples">
					${bdocMacroHelper.format(statement)}
				</a>
			</#if>
			<#if !statement.hasExamples()>
				${bdocMacroHelper.format(statement)}
			</#if>
		</li>
	</#list>
</#macro>
