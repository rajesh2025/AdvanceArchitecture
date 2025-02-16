package com.rajesh.imagecache.cache

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ImageCaching private constructor(context: Context, cacheSize: Int) {
    private val cache = CacheRepository(context, cacheSize)
    private val executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val mRunningDownloadList: HashMap<String, Future<Bitmap?>> = hashMapOf()

    fun displayImage(url: String, imageView: ImageView, placeholder: Int) {
        val bitmap = cache.get(url)
        bitmap?.let {
            imageView.setImageBitmap(it)
            return
        } ?: run {
            imageView.tag = url
            imageView.setImageResource(placeholder)
            addDownloadImageTask(url, DownloadImageTask(url, imageView, cache))
        }

    }

    private fun addDownloadImageTask(url: String, downloadImageTask: DownloadImageTask) {
        mRunningDownloadList.put(url, executorService.submit(downloadImageTask))
    }

    fun clearCache() {
        cache.clear()
    }

    fun cancelTask(url: String) {
        synchronized(this) {
            mRunningDownloadList.forEach {
                if (it.key == url && !it.value.isDone) {
                    it.value.cancel(true)
                }
            }
        }
    }

    fun cacheAll() {
        synchronized(this) {
            mRunningDownloadList.forEach {
                if (!it.value.isDone) {
                    it.value.cancel(true)
                }
            }
        }
    }

    companion object {
        private val INSTANCE: ImageCaching? = null

        @Synchronized
        fun getInstance(context: Context, cacheSize: Int = Config.defaultCacheSize): ImageCaching {
            return INSTANCE?.let {
                return INSTANCE
            } ?: run {
                return ImageCaching(context, cacheSize)
            }
        }
    }
}