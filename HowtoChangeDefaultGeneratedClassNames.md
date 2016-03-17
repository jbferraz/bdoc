BDoc uses the name of the test class to figure out which class it tests, and uses this in the BDoc report. The logic is simple:
  * YourClassTest => YourClass
  * TestYourClass => YourClass
  * TestTest => Test
If you don't want BDoc to set a default class name you can specify this with an annotation as shown below
```
	@RefClass(YourClass.class)
	public class YourClassIntegrationTest {
	}
```

The RefClass annotation does not exist, you have to make it, preferably in the same package as the Ref and Story annotation. The RefClass annotation should look like this:
```
	@Retention(RetentionPolicy.RUNTIME)
	@Target( { ElementType.TYPE })
	public @interface RefClass {

		Class<? extends Object> value();
	}
```