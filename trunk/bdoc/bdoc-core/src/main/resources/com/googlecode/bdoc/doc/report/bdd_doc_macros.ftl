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


<#macro story userstory header="h2">
	<div class="userstory">
		<a name="${userstory.title}"></a>
		<${header}>${userstory.title}</${header}>
		<@bdddoc.narrative value=userstory.getNarrative()/>		
		<@list_behaviour module=userstory/>
	</div>	
</#macro>

<#macro present_package_behaviour package>
	<div class="package">
		<a name="${package.name}"></a>
		<h3>${package.name}</h3>
		<@list_behaviour module=package/>
	</div>				
</#macro>

<#macro list_behaviour module>
	<@list_class_statements list=module.getClassStatements()/>
	<@list_class_specifications list=module.getClassSpecifications()/>
	<@list_scenarios textKey="scenarios" scenarios=module.getScenarios()/>
	<@list_test_tables textKey="test_tables" testTables=module.getTestTables()/>
	<a href="#top">&lt; Back to top</a>				
</#macro>

<#macro list_test_tables testTables textKey="">
	<#if 0 < testTables?size >
		<div class="classBehaviour">
			<#if !(textKey=="") >
				<span class="scenarioTitle"><@bdddoc.text key="${textKey}" /></span>
			</#if>

			<div class="testTables"> 
				<#list testTables as testTable>													
					<ul class="testTable">
						<div class="${testTable.sentence}">
							<p>${bdocMacroHelper.format(testTable)}</p>
							<table>
								<thead>
									<tr>
									<#list testTable.getHeaderColumns() as headerColumn>
										<th>${bdocMacroHelper.format(headerColumn)}</th>							
									</#list>
									</tr>
								</thead>
								<tbody>
									<#list testTable.getRows() as row>
									<tr>							
										<#list row.getColumns() as column>
											<td>${bdocMacroHelper.formatTableColumn(column)}</td>							
										</#list>
									</tr>
									</#list>
								</tbody>							
							</table>
						</div>								
					</ul>
				</#list>
			</div>
		</div>
	</#if>
</#macro>

<#macro list_scenarios scenarios textKey="">
	<#if 0 < scenarios?size >
		<div class="classBehaviour">
			<#if !(textKey=="") >
				<span class="scenarioTitle"><@bdddoc.text key="${textKey}" /></span>
			</#if>
			<div class="scenarios"> 
				<#list scenarios as scenario>													
					<ul class="scenario">
						${bdocMacroHelper.scenarioLines(scenario)}
					</ul>
				</#list>
			</div>
		</div>
	</#if>
</#macro>

<#macro list_class_specifications list textKey="">
	<#if 0 < list?size >
	
		<div class="classBehaviour">
			<#if !(textKey=="") >
				<span class="scenarioTitle"><@bdddoc.text key="${textKey}" /></span>
			</#if>
			
			<ul class="specifications">
				<#list list as classSpecifications>
					<li>
						<span>${classSpecifications.className}</span>
						<ul>
							<#list classSpecifications.specifications as specification>
								<li>${bdocMacroHelper.format(specification)}</li>
							</#list>
						</ul>
					</li>									
				</#list>
			</ul>
		</div>
	
	</#if>									
</#macro>

<#macro list_class_statements list textKey="">
	<#if 0 < list?size >
		
		<div class="classBehaviour">
			<#if !(textKey=="") >
				<span class="scenarioTitle"><@bdddoc.text key="${textKey}" /></span>
			</#if>

			<ul class="statements">
				<#list list as classStatements>
					<#list classStatements.statements as statement>
						<li>${bdocMacroHelper.format(statement)}</li>
					</#list>
				</#list>
			</ul>
		</div>
		
	</#if>									
</#macro>

<#macro narrative value>
	<div class="narrative">
		${value.role},<br/> 
		${value.action},<br/> 
		${value.benefit}
	</div>	
</#macro>

<#macro text key>
	${bdocMacroHelper.text(key)}
</#macro>
