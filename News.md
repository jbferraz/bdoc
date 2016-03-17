_25 October 2009 - Release 0.8.19_
> Internal java-parsing-implementation for getting variable names from methods is replace with an apt-implementation, making it more robust.

_18 October 2009 - Blog post about [BDoc, so far](http://blog.perottobergumchristensen.com/?p=168)_

_13 October 2009 - Release 0.8.17_
> [Friprog hackfest hos BEKK](http://open.bekk.no/2009/10/08/friprog-hackfest-hos-bekk). Bugfix.

_12 October 2009 - Release 0.8.16_
> Version 0.8.16 is released with support for @Before and @After in tests, together with tiny-bdd-support as default. A new example is under development, together with more documentation, for a peak look at Yatzy[GameBehaviour](http://bdoc.googlecode.com/svn/trunk/bdoc-examples/yatzy/src/test/java/yatzy/TestGameBehaviour.java) or just [download](http://bdoc.googlecode.com/files/yatzy-demo.zip) the example project.

_07 September 2009 - Release 0.8.15_
> Version 0.8.15 is released and will be presented at [JavaZone](http://javazone.no/incogito09/events/JavaZone%202009/sessions/Effektiv%20testing) - day 1. More info will be presentete here later on.

_17 August 2009 - Release 0.8.13_
> Version 0.8.13 is released with all the functionality announced in the 0.8.12 version:-)

_16 August 2009 - Release 0.8.12_
> Version 0.8.12 is released with new formatting of the userstory report, locale support for norwegian and swedish, and a new configuration option for setting the presentation order of classes in the userstory report. Read more on HowToSetLocale and HowToConfigureClassPresentationOrder.

_14 June 2009 - Release 0.8.11_
> Version 0.8.11 is released with functionality for showing formules in the bdoc report. Read more on HowToShowFormulasInTheReport.

_4 June 2009 - Release 0.8.10_
> Version 0.8.10 is released with some adjustments on the module report, describing class behaviour. The report now contains all classes. Earlier a class referenced by a user story was only shown in the user story report.

_2 June 2009 - Release 0.8.9_
> Version 0.8.9 is released with the bdoc report split into two. One with behaviour releated to userstories and one related to Java modules. A new split is in the pipeline, for a report focused mainly on the automated acceptance tests.

_3 May 2009 - Release 0.8.8_
> Version 0.8.8 is released with the reports pimped by [Espen](http://dallokken.com/espen). It amazing to see a GUI professional at work. Try the new version!

_19 April 2009 - Release 0.8.7_
> Version 0.8.7 is released with some bugfixes on test tables. Support for different types of line termination in testtable-examplemethods are added.

_23 March 2009 - Release 0.8.5_
> Version 0.8.5 is released with support for test tables and more scenario support. By configuring BDoc in "dynamic" mode it will now run tests that ends with Behaviour. Read more in [TheCalcExample](TheCalcExample.md)

_20 March 2009 - Features in the pipline_
> BDoc can now be configured to also run the tests, and inspect the results. Read more on NextFeatures

_08 March 2009 - Release 0.8.1_
> Version 0.8.1 is released with changes in the underlying persistence mechanism, the doc and diff report is merged into one, and advanced scenarios has had an upgrade. Persistence was earlier a configuration option, this is no longer an option and bdoc will always save a copy of the the bdoc (under 'user.home'/bdoc). The goal _bdoc:diff_ is removed, now the _bdoc:doc_ will both produce a bdoc report and a bdoc change log report. This change will give the users less configuration and full functionality of bdoc in each run. The goal _bdoc:generate_ has been changed to _bdoc:init_, which better communicates the intent of the goal. Read more about _bdoc:init_ in the [HowTo](HowTo.md). There are 2 minor bugfixes for the advanced scenario, first it now accepts try/catch statements and secondly it it accepts multiples of given/when/then blocks.

_20 December 2008 - Release 0.7.17_
> Version 0.7.17 is released with functionality for specifying how scenarios are generated in the BDoc report. Read more on HowToChangeFormattingOfScenarios.

_12 December 2008 - Release 0.7.16_
> Version 0.7.16 is released with functionality for specifying advanced scenarios without making an extremely long test method. The solution was proposed by Micael Vesterlund, which now also is a developer on the project. Read more on HowToWriteScenariosWithoutLongMethodNames.

_04 December 2008 - Release 0.7.14_
> Version 0.7.14 is released with functionality for specifying include/exclude configuration for the plugin. This was asked for in [Issue3](https://code.google.com/p/bdoc/issues/detail?id=3). Read more on [HowToExcludeAndIncludeTestsForBDoc](HowToExcludeAndIncludeTestsForBDoc.md).

_26 November 2008 - Release 0.7.12_
> Version 0.7.12 is released with a bugfix for how class names is generated in the BDoc report, [Issue2](https://code.google.com/p/bdoc/issues/detail?id=2). Documentation on the subject on HowtoChangeDefaultGeneratedClassNames is also added.

_08 November 2008 - Release 0.7.10_
> Version 0.7.10 is released with support for direct reference to the class under test. This could be usefull if you want to name your test differently, for example YourClassIntegrationTest.

_18 October 2008 - Release 0.7.9_

> Version 0.7.9 is released with direct [TestNG](http://testng.org/doc/) support, specific test annotation configuration is no longer neccecery. Now every testmethod annontated with _Test_ will be extracted as behaviour. It doesn't matter if the annotation is _org.junit.Test_ or _org.testng.annotations.Test_. BDoc will also extract any testmethod starting with 'test', supporting JUnit3.