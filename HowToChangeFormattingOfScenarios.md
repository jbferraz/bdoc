You can by configuration change the default formatting of a scenario in the BDoc html report.
The default formatting are one line per Given, When and Then.

For example:
  * Given a stack with one pushed value
  * When peek is called
  * Then that object should be returned and then the value should remain in the stack

If you set the parameter `scenarioFormatterClassName` to `com.googlecode.bdoc.core.report.EachOnNewLineScenarioLinesFormatter`.

Then will the report look like:
  * Given a stack with one pushed value
  * When peek is called
  * Then that object should be returned
  * Then the value should remain in the stack

```
<plugin>
	<groupId>com.googlecode.bdoc</groupId>
	<artifactId>maven-bdoc-plugin</artifactId>
	<version>${maven.bdoc.plugin.version}</version>
	<configuration>
		<scenarioFormatterClassName>com.googlecode.bdoc.core.report.EachOnNewLineScenarioLinesFormatter</scenarioFormatterClassName>
	</configuration>
</plugin>

```