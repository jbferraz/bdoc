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
		<title>${bddDocDiff.projectDiff.nameDiff.newValue}</title>
		<#include "css.ftl">					
	</head>
	<body>
		<div id="projectInfo">
			<h1>${bddDocDiff.projectDiff.nameDiff.newValue} ${bddDocDiff.projectDiff.versionDiff.newValue}</h1>
		</div>
		
		<div id="docTimeDiff"> 
			<@bdddoc.text key="date.periode" />: ${bddDocDiff.docTimeDiff.time1?datetime} - ${bddDocDiff.docTimeDiff.time2?datetime} <br/> 
			<@bdddoc.text key="time.periode" />: 
			${bddDocDiff.docTimeDiff.days} <@bdddoc.text key="days" />, 
			${bddDocDiff.docTimeDiff.hours} <@bdddoc.text key="hours" />, 
			${bddDocDiff.docTimeDiff.minutes} <@bdddoc.text key="minutes" />
		</div>
	
		<#if bddDocDiff.getProjectDiff().diffExists() >
			<hr/>
			<h2><@bdddoc.text key="updated.project.info" /></h2>
			<ul id="projectDiff">
				<#if bddDocDiff.getProjectDiff().getNameDiff().diffExists()>
					<li class="projectInfo">
						<i><@bdddoc.text key="project.name"/></i>
						<ul>
							<li><i><@bdddoc.text key="old.value"/>:</i><span> ${bddDocDiff.projectDiff.nameDiff.oldValue}</span></li>	
							<li><i><@bdddoc.text key="new.value"/>:</i><span> ${bddDocDiff.projectDiff.nameDiff.newValue}</span></li>	
						</ul>
					</li>				
					<li class="projectInfo">
						<i><@bdddoc.text key="project.version"/></i>
						<ul>
							<li><i><@bdddoc.text key="old.value"/>:</i><span> ${bddDocDiff.projectDiff.versionDiff.oldValue}</span></li>	
							<li><i><@bdddoc.text key="new.value"/>:</i><span> ${bddDocDiff.projectDiff.versionDiff.newValue}</span></li>	
						</ul>
					</li>				
				</#if>				
			</ul>
		</#if>
		
		<#if bddDocDiff.hasUserStoryDiff()>
			<div id="userStoryDiff">
					
				<#if bddDocDiff.hasNewStories()>
					<hr/>
					<div id="newStories">
						<h2><@bdddoc.text key="new.user.stories" /></h2>
						
						<ul>
						<#list bddDocDiff.newStories as userstory>	
							<li>
							<@bdddoc.story userstory=userstory header="h3"/>
							</li>							
						</#list>
						</ul>
					</div>
				</#if>
					
				<#if bddDocDiff.hasUpdatedStories()>
					<hr/>		
					<div id="updatedStories">
						<h2><@bdddoc.text key="updated.user.stories"/></h2>
						<ul>
							<#list bddDocDiff.updatedStories as userStoryDiff>
								
								
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
			
				<#if bddDocDiff.hasDeletedStories()>
					<hr/>

					<div id="deletedStories">	
						<h2><@bdddoc.text key="deleted.user.stories" /></h2>
						<ul>
						<#list bddDocDiff.deletedStories as userstory>	
							<li>
							<@bdddoc.story userstory=userstory header="h3"/>
							</li>							
						</#list>
						</ul>
					</div>
				</#if>	
				
			</div>		
		</#if>	
	
		<#if bddDocDiff.hasGeneralBehaviourDiff() >	
			<div id="generalBehaviourDiff">
				<h2><@bdddoc.text key="general.behaviour"/></h2>
				
				<#if bddDocDiff.getGeneralBehaviourDiff().hasNewPackages() >
					<div id="newPackages">
						<h3><@bdddoc.text key="new.packages"/></h3>
						<ul>
							<#list bddDocDiff.generalBehaviourDiff.newPackages as package>
								<@bdddoc.present_package_behaviour package=package/>
							</#list>
						</ul>					
					</div>
				</#if>
	
				<#if bddDocDiff.getGeneralBehaviourDiff().hasUpdatedPackages() >			
					<div id="updatedPackages">
						<h3><@bdddoc.text key="updated.packages"/></h3>
						<ul>				
							<#list bddDocDiff.generalBehaviourDiff.packageDiff as packageDiff>
								<h3>${packageDiff.name}</h3>
								<ul>
									<#assign moduleDiff = packageDiff>				
									<#include "html_module_diff.ftl">			
								</ul>						
							</#list>
						</ul>					
					</div>
				</#if>
			
				<#if bddDocDiff.getGeneralBehaviourDiff().hasDeletedPackages() >
					<div id="deletedPackages">
						<h3><@bdddoc.text key="deleted.packages"/></h3>
						<ul>
							<#list bddDocDiff.generalBehaviourDiff.deletedPackages as package>
								<@bdddoc.present_package_behaviour package=package/>
							</#list>
						</ul>					
					</div>
				</#if>
			
			</div>
		</#if>
				
	</body>
</html>