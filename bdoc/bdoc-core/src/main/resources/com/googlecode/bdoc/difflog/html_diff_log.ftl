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

<#import "bdd_doc_macros.ftl" as bdddoc />

<#setting date_format="short">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><@bdddoc.text key="diff.log.title" /></title>
		<#include "css.ftl">					
	</head>
	<body>
		<div class="diffLogs">
			<div class="diffLogTitle">
				<h1><@bdddoc.text key="diff.log.title" /></h1>
			</div>
			<div class="content">
				<#list diffLog.diffList as bdocDiff>
					<#include "html_diff_report_content.ftl">
				</#list>
			</div>
		</div>
	</body>
</html>