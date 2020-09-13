package com.mcsimf.reddittop.core

/**
 * https://stackoverflow.com/questions/40398072/singleton-with-parameter-in-kotlin
 */
open class SingletonHolder<out T, in A>(private val constructor: (A) -> T) {

    @Volatile
    private var instance: T? = null

    fun get(arg: A): T {
        return when {
            instance != null -> instance!!
            else -> synchronized(this) {
                if (instance == null) instance = constructor(arg)
                instance!!
            }
        }
    }
}