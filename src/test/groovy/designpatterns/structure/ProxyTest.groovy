package designpatterns.structure

import spock.lang.Specification

class ProxyTest extends Specification {
    def "Proxy pattern works as expected"() {
        when:
        ClientConsole mockConsole = Mock()

        8 * mockConsole.readLine() >>> [
            "pass",
            "wrong21", "wrong22", "pass",
            "wrong31", "wrong32", "wrong33",
            "shouldbelocked"]

        final ProxyAccount account = new ProxyAccount(mockConsole)

        then:
        account.printBalance() // Correct password,
        account.printBalance() // then 2 incorrect passwords, and a correct password,
        account.printBalance() // then 3 incorrect passwords, leading to lock.
    }
}
