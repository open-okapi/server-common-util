package org.okapi.lib.utils

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * author chen.kang
 */

@DelicateCoroutinesApi
object UtilCoroutine {

    fun exec(v:()-> Unit) {
        GlobalScope.launch {
            v()
        }
    }
}