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
	<@list_class_behaviour list=module.getClassBehaviour()/>
	<@list_examples module=module/>
	<a href="#top">&lt; Back to top</a>
</#macro>

<#macro list_class_behaviour list >
	<div class="classBehaviour">
		<#list list as classBehaviour>		
			
			<span>${bdocMacroHelper.format(classBehaviour.className)}</span>
			<ul class="specifications">
				<#list classBehaviour.statements as statement>
					<li>${bdocMacroHelper.format(statement)}</li>
				</#list>
				<#list classBehaviour.specifications as specification>
					<li>${bdocMacroHelper.format(specification)}</li>
				</#list>
			</ul>
		</#list>
	</div>
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
						<span>${bdocMacroHelper.format(classSpecifications.className)}</span>
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
					<li>
						<span>${classStatements.className}</span>
						<ul>
							<#list classStatements.statements as statement>
								<li>${bdocMacroHelper.format(statement)}</li>
							</#list>
						</ul>
					</li>									
				</#list>
			</ul>
		</div>

	</#if>
</#macro>

<#macro list_examples module>
	<div class="examples">
		<span class="exampleTitle"><@bdddoc.text key="examples"/></span>
		
		<#list module.getClassBehaviour() as classBehaviour>
	
			<#if classBehaviour.hasTestTables() || classBehaviour.hasScenarios() >
				<p>${classBehaviour.getClassName()}</p>
			</#if>
			<#if classBehaviour.hasTestTables() || classBehaviour.hasScenarios() >
				<@scenarios_and_test_table statements=classBehaviour.getStatements()/>
				<@scenarios_and_test_table statements=classBehaviour.getSpecifications()/>
			</#if>
			<#if classBehaviour.hasScenarios()>
				<@list_scenarios scenarios=classBehaviour.getScenarios()/>
			</#if>		
		</#list>

	</div>
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


<#macro scenarios_and_test_table statements>
	<#list statements as statement>
	
		<#if statement.hasScenarios()>
			<ul class="testTable">
				<p>${bdocMacroHelper.format(statement)}<BR/></p>
				<@list_scenarios scenarios=statement.getScenarios()/>
			</ul>
		</#if>
				
		<#if statement.hasTestTables()>
	
			<#list statement.getTestTables() as testTable>
				<ul class="testTable">
				
					<p>${bdocMacroHelper.format(statement)}<BR/>
					<span class="testTableDescription">${bdocMacroHelper.format(testTable)}</span></p>
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
	</#list>		
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
