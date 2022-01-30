package designpatterns.creation

/**
 * The factory pattern is used to delegate determination of the specific class or implementation to runtime.
 *
 * E.g., suppose we are building a ticket printing system for a match between two clubs. The club details, and
 * stadium details must be determined at runtime.
 *
 * We could have the ticket printing system sift through the complexity of associating clubs with stadiums, managers,
 * currency, transportation details, etc., but that would become unmanageable very quickly. The clean way is to
 * encapsulate the logic of this association within factory classes, and simply use the factory interface within the
 * ticket printing system.
 */
import java.lang.IllegalArgumentException
import kotlin.random.Random

/**
 * A stadium.
 */
interface Stadium {
    val name: String
    val maxCapacity: Int
    fun getMatchdayCapacity(): Int
}

/**
 * A concrete implementation of stadium.
 */
class EmiratesStadium: Stadium {
    override val name = "The Emirates"
    override val maxCapacity = 100
    override fun getMatchdayCapacity(): Int {
        return (Math.random() * maxCapacity.toDouble()).toInt()
    }
}

class Westfallonstadion: Stadium {
    override val name = "Westfallonstadion"
    override val maxCapacity = 200
    override fun getMatchdayCapacity(): Int {
        return (Math.random() * maxCapacity.toDouble()).toInt()
    }
}

class SantiagoBernabeu: Stadium {
    override val name = "Santiago Bernabeu"
    override val maxCapacity = 300
    override fun getMatchdayCapacity(): Int {
        return (Math.random() * maxCapacity.toDouble()).toInt()
    }
}

/**
 * A club. A club is not associated with stadium since clubs can change stadiums (cough, cough, Rams...)
 */
interface Club {
    val name: String
    val location: String
    fun getCurrentManager(): String
}

/**
 * A concrete implementation of a club.
 */
class Arsenal: Club {
    override val name = "Arsenal F.C."
    override val location = "London, England"
    override fun getCurrentManager(): String {
        return "Mikel Arteta"
    }
}

class BorussiaDortmund: Club {
    override val name = "Borussia Dortmund"
    override val location = "Dortmund, Germany"
    override fun getCurrentManager(): String {
        return "Marco Rose"
    }
}

class RealMadrid: Club {
    private val managerList = listOf("Zenadine Zidane", "Carlo Ancelotti", "currently fired")
    override val name = "Real Madrid"
    override val location = "Madrid, Spain"
    override fun getCurrentManager(): String {
        return managerList[Random.nextInt(managerList.size)] // TBD at runtime.
    }
}

/**
 * The factory classes encapsulates the logic for constructing the specifically required object at runtime, in this
 * case additional details about the club like stadium etc.
 */
class StadiumFactory {
    fun getStadium(club: Club): Stadium {
        return when (club) {
            is Arsenal -> EmiratesStadium()
            is RealMadrid -> SantiagoBernabeu()
            is BorussiaDortmund -> Westfallonstadion()
            else -> throw IllegalArgumentException("Unknown club!")
        }
    }
}

class TicketPrinter(val stadiumFactory: StadiumFactory) {
    fun printTicket(host: Club, visitor: Club) {
        // The printer is decoupled from the complexity of constructing the correct stadium.
        val stadium = stadiumFactory.getStadium(host)
        val ticketString =
                """
                    Welcome to the match between ${host.name} and ${visitor.name} at ${stadium.name}! 
                    Our visitors come from ${visitor.location}, and their manager is ${visitor.getCurrentManager()}.
                    
                    Our manager ${host.getCurrentManager()} welcomes ${visitor.name} and their fans to ${stadium.name}!
                    
                    Today's attendance is ${stadium.getMatchdayCapacity()} out of a possible ${stadium.maxCapacity}. 
                    Enjoy the game!
                """.trimIndent()
        println(ticketString)
    }
}
