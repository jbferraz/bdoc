# Use Spec to express text that can't be written with a testmethod #

The following code block illustrates how to express formulas that should be part of the bdoc report.

```
	@Test
	@Spec("( value1 + value2 ) / 2")
	public void averageIsCalculatedAs$spec$() {
		assertEquals(15, calc.average(10, 20), .001);
	}
```

The resulting report line will be:
  * Average is calculated as ( value 1 + value 2 ) / 2

### Spec annotation ###
You need to create your own Spec annotation in order for this to work, like the one shown below:
```
package com.googlecode.bdoc.calc.bdoc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD  })
public @interface Spec {
	String value();	
}

```

As long as the annotation has the classname _Spec_ and a _value()_ method bdoc will figure out the rest.