package com.ayushi.productcatalog.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayushi.productcatalog.ui.cart.CartScreen
import com.ayushi.productcatalog.ui.cart.CartViewModel
import com.ayushi.productcatalog.ui.product.ProductDetailScreen
import com.ayushi.productcatalog.ui.product.ProductScreen
import com.ayushi.productcatalog.ui.product.ProductViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val productViewModel: ProductViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()

    val productUiState = productViewModel.uiState.collectAsState()
    val searchQuery = productViewModel.currentSearchQuery.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "products"
    ) {

        composable("products") {

            ProductScreen(
                uiState = productUiState.value,
                searchQuery = searchQuery.value,
                onSearchQueryChange = productViewModel::onSearchQueryChange,
                onProductClick = { productId ->
                    navController.navigate("product/$productId")
                },
                onCartClick = {
                    navController.navigate("cart")
                },
                onRetry = productViewModel::retry
            )
        }

        composable("product/{productId}") { backStackEntry ->

            val productId = backStackEntry.arguments
                ?.getString("productId")
                ?.toIntOrNull()

            val product = productUiState.value.products
                .find { it.id == productId }

            if (product != null) {

                ProductDetailScreen(
                    product = product,
                    onAddToCart = {
                        cartViewModel.addToCart(product)
                        navController.navigate("cart")
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable("cart") {

            CartScreen(
                viewModel = cartViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}