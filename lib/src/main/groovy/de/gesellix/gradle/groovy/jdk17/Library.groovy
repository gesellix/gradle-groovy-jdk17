package de.gesellix.gradle.groovy.jdk17

import groovy.util.logging.Slf4j

@Slf4j
class Library {

  static void main(String[] args) {
    new Library().checkPrivateAccess()
  }

  boolean checkPrivateAccess() {
    ensureJava17Runtime()

    // This code is illegal in Java 16+ and tests should fail.
    // Access to 'size' exceeds its access rights
    def theSize = ["element 1", "element 2"].size

    log.info("hello {}", theSize)
    theSize == 2
  }

  void ensureJava17Runtime() {
    String version = System.getProperty("java.version")
    if ("17" != version) {
      log.info("java.version: {}", version)

      String runtimeVersion = System.getProperty("java.runtime.version")
      log.info("java.runtime.version: {}", runtimeVersion)

      String vendor = System.getProperty("java.vendor")
      log.info("java.vendor: {}", vendor)

      String vm = System.getProperty("java.vm.name")
      log.info("java.vm.name: {}", vm)

      throw new IllegalStateException("This one is meant to run on Java 17, but the Java version is $runtimeVersion")
    }
  }
}
