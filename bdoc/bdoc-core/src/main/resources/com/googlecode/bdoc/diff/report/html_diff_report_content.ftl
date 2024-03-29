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


<#if bdocDiff.getProjectDiff().diffExists() >
	<hr/>
	<h2><@bdddoc.text key="updated.project.info" /></h2>
	<ul class="projectDiff">
		<#if bdocDiff.getProjectDiff().getNameDiff().diffExists()>
			<li class="projectInfo">
				<i><@bdddoc.text key="project.name"/></i>
				<ul>
					<li><i><@bdddoc.text key="old.value"/>:</i><span> ${bdocDiff.projectDiff.nameDiff.oldValue}</span></li>	
					<li><i><@bdddoc.text key="new.value"/>:</i><span> ${bdocDiff.projectDiff.nameDiff.newValue}</span></li>	
				</ul>
			</li>				
			<li class="projectInfo">
				<i><@bdddoc.text key="project.version"/></i>
				<ul>
					<li><i><@bdddoc.text key="old.value"/>:</i><span> ${bdocDiff.projectDiff.versionDiff.oldValue}</span></li>	
					<li><i><@bdddoc.text key="new.value"/>:</i><span> ${bdocDiff.projectDiff.versionDiff.newValue}</span></li>	
				</ul>
			</li>				
		</#if>				
	</ul>
</#if>

<#if bdocDiff.hasUserStoryDiff()>
	<div class="userStoryDiff">
			
		<#if bdocDiff.hasNewStories()>
			<hr/>
			<div class="newStories">
				<h2><@bdddoc.text key="new.user.stories" /></h2>
				
				<ul>
				<#list bdocDiff.newStories as userstory>	
					<li>
					<@bdddoc.story userstory=userstory header="h3"/>
					</li>							
				</#list>
				</ul>
			</div>
		</#if>
			
		<#if bdocDiff.hasUpdatedStories()>
			<hr/>		
			<div class="updatedStories">
				<h2><@bdddoc.text key="updated.user.stories"/></h2>
				<ul>
					<#list bdocDiff.updatedStories as userStoryDiff>
						
						
						<li class="userStoryDiff">
							<h3>${userStoryDiff.title}</h3>
							<ul>					
								<#if userStoryDiff.getTitleDiff().diffExists()>
									<li class="titleDiff">
										<i><@bdddoc.text key="updated.title"/></i>
										<ul>
											<li><i><@bdddoc.text key="old.value"/>:</i> ${userStoryDiff.titleDiff.oldValue}</li>
											<li><i><@bdddoc.text key="new.value"/>:</i> ${userStoryDiff.titleDiff.newValue}</li>
										</ul>
									</li>
								</#if>
								
								<#if userStoryDiff.hasNarrativeDiff()>
									<li class="narrativeDiff">
										<i><@bdddoc.text key="updated.narrative"/></i>
										<ul>
											<li>
												<i><@bdddoc.text key="old.value"/></i>:											
												<@bdddoc.narrative value=userStoryDiff.getNarrativeDiff().getOldVersion()/>
											</li>
											<li>
												<i><@bdddoc.text key="new.value"/></i>:											
												<@bdddoc.narrative value=userStoryDiff.getNarrativeDiff().getNewVersion()/>
											</li>
										</ul>
									</li>												
								</#if>
						
								<#assign moduleDiff = userStoryDiff>				
								<#include "html_module_diff.ftl">			
							</ul>
						</li>
					</#list>
				</ul>
			</div>
		</#if>
	
		<#if bdocDiff.hasDeletedStories()>
			<hr/>

			<div class="deletedStories">	
				<h2><@bdddoc.text key="deleted.user.stories" /></h2>
				<ul>
				<#list bdocDiff.deletedStories as userstory>	
					<li>
					<@bdddoc.story userstory=userstory header="h3"/>
					</li>							
				</#list>
				</ul>
			</div>
		</#if>	
		
	</div>		
</#if>	

<#if bdocDiff.hasGeneralBehaviourDiff() >	
	<div class="generalBehaviourDiff">
		<h2><@bdddoc.text key="module.behaviour"/></h2>
		
		<#if bdocDiff.getModuleBehaviourDiff().hasNewPackages() >
			<div class="newPackages">
				<h3><@bdddoc.text key="new.packages"/></h3>
				<ul>
					<#list bdocDiff.moduleBehaviourDiff.newPackages as package>
						<@bdddoc.present_package_behaviour package=package/>
					</#list>
				</ul>					
			</div>
		</#if>

		<#if bdocDiff.getModuleBehaviourDiff().hasUpdatedPackages() >			
			<div class="updatedPackages">
				<h3><@bdddoc.text key="updated.packages"/></h3>
				<ul>				
					<#list bdocDiff.moduleBehaviourDiff.packageDiff as packageDiff>
						<h3>${packageDiff.name}</h3>
						<ul>
							<#assign moduleDiff = packageDiff>				
							<#include "html_module_diff.ftl">			
						</ul>						
					</#list>
				</ul>					
			</div>
		</#if>
	
		<#if bdocDiff.getModuleBehaviourDiff().hasDeletedPackages() >
			<div class="deletedPackages">
				<h3><@bdddoc.text key="deleted.packages"/></h3>
				<ul>
					<#list bdocDiff.moduleBehaviourDiff.deletedPackages as package>
						<@bdddoc.present_package_behaviour package=package/>
					</#list>
				</ul>					
			</div>
		</#if>
	
	</div>
</#if>
