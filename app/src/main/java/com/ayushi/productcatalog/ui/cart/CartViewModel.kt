package com.ayushi.productcatalog.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ayushi.productcatalog.data.local.CartDatabase
import com.ayushi.productcatalog.data.model.Product
import com.ayushi.productcatalog.data.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CartRepository(
        dao = CartDatabase.getDatabase(application).cartDao(),
        context = application.applicationContext
    )

    val cartItems = repository.getCartItems()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val totalItemCount = cartItems
        .map { items ->
            items.sumOf { it.quantity }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

    val totalPrice = cartItems
        .map { items ->
            items.sumOf { it.price * it.quantity }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0.0
        )

    fun addToCart(product: Product) =
        viewModelScope.launch {
            repository.addToCart(product)
        }

    fun increaseQuantity(id: Int) =
        viewModelScope.launch {
            repository.increaseQuantity(id)
        }

    fun decreaseQuantity(id: Int) =
        viewModelScope.launch {
            repository.decreaseQuantity(id)
        }

    fun removeFromCart(id: Int) =
        viewModelScope.launch {
            repository.removeFromCart(id)
        }

    fun clearCart() =
        viewModelScope.launch {
            repository.clearCart()
        }
}