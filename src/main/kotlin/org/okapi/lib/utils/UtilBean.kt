package org.okapi.lib.utils

import org.springframework.beans.BeanUtils
import java.util.*
import java.util.stream.Collectors

/**
 * author chen.kang
 */
object UtilBean {

    // 通过 bean util 复制对象
    fun <T> copy(source: Any, target: Class<T>): T? {
        try {
            val t = target.getConstructor().newInstance()
            BeanUtils.copyProperties(source, t)
            return t
        } catch (e: Exception) {
            println("bean transfer error: ${e.message}")
        }
        return null
    }

    // 复制列表直接返回
    fun <T, R> copy(sources: List<T>, rClass: Class<R>, async: Boolean): MutableList<R> {
        val stream = if (async) sources.parallelStream() else sources.stream()
        return stream.map {
            try {
                val r = rClass.getConstructor().newInstance()
                @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                BeanUtils.copyProperties(it, r)
                return@map r
            } catch (e: java.lang.Exception) {
                println("instance error: ${e.message}")
            }
            return@map null
        }.filter { obj: R? -> Objects.nonNull(obj) }.collect(Collectors.toList())
    }
}