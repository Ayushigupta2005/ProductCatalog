package com.ayushi.productcatalog.ui.product


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushi.productcatalog.data.model.Product
import com.ayushi.productcatalog.data.remote.RetrofitInstance
import com.ayushi.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository(RetrofitInstance.api)

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState(isLoading = true)

            try {
                val response = repository.getProducts()

                _uiState.value = ProductUiState(
                    products = response.products
                )
            } catch (e: Exception) {
                _uiState.value = ProductUiState(
                    error = "Unable to load products. Please try again."
                )
            }
        }
    }
}