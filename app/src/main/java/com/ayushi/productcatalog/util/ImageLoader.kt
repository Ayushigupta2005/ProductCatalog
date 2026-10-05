package com.ayushi.productcatalog.util

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache

fun createImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()
}