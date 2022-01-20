plugins {
  groovy
  `java-library`
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.codehaus.groovy:groovy:3.0.9")
  implementation("org.slf4j:slf4j-api:1.7.33")
  implementation("org.slf4j:slf4j-simple:1.7.33")
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      useSpock("2.0-groovy-3.0")
    }
  }
}

val resolvedRuntimeDependencies: Configuration by configurations.creating {
  extendsFrom(configurations.implementation.get())
}

val copyRuntimeDependencies by tasks.registering(Copy::class) {
  from(resolvedRuntimeDependencies)
  into("$buildDir/runtime-libs")
}

tasks.assemble.get().dependsOn(copyRuntimeDependencies)
