If there are certain parts of your code you wan't BDoc do exclude or include you can configure this "Ant-style". In the example below the directory _com/googlecode/bdoc/examples/excluded/_ with all its testclasses and subdirectories will be excluded. The underlying implementation is done with the Ant-api, so any pattern that works with Ant should work in BDoc as well.

```
<plugin>
	<groupId>com.googlecode.bdoc</groupId>
	<artifactId>maven-bdoc-plugin</artifactId>
	<version>${maven.bdoc.plugin.version}</version>
	<configuration>
		<storyRefAnnotationClassName>com.googlecode.bdoc.examples.taskhandling.Ref</storyRefAnnotationClassName>
		<logDirectory>bddlog</logDirectory>
		
		<excludes>
			<exclude>com/googlecode/bdoc/examples/excluded/**</exclude>
		</excludes>
		
		<includes>
			<include>com/googlecode/bdoc/examples/taskhandling/**</include>
		</includes>
		
	</configuration>
</plugin>

```