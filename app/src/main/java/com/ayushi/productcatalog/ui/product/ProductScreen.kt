package com.ayushi.productcatalog.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayushi.productcatalog.R
import androidx.compose.ui.tooling.preview.Preview
import com.ayushi.productcatalog.data.model.Product
import com.ayushi.productcatalog.ui.theme.ProductCatalogTheme

@Composable
fun ProductScreen(
    uiState: ProductUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onProductClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp
                )
        ) {

            Text(
                text = "Discover",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Find something you'll love",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = {
                    Text("Search products")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search"
                    )
                },
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Popular products",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            uiState.products.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No products found",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 24.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.products) { product ->
                        ProductCard(
                            product = product,
                            onClick = {
                                onProductClick(product.id)
                            }
                        )
                    }
                }
            }
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ProductScreenPreview() {
    ProductCatalogTheme {
        ProductScreen(
            uiState = ProductUiState(
                products = listOf(
                    Product(
                        id = 1,
                        title = "Essence Mascara Lash Princess",
                        description = "The Essence Mascara Lash Princess gives your lashes a dramatic look.",
                        price = 9.99,
                        rating = 4.5,
                        category = "beauty",
                        brand = "Essence",
                        stock = 99,
                        thumbnail = ""
                    ),
                    Product(
                        id = 2,
                        title = "Eyeshadow Palette with Mirror",
                        description = "A beautiful eyeshadow palette with a built-in mirror.",
                        price = 19.99,
                        rating = 4.2,
                        category = "beauty",
                        brand = "Glamour Beauty",
                        stock = 34,
                        thumbnail = ""
                    ),
                    Product(
                        id = 3,
                        title = "Powder Canister",
                        description = "A compact powder canister for everyday use.",
                        price = 14.99,
                        rating = 4.7,
                        category = "beauty",
                        brand = "Velvet Touch",
                        stock = 55,
                        thumbnail = ""
                    ),
                    Product(
                        id = 4,
                        title = "Red Lipstick",
                        description = "A rich red lipstick with a smooth finish.",
                        price = 12.99,
                        rating = 4.4,
                        category = "beauty",
                        brand = "L'Oreal",
                        stock = 42,
                        thumbnail = ""
                    )
                )
            ),
            searchQuery = "",
            onSearchQueryChange = {},
            onProductClick = {}
        )
    }
}