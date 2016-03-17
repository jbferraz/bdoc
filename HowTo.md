> ### doc - 30 seconds introduction ###
> To give bdoc a testrun on your project place the following configuration in your pom.xml:
```
  	<build>
		<plugins>
			<plugin>
				<groupId>com.googlecode.bdoc</groupId>
				<artifactId>maven-bdoc-plugin</artifactId>
				<version>0.8.17</version>
			</plugin>
		</plugins>
	</build>
	
	<pluginRepositories>
		<pluginRepository>
			<id>perottobc-release</id>
			<url>http://perottobc-repository.googlecode.com/svn/maven2/releases</url>
		</pluginRepository>
	</pluginRepositories>
```
> BDoc is not currently deployd to the central maven repository, so until the 1.0 is released you need a reference to the repo of Mr Per Otto Bergum Christensen. Your could also build the project locally from the source at http://bdoc.googlecode.com/svn/trunk.

> With the above configuration in place run the following command:
```
mvn bdoc:doc
```
> Your bdoc.html is now generated in the ./target/site/ directory of your project. Check  it out and see what your testcode communicates.

> Default support is for JUnit3 and JUnit4 and TestNG.

> ### doc userstories - 2 minute tutorial ###
> In order to doc userstories with behaviour they must be defined and referenced by the tests.

> User stories are defined in a java enum, shown by the example below. There is no interface to implement, but the the id() and narrative() methods are required. The name of the userstory is fetched from the name() method by the enum default implementation.
```
public enum Story {

	STACK(1, "As a developer", "I want to push objects to a stack", "so that objects can be poped last in first out");

	private Integer id;
	private String[] narrative;

	Story(Integer id, String ... narrative ) {
		this.id = id;
		this.narrative = narrative;
	}

	public Integer id() {
		return id;
	}

	public String[] narrative() {
		return narrative;
	}
}
```

> The tests are referencing the user stories with an annotation, which also needs to be implemented. An example of the annotation is shown below.
```
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface Ref {

	Story value();
}
```


> It is not necessary to handcode these classes, the following command will do the job for you:
```
mvn bdoc:init
```
> The result will describe what files have been generated:
```
C:\googlecode\bdoc-base\bdoc-test\bdoc-test-no-config>mvn bdoc:init
[INFO] Scanning for projects...
[INFO] Searching repository for plugin with prefix: 'bdoc'.
[INFO] ------------------------------------------------------------------------
[INFO] Building bdoc-test-no-config
[INFO]    task-segment: [bdoc:init]
[INFO] ------------------------------------------------------------------------
[INFO] [bdoc:init]
[INFO] No package is set with option -DjavaPackage=<package>, will use: com.googlecode.bdoc.test.no.config
[INFO] ------------------------------------------------------------------------
[INFO] Result: SUCCESS
File created: C:\googlecode\bdoc-base\bdoc-test\bdoc-test-no-config\src\test\java\com\googlecode\bdoc\test\no\config\Ref.java
File created: C:\googlecode\bdoc-base\bdoc-test\bdoc-test-no-config\src\test\java\com\googlecode\bdoc\test\no\config\Story.java

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESSFUL
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1 second
[INFO] Finished at: Wed Aug 27 22:45:36 CEST 2008
[INFO] Final Memory: 5M/9M
[INFO] ------------------------------------------------------------------------
```

> With the Ref-annotation and the Story-enum in place the Ref-annotation must be configured for the plugin, see example below:
```
<plugin>
	<groupId>com.googlecode.bdoc</groupId>
	<artifactId>maven-bdoc-plugin</artifactId>
	<version>${maven.bdoc.plugin.version}</version>
	<configuration>
		<storyRefAnnotationClassName>com.googlecode.bdoc.test.no.config.Ref</storyRefAnnotationClassName>
	</configuration>
</plugin>
```

> Testclasses and/or testmethods can now be associated with userstories, which then will be documented in the bdoc.html
```
@Ref(Story.STACK)
public class TestStack {

   .. .
	@Test
	public void givenAnEmptyStackWhenOneValueIsPushedThenTheStackShouldNotBeEmpty() {
		stack.push("1");
		assertFalse(stack.isEmpty());
	}
```
> ### doc - download example ###
> Download the maven example project for a yatzy game:
http://bdoc.googlecode.com/files/yatzy-demo.zip