package com.ayushi.productcatalog.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayushi.productcatalog.data.model.Product
import com.ayushi.productcatalog.data.remote.RetrofitInstance
import com.ayushi.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@OptIn(FlowPreview::class)
class ProductViewModel : ViewModel() {

    private val repository = ProductRepository(RetrofitInstance.api)

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    val currentSearchQuery: StateFlow<String> = searchQuery.asStateFlow()

    init {
        loadProducts()

        viewModelScope.launch {
            searchQuery
                .debounce(400)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        loadProducts()
                    } else {
                        searchProducts(query)
                    }
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState(isLoading = true)

            try {
                val response = repository.getProducts()

                _uiState.value = ProductUiState(
                    products = response.products
                )
            } catch (e: Exception) {
                _uiState.value = ProductUiState(
                    error = "Unable to load products. Please check your connection and try again."
                )
            }
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val response = repository.searchProducts(query)

                _uiState.value = ProductUiState(
                    products = response.products
                )
            } catch (e: Exception) {
                _uiState.value = ProductUiState(
                    error = "Unable to search products. Please check your connection and try again."
                )
            }
        }
    }

    fun retry() {
        if (searchQuery.value.isBlank()) {
            loadProducts()
        } else {
            searchProducts(searchQuery.value)
        }
    }

}