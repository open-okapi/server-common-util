package org.okapi.lib.utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * author chen.kang
 */
object UtilJson {

    private val mp = ObjectMapper()

    fun obj2str(o: Any): String {
        return mp.writeValueAsString(o)
    }

    fun <T> string2Obj(str: String, clazz: Class<T>): T {
        return mp.readValue(str, clazz)
    }

    fun <T> string2Obj(str: String, valueType: TypeReference<T>): T {
        return mp.readValue<T>(str, valueType)
    }
}