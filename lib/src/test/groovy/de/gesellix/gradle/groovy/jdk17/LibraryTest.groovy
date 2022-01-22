package de.gesellix.gradle.groovy.jdk17

import spock.lang.Specification

class LibraryTest extends Specification {

  def "someLibraryMethod returns true"() {
    setup:
    def lib = new Library()

    expect:
    lib.checkPrivateAccess()
  }
}
