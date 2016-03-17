Often on class is more important than others, and you want i to be presented first in the userstory report. ReportConfig is the new way to configure bodc, and presentation order is the first config-element in use. Just add a class named ReportConfig to you test-directory and list the class-order you want.
Example:

```
public class ReportConfig {
	Class<?>[] presentationOrder = {
	//
			<ClassA>.class,//
			<ClassB>.class, //
			<ClassC>.class
        };
}
```