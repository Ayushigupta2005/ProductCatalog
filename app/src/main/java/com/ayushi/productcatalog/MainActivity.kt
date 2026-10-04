package com.ayushi.productcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ayushi.productcatalog.ui.product.ProductScreen
import com.ayushi.productcatalog.ui.product.ProductViewModel
import com.ayushi.productcatalog.ui.theme.ProductCatalogTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ProductCatalogTheme {

                val viewModel: ProductViewModel = viewModel()
                val uiState = viewModel.uiState.collectAsState()
                val searchQuery = viewModel.currentSearchQuery.collectAsState()

                ProductScreen(
                    uiState = uiState.value,
                    searchQuery = searchQuery.value,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onProductClick = {}
                )
            }
        }
    }
}