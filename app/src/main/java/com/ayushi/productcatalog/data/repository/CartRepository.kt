package com.ayushi.productcatalog.data.repository

import android.content.Context
import com.ayushi.productcatalog.data.local.CartDao
import com.ayushi.productcatalog.data.local.CartEntity
import com.ayushi.productcatalog.data.model.Product
import com.ayushi.productcatalog.util.ImageStorage
import kotlinx.coroutines.flow.Flow

class CartRepository(
    private val dao: CartDao,
    private val context: Context
) {

    fun getCartItems(): Flow<List<CartEntity>> = dao.getCartItems()

    suspend fun addToCart(product: Product) {

        val existingItem = dao.getCartItem(product.id)

        if (existingItem == null) {

            val localImagePath = ImageStorage.saveImage(
                context = context,
                imageUrl = product.thumbnail,
                productId = product.id
            )

            dao.upsert(
                CartEntity(
                    productId = product.id,
                    title = product.title,
                    price = product.price,
                    thumbnail = localImagePath ?: product.thumbnail,
                    quantity = 1
                )
            )

        } else {

            dao.upsert(
                existingItem.copy(
                    quantity = existingItem.quantity + 1
                )
            )
        }
    }

    suspend fun increaseQuantity(productId: Int) {
        val item = dao.getCartItem(productId)

        if (item != null) {
            dao.upsert(
                item.copy(
                    quantity = item.quantity + 1
                )
            )
        }
    }

    suspend fun decreaseQuantity(productId: Int) {
        val item = dao.getCartItem(productId)

        if (item != null) {
            if (item.quantity > 1) {
                dao.upsert(
                    item.copy(
                        quantity = item.quantity - 1
                    )
                )
            } else {
                dao.deleteById(productId)
            }
        }
    }

    suspend fun removeFromCart(productId: Int) {
        dao.deleteById(productId)
    }

    suspend fun clearCart() {
        dao.clearCart()
    }
}