# gradle-groovy-jdk17

Example project to show an issue when using Gradle
in a Groovy project on Java 17 with code performing
illegal access to Java internal modules.

This project had been created before we found
an older [Gradle issue #18671](https://github.com/gradle/gradle/issues/18671),
so this project only adds another example from our real world projects
to illustrate the issue.

## Setup

- Use Java 17

### Succeeds:

```shell
./gradlew clean check --info
```

Output should contain something like this:

```
...
LibraryTest > someLibraryMethod returns true STANDARD_ERROR
    [Test worker] INFO de.gesellix.gradle.groovy.jdk17.Library - hello 2
...
```

### Fails:

```shell
# prepare a folder with required dependencies
./gradlew clean assemble
# simulate a production environment (plain Java)
java -cp `pwd`/lib/build/classes/groovy/main:`pwd`/lib/build/runtime-libs/groovy-3.0.9.jar:`pwd`/lib/build/runtime-libs/slf4j-simple-1.7.33.jar:`pwd`/lib/build/runtime-libs/slf4j-api-1.7.33.jar de.gesellix.gradle.groovy.jdk17.Library
```

Output should contain something like this:

```
...
Exception in thread "main" groovy.lang.MissingPropertyException: Exception evaluating property 'size' for java.util.ArrayList, Reason: groovy.lang.MissingPropertyException: No such property: size for class: java.lang.String
	at org.codehaus.groovy.runtime.DefaultGroovyMethods.getAtIterable(DefaultGroovyMethods.java:8761)
	at org.codehaus.groovy.runtime.DefaultGroovyMethods.getAt(DefaultGroovyMethods.java:8749)
	at groovy.lang.MetaClassImpl$6.getProperty(MetaClassImpl.java:2071)
	at org.codehaus.groovy.runtime.callsite.GetEffectivePojoPropertySite.getProperty(GetEffectivePojoPropertySite.java:63)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callGetProperty(AbstractCallSite.java:329)
	at de.gesellix.gradle.groovy.jdk17.Library.someLibraryMethod(Library.groovy:15)
	at de.gesellix.gradle.groovy.jdk17.Library$someLibraryMethod.call(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:125)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:130)
	at de.gesellix.gradle.groovy.jdk17.Library.main(Library.groovy:9)
...
```

### Custom Workaround:

```shell
# prepare a folder with required dependencies
./gradlew clean assemble
# simulate a production environment (plain Java)
# manually opens required modules
java --add-opens java.base/java.util=ALL-UNNAMED -cp `pwd`/lib/build/classes/groovy/main:`pwd`/lib/build/runtime-libs/groovy-3.0.9.jar:`pwd`/lib/build/runtime-libs/slf4j-simple-1.7.33.jar:`pwd`/lib/build/runtime-libs/slf4j-api-1.7.33.jar de.gesellix.gradle.groovy.jdk17.Library
```

Output should look like this:

```
[main] INFO de.gesellix.gradle.groovy.jdk17.Library - hello 2
```
