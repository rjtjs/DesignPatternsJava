package designpatterns.concurrency

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

class Counter {
    private var count = 0
    private val dispatchQueue: BlockingQueue<Runnable> = LinkedBlockingDeque()

    init {
        thread {
            try {
                while (true) {
                    dispatchQueue.take().run()
                }
            } catch (e: InterruptedException) {
                // Do nothing.
            }
        }
    }

    fun increment() {
        dispatchQueue.put(Runnable { count += 1; println("Count = ${count}.") })
    }
}