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

		<SCRIPT language='JavaScript'> 
			function openSource(location_href) {
			 parent.frames[2].location.href=location_href;			 
			}
		</SCRIPT>		
	</head>
	<body>

		<div class="exampleStatementTitle">
			<p><a class="notvisible" href="javascript:openSource('../xref-test/${bdocMacroHelper.statementXRef(statement)}')">${bdocMacroHelper.format(statement)}</a></p>
		</div>	
		
		<#if statement.hasScenarios()>
			<ul class="scenario">
				<@list_scenarios scenarios=statement.getScenarios()/>
			</ul>
		</#if>
				
		<#if statement.hasTestTables()>
		
			<#list statement.getTestTables() as testTable>
				<ul class="testTable">
					<span class="testTableDescription">${bdocMacroHelper.format(testTable)}</span>
					<table>
						<thead>
							<tr>
							<#list testTable.getHeaderColumns() as headerColumn>
								<th>${bdocMacroHelper.formatHeaderColumn(headerColumn)}</th>
							</#list>
							</tr>
						</thead>
						<tbody>
							<#list testTable.getRows() as row>
							<tr>
								<#list row.getColumns() as column>
									<td>${bdocMacroHelper.format(column)}</td>
								</#list>
							</tr>
							</#list>
						</tbody>
					</table>
					
				</ul>
			</#list>
		</#if>
		
	</body>
</html>

<#macro list_scenarios scenarios>
	<#if 0 < scenarios?size >
		<div class="classBehaviour">
			<div class="scenarios">
				<#list scenarios as scenario>
					<ul class="scenario">
						<#list scenario.getParts() as part>
							<li>
								${bdocMacroHelper.format(part.camelCaseDescription())}
								<#if part.hasIndentedParts() >
									<ul>
										<#list part.getIndentedParts() as part>
											<li>${bdocMacroHelper.format(part.camelCaseDescription())}</li>
										</#list>
									</ul>
								</#if>						
							</li>
						</#list>
						<BR/>
					</ul>
				</#list>
			</div>
		</div>
	</#if>
</#macro>