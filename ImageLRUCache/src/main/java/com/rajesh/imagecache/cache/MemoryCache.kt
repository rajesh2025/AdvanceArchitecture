package com.rajesh.imagecache.cache

import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache

class MemoryCache(newMaxSize: Int) : ImageCache {
    private val cache: LruCache<String, Bitmap>

    init {
        val cacheSize: Int
        if (newMaxSize > Config.maxMemory) {
            cacheSize = Config.defaultCacheSize
            Log.d(
                "MemoryCache", "New value of cache is bigger than " +
                        "maximum cache available on system"
            )
        } else {
            cacheSize = newMaxSize
        }

        cache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                return if (value != null) {
                    (value.rowBytes).times(value.height) / 1024
                } else {
                    return 1
                }
            }
        }

    }

    override fun put(url: String, bitmap: Bitmap) {
        cache.put(url, bitmap)
    }

    override fun get(url: String): Bitmap? {
        return cache.get(url)
    }

    override fun clear() {
        cache.evictAll()
    }
}