package designpatterns.creation

import spock.lang.Specification

class FactoryTest extends Specification {
    def "Factory Pattern must work as expected"() {
        when:
        Club arsenal = new Arsenal()
        Club realMadrid = new RealMadrid()
        Club dortmund = new BorussiaDortmund()

        StadiumFactory stadiumFactory = new StadiumFactory()
        TicketPrinter ticketPrinter = new TicketPrinter(stadiumFactory)

        then:
        println("MATCH 1:")
        ticketPrinter.printTicket(arsenal, realMadrid)
        println()
        println("MATCH 2:")
        ticketPrinter.printTicket(dortmund, arsenal)
    }
}
