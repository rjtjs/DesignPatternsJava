package designpatterns.structure

/**
 * The proxy pattern is used to provide a representative object in place of an intended object. Most commonly, this
 * is so that the representative object (the "proxy") can handle some client side logic, before invoking the logic
 * of the intended class.
 *
 * E.g., in a banking app, a real accounts might be handled server side, but the clients interact with a proxy account
 * that checks if they are a bank customer, their password etc., before allowing access to the real account.
 */

/**
 * This is a limited account.
 */
interface Account {
    fun printBalance()
}

/**
 * The "real" account object that the client feels they are working with.
 */
class RealAccount : Account {
    override fun printBalance() {
        // Accesses the database here and gets potentially confidential information. Since DB access is expensive,
        // and we want to carefully control access to confidential information, this should be only be accessed
        // once the proxy as authenticated the client.
        println("Your balance is $3.50.")
    }
}

enum class AccountState {
    LOCKED,
    UNLOCKED
}

interface ClientConsole {
    fun readLine(): String
}

/**
 * The proxy account.
 */
class ProxyAccount(private val console: ClientConsole): Account {
    private val realAccount: RealAccount = RealAccount()
    private var accountState: AccountState = AccountState.UNLOCKED

    companion object {
        private const val MAX_ATTEMPTS: Int = 3
        private const val CURRENT_PASSWORD: String = "pass" // I'm sorry, it had to be done.
    }

    override fun printBalance() {
        // Obviously a contrived example, but execute some client side logic to see if calling realAccount is
        // indeed necessary.
        if (accountState == AccountState.LOCKED) {
            println("Account locked! Boohoo.")
            return
        }

        var passwordIsCorrect = false

        for (attempt in 0..MAX_ATTEMPTS) {
            val enteredText = console.readLine() // Sanitize the entered text to prevent injection and other badness.

            if (enteredText == CURRENT_PASSWORD) {
                passwordIsCorrect = true
                break
            } else {
                println("Incorrect password. ${MAX_ATTEMPTS - attempt} attempts remaining.")
            }
        }

        if (passwordIsCorrect) {
            // Everything looks good, now communicate over the wire with the real account, and print details.
            realAccount.printBalance()
        } else {
            println("Maximum password attempts reached, locking account.")
            accountState = AccountState.LOCKED
        }
    }
}