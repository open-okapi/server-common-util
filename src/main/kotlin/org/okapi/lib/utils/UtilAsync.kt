package org.okapi.lib.utils

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.*

/**
 * author chen.kang
 */
object UtilAsync {
    private val pool: ExecutorService = ThreadPoolExecutor(
        30,
        Int.MAX_VALUE - 1,
        1000,
        TimeUnit.MILLISECONDS,
        LinkedBlockingQueue(),
        Executors.defaultThreadFactory(),
        ThreadPoolExecutor.AbortPolicy()
    )

    // 线程异步执行
    fun execRunnable(runnable: Runnable) {
        pool.execute(runnable)
    }

    // 线程异步并发执行
    fun execRunnable(async: Int, timeout: Long, unit: TimeUnit, vararg runnableList: Runnable) {
        if (runnableList.size != async) {
            throw RuntimeException("input runnable size error")
        }
        val countDownLatch = CountDownLatch(async)
        for (runnable in runnableList) {
            pool.execute(Runnable {
                try {
                    runnable.run()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    countDownLatch.countDown()
                }
            })
        }
        try {
            val down = countDownLatch.await(timeout, unit)
            if (!down) {
                throw RuntimeException("timeout!, please increase the timeout config parameter")
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    // 协程异步执行
    fun execCoroutine(async: () -> Unit, sync: () -> Unit) {
        runBlocking {
            launch {
                async()
            }
            sync()
        }
    }

    // 协程异步并发执行
    fun execCoroutine(units: MutableList<() -> Unit>) {
        runBlocking {
            for (i in 0 until units.size) {
                launch {
                    val res = async {
                        units[i]()
                    }
                    res.await()
                }
            }
        }
    }
}