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
	@author Espen Dalløkken
-->

<#if moduleDiff.hasNewScenarios()>		
	<li class="newScenarios">
		<@bdddoc.list_scenarios textKey="new.scenarios" scenarios=moduleDiff.getNewScenarios()/>
	</li>
</#if>

<#if moduleDiff.hasDeletedScenarios()>
	<li class="deletedScenarios">
		<@bdddoc.list_scenarios textKey="deleted.scenarios" scenarios=moduleDiff.getDeletedScenarios()/>
	</li>
</#if>						
		
<#if moduleDiff.hasNewClassSpecifications()>					
	<li class="newSpecifications">
		<@bdddoc.list_class_specifications textKey="new.specifications" list=moduleDiff.getNewClassSpecifications()/>										
	</li>
</#if>

<#if moduleDiff.hasDeletedClassSpecifications()>
	<li class="deletedSpecifications">
		<@bdddoc.list_class_specifications textKey="deleted.specifications" list=moduleDiff.getDeletedClassSpecifications()/>
	</li>
</#if>

<#if moduleDiff.hasNewClassStatements()>					
	<li class="newStatements">
		<@bdddoc.list_class_statements textKey="new.statements" list=moduleDiff.getNewClassStatements()/>										
	</li>
</#if>

<#if moduleDiff.hasDeletedClassStatements()>
	<li class="deletedStatements">
		<@bdddoc.list_class_statements textKey="deleted.statements" list=moduleDiff.getDeletedClassStatements()/>
	</li>
</#if>
