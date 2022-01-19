package de.gesellix.gradle.groovy.jdk17

import groovy.util.logging.Slf4j

@Slf4j
class Library {

  boolean someLibraryMethod() {
    List someList = new ArrayList()
    someList.addAll(["element 1", "element 2"])
    log.info("hello ${someList.size}")
    true
  }
}
