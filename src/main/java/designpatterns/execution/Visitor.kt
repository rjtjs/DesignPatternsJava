package designpatterns.execution

import java.lang.IllegalArgumentException
import kotlin.math.pow

/**
 * The visitor pattern is useful for extending the functionality of an interface and its implementations with
 * minimum disruption to them.
 *
 * It does so by decoupling the operating logic from the object on which it acts, and encapsulating the logic in
 * a "Visitor" class (the name is misleading; read it more as an "Operator").
 *
 * The "visited" class then only needs the ability to accept this "visitor" and make the necessary modifications.
 *
 * E.g., we are building a game with monsters that can attack or defend. We want to extend this family of objects
 * with the ability to recover and make noises, without significantly refactoring the previous library.
 */

/**
 * A monster has strength [power] upto [maxPower]. It can [attack] to decrease another monster's power,
 * or [defend] to recover it's own.
 */
interface Monster {
    val maxPower : Float
    var power : Float
    fun attack() : Float
    fun defend(attackPower : Float = 0.0f)
    fun reportPower()

    // Add this to accept a visitor which provides new functionality.
    fun accept(visitor: Visitor)
}

class Gargoyle : Monster {
    override val maxPower = 100.0f
    override var power = 100.0f

    override fun attack() : Float {
        val attackPower = Math.random().toFloat() * power
        println("Gargoyle attacked with power $attackPower.").also { reportPower() }
        return attackPower
    }

    override fun defend(attackPower: Float) {
        val damage = Math.random().toFloat() * attackPower
        power -= damage
        power = power.coerceAtLeast(0.0f)
        println("Gargoyle defended an attack with power $attackPower, took $damage damage.").also { reportPower() }
    }

    override fun reportPower() {
        println("Gargoyle has power $power.")
    }

    // Extend functionality.
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
}

class Basilisk : Monster {
    override val maxPower = 120.0f
    override var power = 100.0f

    override fun attack() : Float {
        val randomVenomBoost =  Math.random().toFloat().pow(2) * maxPower
        val attackPower = Math.random().toFloat()  * power + randomVenomBoost
        println("Basilisk attacked with power $attackPower.").also { reportPower() }
        return attackPower
    }

    override fun defend(attackPower: Float) {
        val stupidityTax = Math.random().toFloat().pow(2) * maxPower
        val damage = Math.random().toFloat() * attackPower + stupidityTax
        power -= damage
        power = power.coerceAtLeast(0.0f)
        println("Basilisk defended an attack with power $attackPower, took $damage damage.").also { reportPower() }
    }

    override fun reportPower() {
        println("Basilisk has power $power.")
    }

    // Extend functionality.
    override fun accept(visitor: Visitor) {
        visitor.visit(this)
    }
}

/**
 * Instead of adding recover() and scream() to the [Monster] interface and implementing that functionality
 * throughout in that codebase, we can now extend the functionality by hiding away the complexity in
 * implementations of the [Visitor] interface instead.
 */
interface Visitor {
    fun visit(monster: Monster)
}

/**
 * A concrete implementation of a "visiting" function. This adds the ability for a monster to recover
 * some power.
 */
class MonsterRecoveryVisitor : Visitor {
    override fun visit(monster: Monster) {
        val recoveredPower = Math.random().toFloat().pow(2) * monster.maxPower
        monster.power += recoveredPower
        monster.power = monster.power.coerceAtMost(monster.maxPower)
        println("Monster recovered power $recoveredPower.").also { monster.reportPower() }
    }
}

class MonsterScreamVisitor : Visitor {
    override fun visit(monster: Monster) {
        when (monster)  {
            is Gargoyle -> println("Gargoyle has screamed grotesquely.")
            is Basilisk -> println("Basilisk has hisssssssed.")
            else -> throw IllegalArgumentException("Undiscovered monster species!")
        }
    }
}
