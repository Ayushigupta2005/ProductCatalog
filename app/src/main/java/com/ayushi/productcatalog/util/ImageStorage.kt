package com.ayushi.productcatalog.util

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

object ImageStorage {

    suspend fun saveImage(
        context: Context,
        imageUrl: String,
        productId: Int
    ): String? = withContext(Dispatchers.IO) {

        try {
            val directory = File(
                context.filesDir,
                "product_images"
            )

            if (!directory.exists()) {
                directory.mkdirs()
            }

            val imageFile = File(
                directory,
                "product_$productId.jpg"
            )

            if (!imageFile.exists()) {
                URL(imageUrl).openStream().use { input ->
                    imageFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }

            if (imageFile.exists() && imageFile.length() > 0) {
                imageFile.absolutePath
            } else {
                null
            }

        } catch (e: Exception) {
            null
        }
    }
}