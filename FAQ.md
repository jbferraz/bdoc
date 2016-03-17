# Frequently Asked Questions (FAQ) #

### BDoc doesn't show up in my site report ###
Edit your pom.xml and add bdoc as a report plugin:
```
<reporting>
	<plugins>
		<plugin>
			<groupId>com.googlecode.bdoc</groupId>
			<artifactId>maven-bdoc-plugin</artifactId>
			<version>.. .</version>
			
			<configuration>
			  .. . 
			</configuration>
			
		</plugin>
	</plugins>
</reporting>

```

### Eclipse does not allow me to create Enums ###
Make sure you have configured Maven to be Java 1.5 compliant by configuring the compiler plug-in like so:
```
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-compiler-plugin</artifactId>
	<configuration>
		<source>1.5</source>
		<target>1.5</target>
	</configuration>
</plugin>
```
Once the POM is configured run these two commands:
**mvn eclipse:clean** mvn eclipse:eclipse
Then refresh your project and Eclipse should identify your project as valid Java 5 project.

### svn checkout does not work ###
If your behind a proxy and don't get a svn connection, try to configure user\Programdata\Subversion\servers with your proxy-information, use of svn directly could be an issue, not picking up the proxy-settings, try adding the repository in eclipse and do a checkout there. Importent settings: http-proxy-exceptions, http-proxy-host, http-proxy-port