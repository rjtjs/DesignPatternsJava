package designpatterns.execution

import spock.lang.Specification

class HelloWorld extends Specification {
    def "Say Hello World" () {
        when:
        System.out.println("Hello World")

        then:
        1 == 1
    }
}
